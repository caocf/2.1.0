package com.mysteel.banksteel.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mysteel.banksteel.ao.impl.SystemManagerImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.DeleteMessageData;
import com.mysteel.banksteel.entity.MessageCenterData;
import com.mysteel.banksteel.entity.MessageCenterData.Data.Datas;
import com.mysteel.banksteel.entity.SignData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.activity.FindFoodActivity;
import com.mysteel.banksteel.view.activity.LocalQuotedPriceActivity;
import com.mysteel.banksteel.view.adapters.MsgDingdanAdapter;
import com.mysteel.banksteel.view.interfaceview.IListViewInterface;
import com.mysteel.banksteel.view.interfaceview.ISystemManagerView;
import com.mysteel.banksteel.view.ui.ShowDialog;
import com.mysteel.banksteel.view.ui.XListView;
import com.mysteel.banksteel.view.ui.XListView.IXListViewListener;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/**
 * 订单消息
 * @author 作者 wushaoge
 * @date 创建时间：2015年7月31日10:10:19
 */
public class MsgDingdanFragment extends BaseFragment implements OnClickListener,IListViewInterface,IXListViewListener, ISystemManagerView
{
	
	private XListView xlistview;
	
	private LinearLayout ll_del_layout; //底部删除layout
	private LinearLayout ll_selall_layout; //全选
	private ImageView iv_ischeck; //是否全选的图标
	private TextView tv_ischeck; //是否全选的文字
	private TextView tv_ok; //确认按钮
	
	private LinearLayout ll_nullmsg; //没数据时候显示的内容
	private TextView tv_qiugou; //我要求购
	private TextView tv_qiangdan; //我要抢单
	

	private SystemManagerImpl systemManagerImpl;
	private MsgDingdanAdapter msgAdapter;
	
	private boolean isSelAll = false; //是否全选
	
	boolean toggle = false; //是否显示选择按钮
	
	private String resultID = ""; //要删除的id
	
	boolean tag = true; //true表示没有 false表示有

	/** 进度条 */
	private ProgressBar progressBar;

	private ArrayList<Datas> datas;
	private String userId;
	private String page = "1";
	private String size = "10";
	private String type = "1"; //1表示订单消息
	private MessageCenterData messageCenterData;
	
	private View currentView;
	
	public View getCurrentView()
	{
		return currentView;
	}
	
	public ArrayList<Datas> getDatas()
	{
		return datas;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
    	currentView = inflater.inflate(R.layout.fragment_dingdan_msg, container,
				false);
		EventBus.getDefault().register(this);
		initView();
		initData();
		return currentView;
    }
    
    private void initView()
	{
    	progressBar = (ProgressBar) currentView.findViewById(R.id.pb_progressbar);
    	
    	xlistview = (XListView)currentView.findViewById(R.id.xlistview);
    	xlistview.setXListViewListener(this);
    	
    	ll_del_layout = (LinearLayout) currentView.findViewById(R.id.ll_del_layout);
    	ll_selall_layout = (LinearLayout) currentView.findViewById(R.id.ll_selall_layout);
    	ll_selall_layout.setOnClickListener(this);
    	
    	iv_ischeck = (ImageView) currentView.findViewById(R.id.iv_ischeck);
    	tv_ischeck = (TextView) currentView.findViewById(R.id.tv_ischeck);
    	tv_ok = (TextView) currentView.findViewById(R.id.tv_ok);
    	tv_ok.setOnClickListener(this);
    	
    	ll_nullmsg = (LinearLayout) currentView.findViewById(R.id.ll_nullmsg);
    	tv_qiugou = (TextView) currentView.findViewById(R.id.tv_qiugou);
    	tv_qiangdan = (TextView) currentView.findViewById(R.id.tv_qiangdan);
    	tv_qiugou.setOnClickListener(this);
    	tv_qiangdan.setOnClickListener(this);
    	//初始化
    	xlistview.setVisibility(View.VISIBLE);
    	ll_del_layout.setVisibility(View.GONE);
    	ll_nullmsg.setVisibility(View.GONE);
    	
	}
    
