package com.mysteel.banksteel.view.adapters;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mysteel.banksteel.entity.ImageBean;
import com.mysteel.banksteel.util.ImageLoader;
import com.mysteel.banksteel.util.ImageLoader.OnCallBackListener;
import com.mysteel.banksteel.view.activity.AlbumImagesActivity;
import com.mysteel.banksteeltwo.R;

/**
 * 相册适配器
 * 
 * @author:huoguodong
 * @date：015-4-22 上午11:26:43
 */
public class AlbumImagesAdapter extends BaseAdapter
{

	private Context context;
	private GridView gridView;
	private LayoutInflater inflater;

	private List<ImageBean> allImagesList;
	private ArrayList<String> selectedImageList;

	private int count; // 选择的图片的数量
	private RelativeLayout.LayoutParams param; // 相册中图片的布局

	private OnCheckboxChangedListener listener;

	public void setListener(OnCheckboxChangedListener listener)
	{
		this.listener = listener;
	}

	public AlbumImagesAdapter(Context context, GridView gridView, int columWidht)
	{
		this.context = context;
		this.gridView = gridView;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		allImagesList = new ArrayList<ImageBean>();
		selectedImageList = new ArrayList<String>();
		param = new RelativeLayout.LayoutParams(columWidht, columWidht);
	}

	@Override
	public int getCount()
	{
		if (allImagesList != null)
		{
			return allImagesList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position)
	{
		return position;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	public void updateData(List<ImageBean> imagesPath, int count)
	{
		this.allImagesList = imagesPath;
		this.count = count;
		this.notifyDataSetChanged();
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		MyOnClickListener myListener = null;
		Holder holder = null;
		final String path = allImagesList.get(position).getImagePath();
		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.gridview_image_item, null);
			holder = new Holder();
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.iv_item);
			holder.select = (ImageView) convertView
					.findViewById(R.id.iv_select);
			convertView.setTag(holder);
		} else
		{
			holder = (Holder) convertView.getTag();
		}

		holder.imageView.setTag(path);

		/***/
		Bitmap bitmap = ImageLoader.getInstance().loadImage(path,
				new OnCallBackListener()
				{
					@Override
					public void setBitmap(Bitmap bitmap, String url)
					{
						ImageView imageView = (ImageView) gridView
								.findViewWithTag(url);
						if (imageView != null && bitmap != null)
						{
							imageView.setImageBitmap(bitmap);
						}
					}
				});

		if (bitmap != null)
		{
			holder.imageView.setImageBitmap(bitmap);
		} else
		{
			holder.imageView.setImageResource(R.drawable.pictures_no);
		}

		if (allImagesList.get(position).getIsSelected())
		{
			holder.select.setImageResource(R.drawable.yes);
			holder.imageView.setColorFilter(Color.GRAY, Mode.MULTIPLY);
		} else
		{
			holder.select.setImageResource(R.drawable.no);
			holder.imageView.clearColorFilter();
		}

		holder.imageView.setLayoutParams(param);

		myListener = new MyOnClickListener(position, holder.imageView);
		holder.select.setOnClickListener(myListener);

		return convertView;
	}

	class Holder
	{
		public ImageView imageView;
		public ImageView select;
	}

	/**
	 * 把最新的图片状态数据传递到相册页面（ShowImagesActivity），比如图片是否选中，选择图片地址，选中图片数量，可选图片数量提示
	 */
	public interface OnCheckboxChangedListener
	{
		void onCheck(ArrayList<String> selectedImageList);
	}

	public class MyOnClickListener implements OnClickListener
	{

		int position;
		ImageView image;

		public MyOnClickListener(int position, ImageView image)
		{
			this.position = position;
			this.image = image;
		}

		@Override
		public void onClick(View v)
		{
			/** 如果已经被选中,则删除该图片地址 */
			if (allImagesList.get(position).getIsSelected())
			{
				count--;
				allImagesList.get(position).setIsSelected(false);
				selectedImageList.remove(allImagesList.get(position)
						.getImagePath());
				((ImageView) v).setImageResource(R.drawable.no);
				image.clearColorFilter();
			} else
			{
				image.setColorFilter(Color.GRAY, Mode.MULTIPLY);
				/** 图片数量没有达到上限 */
				if (count < AlbumImagesActivity.MOST_PICTURE_COUNT)
				{
					count++;
					allImagesList.get(position).setIsSelected(true);
					selectedImageList.add(allImagesList.get(position)
							.getImagePath());
					((ImageView) v).setImageResource(R.drawable.yes);
				}
				/** 图片数量达到上限 */
				else
				{
					Toast.makeText(
							context,
							"你最多能选择" + AlbumImagesActivity.MOST_PICTURE_COUNT
									+ "张图片", Toast.LENGTH_LONG).show();
				}
			}

			listener.onCheck(selectedImageList);
		}
	}
}
