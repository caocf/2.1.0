package com.mysteel.banksteel.view.ui;

import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteeltwo.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class SwipeListView extends ListView implements OnScrollListener
{
	private Boolean mIsHorizontal;
	private View mPreItemView;
	private View mCurrentItemView;
	private float mFirstX;
	private float mFirstY;
	private int mRightViewWidth;
	private final int mDuration = 100;
	private final int mDurationStep = 10;
	private boolean mIsShown;

	private final static int SCROLL_BACK_HEADER = 0;
	private final static int SCROLL_BACK_FOOTER = 1;

	private final static int SCROLL_DURATION = 400;

	// when pull up >= 50px
	private final static int PULL_LOAD_MORE_DELTA = 50;

	// support iOS like pull
	private final static float OFFSET_RADIO = 1.8f;

	private float mLastY = -1;

	// used for scroll back
	private Scroller mScroller;
	// user's scroll listener
	private OnScrollListener mScrollListener;
	// for mScroller, scroll back from header or footer.
	private int mScrollBack;

	// the interface to trigger refresh and load more.
	private IXListViewListener mListener;

	private XHeaderView mHeader;
	// header view content, use it to calculate the Header's height. And hide it
	// when disable pull refresh.
	private RelativeLayout mHeaderContent;
	private TextView mHeaderTime;
	private int mHeaderHeight;

	private LinearLayout mFooterLayout;
	private XFooterView mFooterView;
	private boolean mIsFooterReady = false;

	private boolean mEnablePullRefresh = true;
	private boolean mPullRefreshing = false;

	private boolean mEnablePullLoad = true;
	private boolean mEnableAutoLoad = false;
	private boolean mPullLoading = false;

	// total list items, used to detect is at the bottom of ListView
	private int mTotalItemCount;
	private boolean mNeedScrool;

	public SwipeListView(Context context)
	{
		this(context, null);
		initWithContext(context);
	}

	public SwipeListView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
		initWithContext(context);
	}

	public SwipeListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.swipelistviewstyle);
		// 获取自定义属性和默认值
		mRightViewWidth = (int) mTypedArray.getDimension(
				R.styleable.swipelistviewstyle_right_width,
				Tools.dip2px(context, 170));
		mTypedArray.recycle();
		initWithContext(context);
	}

	private void initWithContext(Context context)
	{
		mScroller = new Scroller(context, new DecelerateInterpolator());
		super.setOnScrollListener(this);

		// init header view
		mHeader = new XHeaderView(context);
		mHeaderContent = (RelativeLayout) mHeader
				.findViewById(R.id.header_content);
		mHeaderTime = (TextView) mHeader.findViewById(R.id.header_hint_time);
		addHeaderView(mHeader);

		// init footer view
		mFooterView = new XFooterView(context);
		mFooterLayout = new LinearLayout(context);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.CENTER;
		mFooterLayout.addView(mFooterView, params);

		// init header height
		ViewTreeObserver observer = mHeader.getViewTreeObserver();
		if (null != observer)
		{
			observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener()
			{
				@SuppressWarnings("deprecation")
				@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
				@Override
				public void onGlobalLayout()
				{
					mHeaderHeight = mHeaderContent.getHeight();
					ViewTreeObserver observer = getViewTreeObserver();

					if (null != observer)
					{
						if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
						{
							observer.removeGlobalOnLayoutListener(this);
						} else
						{
							observer.removeOnGlobalLayoutListener(this);
						}
					}
				}
			});
		}
	}

	@Override
	public void setAdapter(ListAdapter adapter)
	{
		// make sure XFooterView is the last footer view, and only add once.
		if (!mIsFooterReady)
		{
			mIsFooterReady = true;
			addFooterView(mFooterLayout);
		}

		super.setAdapter(adapter);
	}

	/**
	 * Enable or disable pull down refresh feature.
	 * 
	 * @param enable
	 */
	public void setPullRefreshEnable(boolean enable)
	{
		mEnablePullRefresh = enable;

		// disable, hide the content
		mHeaderContent.setVisibility(enable ? View.VISIBLE : View.INVISIBLE);
	}

	/**
	 * Enable or disable pull up load more feature.
	 * 
	 * @param enable
	 */
	public void setPullLoadEnable(boolean enable)
	{
		mEnablePullLoad = enable;

		if (!mEnablePullLoad)
		{
			mFooterView.setBottomMargin(0);
			mFooterView.hide();
			mFooterView.setPadding(0, 0, 0, mFooterView.getHeight() * (-1));
			mFooterView.setOnClickListener(null);

		} else
		{
			mPullLoading = false;
			mFooterView.setPadding(0, 0, 0, 0);
			mFooterView.show();
			mFooterView.setState(XFooterView.STATE_NORMAL);
			// both "pull up" and "click" will invoke load more.
			mFooterView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					startLoadMore();
				}
			});
		}
	}

	/**
	 * Enable or disable auto load more feature when scroll to bottom.
	 * 
	 * @param enable
	 */
	public void setAutoLoadEnable(boolean enable)
	{
		mEnableAutoLoad = enable;
	}

	/**
	 * Stop refresh, reset header view.
	 */
	public void stopRefresh()
	{
		if (mPullRefreshing)
		{
			mPullRefreshing = false;
			resetHeaderHeight();
		}
	}

	/**
	 * Stop load more, reset footer view.
	 */
	public void stopLoadMore()
	{
		if (mPullLoading)
		{
			mPullLoading = false;
			mFooterView.setState(XFooterView.STATE_NORMAL);
		}
	}

	/**
	 * Set last refresh time
	 * 
	 * @param time
	 */
	public void setRefreshTime(String time)
	{
		mHeaderTime.setText(time);
	}

	/**
	 * Set listener.
	 * 
	 * @param listener
	 */
	public void setXListViewListener(IXListViewListener listener)
	{
		mListener = listener;
	}

	/**
	 * Auto call back refresh.
	 */
	public void autoRefresh()
	{
		mHeader.setVisibleHeight(mHeaderHeight);

		if (mEnablePullRefresh && !mPullRefreshing)
		{
			// update the arrow image not refreshing
			if (mHeader.getVisibleHeight() > mHeaderHeight)
			{
				mHeader.setState(XHeaderView.STATE_READY);
			} else
			{
				mHeader.setState(XHeaderView.STATE_NORMAL);
			}
		}

		mPullRefreshing = true;
		mHeader.setState(XHeaderView.STATE_REFRESHING);
		refresh();
	}

	private void invokeOnScrolling()
	{
		if (mScrollListener instanceof OnXScrollListener)
		{
			OnXScrollListener listener = (OnXScrollListener) mScrollListener;
			listener.onXScrolling(this);
		}
	}

	private void updateHeaderHeight(float delta)
	{
		mHeader.setVisibleHeight((int) delta + mHeader.getVisibleHeight());

		if (mEnablePullRefresh && !mPullRefreshing)
		{
			// update the arrow image unrefreshing
			if (mHeader.getVisibleHeight() > mHeaderHeight)
			{
				mHeader.setState(XHeaderView.STATE_READY);
			} else
			{
				mHeader.setState(XHeaderView.STATE_NORMAL);
			}
		}

		// scroll to top each time
		setSelection(0);
	}

	private void resetHeaderHeight()
	{
		int height = mHeader.getVisibleHeight();
		if (height == 0)
			return;

		// refreshing and header isn't shown fully. do nothing.
		if (mPullRefreshing && height <= mHeaderHeight)
			return;

		// default: scroll back to dismiss header.
		int finalHeight = 0;
		// is refreshing, just scroll back to show all the header.
		if (mPullRefreshing && height > mHeaderHeight)
		{
			finalHeight = mHeaderHeight;
		}

		mScrollBack = SCROLL_BACK_HEADER;
		mScroller.startScroll(0, height, 0, finalHeight - height,
				SCROLL_DURATION);

		// trigger computeScroll
		invalidate();
	}

	private void updateFooterHeight(float delta)
	{
		int height = mFooterView.getBottomMargin() + (int) delta;

		if (mEnablePullLoad && !mPullLoading)
		{
			if (height > PULL_LOAD_MORE_DELTA)
			{
				// height enough to invoke load more.
				mFooterView.setState(XFooterView.STATE_READY);
			} else
			{
				mFooterView.setState(XFooterView.STATE_NORMAL);
			}
		}

		mFooterView.setBottomMargin(height);

		// scroll to bottom
		// setSelection(mTotalItemCount - 1);
	}

	private void resetFooterHeight()
	{
		int bottomMargin = mFooterView.getBottomMargin();

		if (bottomMargin > 0)
		{
			mScrollBack = SCROLL_BACK_FOOTER;
			mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
					SCROLL_DURATION);
			invalidate();
		}
	}

	private void startLoadMore()
	{
		mPullLoading = true;
		mFooterView.setState(XFooterView.STATE_LOADING);
		loadMore();
	}

	/**
	 * return true, deliver to listView. return false, deliver to child. if
	 * move, return true
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev)
	{
		if (mNeedScrool)
		{
			float lastX = ev.getX();
			float lastY = ev.getY();
			switch (ev.getAction())
			{
			case MotionEvent.ACTION_DOWN:
				mIsHorizontal = null;
				mFirstX = lastX;
				mFirstY = lastY;
				int motionPosition = pointToPosition((int) mFirstX,
						(int) mFirstY);

				// Log.d("SwipeListView", "外面的 = " + motionPosition);
				if (motionPosition < 0 /*
										 * || motionPosition == getChildCount()
										 * -1
										 */)
				{
					return false;
				} else if (motionPosition >= 0)
				{
					View currentItemView = getChildAt(motionPosition
							- getFirstVisiblePosition());
					mPreItemView = mCurrentItemView;
					mCurrentItemView = currentItemView;
					Log.d("SwipeListView", "motionPosition = " + motionPosition);
				}
				break;

			case MotionEvent.ACTION_MOVE:
				float dx = lastX - mFirstX;
				float dy = lastY - mFirstY;
				if (Math.abs(dx) >= 5 && Math.abs(dy) >= 5)
				{
					return true;
				}

				break;

			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				if (mIsShown
						&& (mPreItemView != mCurrentItemView || isHitCurItemLeft(lastX)))
				{
					/**
					 * 情况一： 一个Item的右边布局已经显示， 这时候点击任意一个item,
					 * 那么那个右边布局显示的item隐藏其右边布局
					 */
					hiddenRight(mPreItemView);
					return true;// TODO
				}
				break;
			}
		}

		return super.onInterceptTouchEvent(ev);
	}

	private boolean isHitCurItemLeft(float x)
	{
		return x < getWidth() - mRightViewWidth;
	}

	/**
	 * @param dx
	 * @param dy
	 * @return judge if can judge scroll direction
	 */
	private boolean judgeScrollDirection(float dx, float dy)
	{
		boolean canJudge = true;
		if (Math.abs(dx) > 30 && Math.abs(dx) > 2 * Math.abs(dy))
		{
			mIsHorizontal = true;
		} else if (Math.abs(dy) > 30 && Math.abs(dy) > 2 * Math.abs(dx))
		{
			mIsHorizontal = false;
		} else
		{
			canJudge = false;
		}
		return canJudge;
	}

	/**
	 * return false, can't move any direction. return true, cant't move
	 * vertical, can move horizontal. return super.onTouchEvent(ev), can move
	 * both.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		if (mLastY == -1)
		{
			mLastY = ev.getRawY();
		}
		float lastX = ev.getX();
		float lastY = ev.getY();

		switch (ev.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			mLastY = ev.getRawY();
			if (mIsShown)
			{
				hiddenRight(mPreItemView);
				return false;
			}
			break;

		case MotionEvent.ACTION_MOVE:
			if (mNeedScrool)
			{
				if (mCurrentItemView == null)
				{
					break;
				}
				int motionPosition = pointToPosition((int) mFirstX,
						(int) mFirstY);
				if (motionPosition < 0 /*
										 * || motionPosition == getChildCount()
										 * -1
										 */)
				{
					break;
				}
				float dx = lastX - mFirstX;
				float dy = lastY - mFirstY;
				// if (Math.abs(dy) > 0 && mIsShown)
				// {
				// hiddenRight(mPreItemView);
				// return true;// TODO
				// }
				if (mIsHorizontal == null)
				{
					if (!judgeScrollDirection(dx, dy))
					{
						break;
					}
				}

				if (mIsHorizontal)
				{
					if (mIsShown && mPreItemView != mCurrentItemView)
					{
						/**
						 * 情况二： 一个Item的右边布局已经显示，
						 * 这时候左右滑动另外一个item,那个右边布局显示的item隐藏其右边布局
						 * 向左滑动只触发该情况，向右滑动还会触发情况五
						 */
						hiddenRight(mPreItemView);
						return true;// TODO
					}

					if (mIsShown && mPreItemView == mCurrentItemView)
					{
						dx = dx - mRightViewWidth;
					}
					if (dx < 0 && dx > -mRightViewWidth)
					{
						mCurrentItemView.scrollTo((int) (-dx), 0);
					}
					return true;
				} else
				{
					if (mIsShown)
					{
						/**
						 * 情况三： 一个Item的右边布局已经显示，
						 * 这时候上下滚动ListView,那么那个右边布局显示的item隐藏其右边布局
						 */
						hiddenRight(mPreItemView);
						return true;// TODO
					}
				}
			}

			final float deltaY = ev.getRawY() - mLastY;
			mLastY = ev.getRawY();
			if (getFirstVisiblePosition() == 0
					&& (mHeader.getVisibleHeight() > 0 || deltaY > 0))
			{
				// the first item is showing, header has shown or pull down.
				updateHeaderHeight(deltaY / OFFSET_RADIO);
				invokeOnScrolling();

			} else if (getLastVisiblePosition() == mTotalItemCount - 1
					&& (mFooterView.getBottomMargin() > 0 || deltaY < 0))
			{
				// last item, already pulled up or want to pull up.
				updateFooterHeight(-deltaY / OFFSET_RADIO);
			}
			break;

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			if (mNeedScrool)
			{
				if (mCurrentItemView == null)
				{
					break;
				}
				int motionPosition = pointToPosition((int) mFirstX,
						(int) mFirstY);
				if (motionPosition < 0 /*
										 * || motionPosition == getChildCount()
										 * -1
										 */)
				{
					break;
				}
				clearPressedState();
				if (mIsShown)
				{
					/**
					 * 情况四： 一个Item的右边布局已经显示，
					 * 这时候左右滑动当前一个item,那个右边布局显示的item隐藏其右边布局
					 */
					hiddenRight(mPreItemView);
					return true;// TODO
				}

				if (mIsHorizontal != null && mIsHorizontal)
				{
					if (mFirstX - lastX > mRightViewWidth / 2)
					{
						showRight(mCurrentItemView);
					} else
					{
						/**
						 * 情况五：
						 * <p>
						 * 向右滑动一个item,且滑动的距离超过了右边View的宽度的一半，隐藏之。
						 */
						hiddenRight(mCurrentItemView);
						return true;// TODO
					}
					return true;
				}
			}

		default:
			// reset
			mLastY = -1;
			if (getFirstVisiblePosition() == 0)
			{
				// invoke refresh
				if (mEnablePullRefresh
						&& mHeader.getVisibleHeight() > mHeaderHeight)
				{
					mPullRefreshing = true;
					mHeader.setState(XHeaderView.STATE_REFRESHING);
					refresh();
				}

				resetHeaderHeight();

			} else if (getLastVisiblePosition() == mTotalItemCount - 1)
			{
				// invoke load more.
				if (mEnablePullLoad
						&& mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA)
				{
					startLoadMore();
				}
				resetFooterHeight();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	private void clearPressedState()
	{
		if (mCurrentItemView == null)
		{
			return;
		}
		mCurrentItemView.setPressed(false);
		setPressed(false);
		refreshDrawableState();
	}

	private void showRight(View view)
	{

		Message msg = new MoveHandler().obtainMessage();
		msg.obj = view;
		msg.arg1 = view.getScrollX();
		msg.arg2 = mRightViewWidth;
		msg.sendToTarget();

		mIsShown = true;
	}

	private void hiddenRight(View view)
	{
		if (mCurrentItemView == null || view == null)
		{
			return;
		}
		Message msg = new MoveHandler().obtainMessage();//
		msg.obj = view;
		msg.arg1 = view.getScrollX();
		msg.arg2 = 0;
		msg.sendToTarget();
		mIsShown = false;
	}

	/**
	 * show or hide right layout animation
	 */
	@SuppressLint("HandlerLeak")
	class MoveHandler extends Handler
	{
		int stepX = 0;
		int fromX;
		int toX;
		View view;
		private boolean mIsInAnimation = false;

		private void animatioOver()
		{
			mIsInAnimation = false;
			stepX = 0;
		}

		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			if (stepX == 0)
			{
				if (mIsInAnimation)
				{
					return;
				}
				mIsInAnimation = true;
				view = (View) msg.obj;
				fromX = msg.arg1;
				toX = msg.arg2;
				stepX = (int) ((toX - fromX) * mDurationStep * 1.0 / mDuration);
				if (stepX < 0 && stepX > -1)
				{
					stepX = -1;
				} else if (stepX > 0 && stepX < 1)
				{
					stepX = 1;
				}
				if (Math.abs(toX - fromX) < 10)
				{
					view.scrollTo(toX, 0);
					animatioOver();
					return;
				}
			}

			fromX += stepX;
			boolean isLastStep = (stepX > 0 && fromX > toX)
					|| (stepX < 0 && fromX < toX);
			if (isLastStep)
			{
				fromX = toX;
			}
			view.scrollTo(fromX, 0);
			invalidate();
			if (!isLastStep)
			{
				this.sendEmptyMessageDelayed(0, mDurationStep);
			} else
			{
				animatioOver();
			}
		}
	}

	public int getRightViewWidth()
	{
		return mRightViewWidth;
	}

	public void setRightViewWidth(int mRightViewWidth)
	{
		this.mRightViewWidth = mRightViewWidth;
	}

	public void deleteItem(View v)
	{
		hiddenRight(v);
	}

	public int getHorizontal()
	{
		return mRightViewWidth;
	}

	@Override
	public void computeScroll()
	{
		if (mScroller.computeScrollOffset())
		{
			if (mScrollBack == SCROLL_BACK_HEADER)
			{
				mHeader.setVisibleHeight(mScroller.getCurrY());
			} else
			{
				mFooterView.setBottomMargin(mScroller.getCurrY());
			}

			postInvalidate();
			invokeOnScrolling();
		}

		super.computeScroll();
	}

	@Override
	public void setOnScrollListener(OnScrollListener l)
	{
		mScrollListener = l;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		if (mScrollListener != null)
		{
			mScrollListener.onScrollStateChanged(view, scrollState);
		}

		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
		{
			if (mEnableAutoLoad && getLastVisiblePosition() == getCount() - 1)
			{
				startLoadMore();
			}
		}

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount)
	{
		// send to user's listener
		mTotalItemCount = totalItemCount;
		if (mScrollListener != null)
		{
			mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
					totalItemCount);
		}

	}

	private void refresh()
	{
		if (mEnablePullRefresh && null != mListener)
		{
			mListener.onRefresh();
		}
	}

	private void loadMore()
	{
		if (mEnablePullLoad && null != mListener)
		{
			mListener.onLoadMore();
		}
	}

	/**
	 * You can listen ListView.OnScrollListener or this one. it will invoke
	 * onXScrolling when header/footer scroll back.
	 */
	public interface OnXScrollListener extends OnScrollListener
	{
		public void onXScrolling(View view);
	}

	public interface IXListViewListener
	{
		public void onRefresh();

		public void onLoadMore();
	}

	/**
	 * hide foot
	 */
	public void hideFoot()
	{
		mFooterView.hide();
	}

	/**
	 * show foot
	 */
	public void showFoot()
	{
		mFooterView.show();
	}

	public void setisNeedSwipe(boolean flag)
	{
		mNeedScrool = flag;
	}
}