    private void initData()
    {
    	msgAdapter = new MsgDingdanAdapter(getActivity());
    	xlistview.setAdapter(msgAdapter);
    	getMessage();
    }
    
    /**
	 * 拉取消息中心数据
	 */
	private void getMessage()
	{
		systemManagerImpl = new SystemManagerImpl(getActivity(),this);
		userId = SharePreferenceUtil.getString(getActivity(),
				Constants.PREFERENCES_USERID);
		String url = RequestUrl.getInstance(getActivity()).getUrl_pageHistoryMessage(
				userId, page, size, type);
		systemManagerImpl.messageCenter(url,
				Constants.INTERFACE_pageHistoryMessage);

	}
    
    
  

	@Override
	public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_selall_layout://全选
            	toggleSelAll();
                break;
            case R.id.tv_ok://确定
            	delMsg();
                break;
			case R.id.tv_qiugou://我要求购
				startActivity(new Intent(getActivity(), FindFoodActivity.class));
				getActivity().finish();
				break;
			case R.id.tv_qiangdan://我要抢单
				startActivity(new Intent(getActivity(), LocalQuotedPriceActivity.class));
				getActivity().finish();
				break;
            default:
                break;
        }
    }
	
	
	
	@Subscriber(tag = "msgdingdanfragment_current")
	private void setTagFlag(boolean tag)
	{
		this.tag = tag;
	}
	
	@Subscriber(tag = "msgdingdanfragment_edit")
	private void setToggleOpen(boolean toggle)
	{
		this.toggle = toggle;
		msgAdapter.reSetListView(datas, this.toggle);
		for(Datas tempDatas:datas){
			if(this.toggle){
				tempDatas.setSelected(false);
			}
		}
		if(this.toggle){
			isSelAll = false;
			iv_ischeck.setImageResource(R.drawable.msg_uncheck);
			tv_ischeck.setTextColor(getActivity().getResources().getColor(R.color.font_black_two));
			ll_del_layout.setVisibility(View.VISIBLE);
		}else{
			ll_del_layout.setVisibility(View.GONE);
		}
	}
	
    @Subscriber(tag = "msgDingdanAdapter")
	private void getCity(int position)
	{
    	if(datas.get(position).getSelected()==true){
    		datas.get(position).setSelected(false);
    	}else{
    		datas.get(position).setSelected(true);
    	}
    	msgAdapter.reSetListView(datas, this.toggle);
	}
	
	/**
	 * 全选
	 */
	private void toggleSelAll(){
		isSelAll = !isSelAll;
		if(isSelAll){
			iv_ischeck.setImageResource(R.drawable.msg_check);
			tv_ischeck.setTextColor(getActivity().getResources().getColor(R.color.main_imred));
		}else{
			iv_ischeck.setImageResource(R.drawable.msg_uncheck);
			tv_ischeck.setTextColor(getActivity().getResources().getColor(R.color.font_black_two));
		}
		
		for(Datas tempDatas:datas){
			tempDatas.setSelected(isSelAll);
		}
		msgAdapter.reSetListView(datas, this.toggle);
	}
	
	/**
	 * 删除
	 */
	private void delMsg(){
		//计算所有被选中的id
		resultID = "";
		for(Datas tempDatas:datas){
			if(tempDatas.getSelected()){
				resultID += tempDatas.getId()+",";
			}
		}
		if(!TextUtils.isEmpty(resultID)){
			resultID = resultID.substring(0,resultID.length()-1);
			ShowDialog dialog = new ShowDialog(getActivity(), "你确定删除这些信息吗?", new ShowDialog.ICallBack() {
				@Override
				public void requestOK() {
					String url = RequestUrl.getInstance(getActivity()).getUrl_getDeleteMessage(getActivity(),resultID);
					systemManagerImpl.deleteMessage(url,
							Constants.INTERFACE_deleteMessage);
				}
				@Override	
				public void requestCancle() {
					
				}
			});
			dialog.show();
		}else{
			Tools.showToast(getActivity(), "选择不能为空!");
		}
	}

	@Override
	public void updateView(BaseData data) {
		
	}

	@Override
	public void isShowDialog(boolean flag) {
		if (flag)
		{
			progressBar.setVisibility(View.VISIBLE);
			progressBar.setProgress(100);
		} else
		{
			progressBar.setVisibility(View.GONE);
		}
	}

	/**
	 * 下拉刷新
	 */
	@Override
	public void onRefresh()
	{
		String url = RequestUrl.getInstance(getActivity()).getUrl_pageHistoryMessage(
				userId, page, size, type);
		systemManagerImpl.messageCenter(url,
				Constants.INTERFACE_pageHistoryMessage);
	}

	/**
	 * 上拉加载
	 */
	@Override
	public void onLoadMore()
	{
		if (TextUtils.isEmpty(messageCenterData.getData().getPagenum())
				|| TextUtils.isEmpty(messageCenterData.getData().getPage()))
		{
			return;
		}

		if (Integer.parseInt(page) >= Integer.parseInt(messageCenterData
				.getData().getPagenum()))
		{
			onLoad();
			Tools.showToast(getActivity(), "已经到最后一页!");
			return;
		}
		page = Integer.parseInt(page) + 1 + "";

		String url = RequestUrl.getInstance(getActivity()).getUrl_pageHistoryMessage(
				userId, page, size, type);

		systemManagerImpl.messageCenter(url,
				Constants.INTERFACE_pageHistoryMessage);
	}

	@Override
	public void stopUpdate() {
		
	}
	
    @Override
	public void onDestroy()
	{
		super.onDestroy();
		systemManagerImpl.finishRequest();
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void checkUpDate(BaseData data) {
		
	}

	@Override
	public void getHistoryMessage(MessageCenterData data) {
		messageCenterData = data;
		if (messageCenterData.getData().getDatas() != null)
		{
			if ("1".equals(page))
			{
				datas = messageCenterData.getData().getDatas();
				xlistview.setVisibility(View.VISIBLE);
		    	ll_nullmsg.setVisibility(View.GONE);
		    	if(!tag){
					EventBus.getDefault().post(true, "msgdingdanfragment_righttop");
		    	}
			} else
			{
				datas.addAll(messageCenterData.getData().getDatas());
			}
			//判断是否全选
			for(Datas tempDatas:datas){
				tempDatas.setSelected(isSelAll);
			}
			msgAdapter.reSetListView(datas, toggle);
		}else{
			xlistview.setVisibility(View.GONE);
	    	ll_nullmsg.setVisibility(View.VISIBLE);
			EventBus.getDefault().post(false, "msgdingdanfragment_righttop");
		}
		onLoad();
	}
	
	private void onLoad()
	{
		xlistview.stopRefresh();
		xlistview.stopLoadMore();
		xlistview.setRefreshTime(new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.CHINESE).format(new Date()));
	}
	
	
	@Override
	public void getDeleteMessage(DeleteMessageData data) {
		if(!TextUtils.isEmpty(data.getData())&&data.getData().length()>=2){
			//说明删除成功
			String requestIDS = data.getData();
			String[] attrId = requestIDS.substring(0, requestIDS.length()-1).split(",");
			
			//删除消息
		    Iterator<Datas> sListIterator = datas.iterator();  
		    while(sListIterator.hasNext()){  
		    	Datas datasTemp = sListIterator.next();
		    	for(int i = 0;i<attrId.length;i++){
		    		if(datasTemp.getId().equals(attrId[i])){  
			        	sListIterator.remove();  
			        }
		    	}
		    }
		    
		    //判断删除之后datas是否为空
		    if(datas.size()==0){
//		    	xlistview.setVisibility(View.GONE);
//		    	ll_del_layout.setVisibility(View.GONE);
//		    	ll_nullmsg.setVisibility(View.VISIBLE);
//		    	EventBus.getDefault().post(true, "msgdingdanfragment_del");
				page = "1";
				getMessage();
		    }else{
		    	msgAdapter.reSetListView(datas, this.toggle);
		    }
		}
	}

	@Override
	public void getSign(SignData data) {
		
	}

	@Override
	public void getSuggest(BaseData data) {
		
	}

	@Override
	public void getIsPush(BaseData data) {
		
	}
}
