package com.mysteel.banksteel.ao.impl;

import android.content.Context;

import com.mysteel.banksteel.ao.DefaultAOCallBack;
import com.mysteel.banksteel.ao.ITransportManager;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.dao.GetDataDAO;
import com.mysteel.banksteel.entity.LogisticsOrderData;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.TransportDetailsData;
import com.mysteel.banksteel.entity.TransportDetailsData;
import com.mysteel.banksteel.entity.TransportQuotData;
import com.mysteel.banksteel.view.interfaceview.ITransportManagerView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 物流管理类
 * @author 作者 wushaoge
 * @date 创建时间：2015年8月17日09:04:13
 */
public class TransportManagerImpl implements ITransportManager
{

    private ITransportManagerView interfaceView;
    private Context context;
    private GetDataDAO<TransportQuotData> getQuotData;
    private GetDataDAO<LogisticsOrderData> getOrderByUserId;
	private GetDataDAO<BaseData> getCreateOrderData;
    private GetDataDAO<BaseData> getCustomerCareData;
    private GetDataDAO<TransportDetailsData> getTransportDetails;
    private GetDataDAO<BaseData> getAppraiseData;


    public ArrayList<String> Tags = new ArrayList<String>();

    public TransportManagerImpl(Context context)
    {
        this.context = context;
        interfaceView = (ITransportManagerView) context;
    }

	public TransportManagerImpl(Context context, ITransportManagerView interfaceView)
	{
		this.context = context;
		this.interfaceView = interfaceView;
	}
//    public TransportManagerImpl(Context context, ITransportManagerView viewInterface)
//    {
//        this.context = context;
//        this.interfaceView = viewInterface;
//    }


    @Override
    public void getQueryTransportQuot(String url, String request_tag)
    {
        if (getQuotData == null)
        {
            getQuotData = new GetDataDAO<TransportQuotData>(context,
                    TransportQuotData.class, new DefaultAOCallBack<TransportQuotData>(
                    interfaceView, context)
            {
                @Override
                public void dealWithSuccess(TransportQuotData obj)
                {
                    interfaceView.updateView(obj);
                }
            });
        }
        interfaceView.isShowDialog(true);
        setTagForRequest(request_tag);
        getQuotData.getData(url, request_tag);
    }

	@Override
	public void getCreateTransportOrder(String url, String request_tag) {
		if (getCreateOrderData == null)
		{
			getCreateOrderData = new GetDataDAO<BaseData>(context,
					BaseData.class, new DefaultAOCallBack<BaseData>(
					interfaceView, context)
			{
				@Override
				public void dealWithSuccess(BaseData obj) {
					interfaceView.updateView(obj);
				}
			});
		}
		interfaceView.isShowDialog(true);
		setTagForRequest(request_tag);
		getCreateOrderData.getData(url, request_tag);
	}

    @Override
    public void getTransportDetails(String url, String request_tag)
    {
        if (getTransportDetails == null)
        {
            getTransportDetails = new GetDataDAO<TransportDetailsData>(context,
                    TransportDetailsData.class, new DefaultAOCallBack<TransportDetailsData>(
                    interfaceView, context)
            {
                @Override
                public void dealWithSuccess(TransportDetailsData obj)
                {
                    interfaceView.updateView(obj);
                }
            });
        }
        interfaceView.isShowDialog(true);
        setTagForRequest(request_tag);
        getTransportDetails.getData(url, request_tag);
    }

    @Override
    public void getOrderByUserId(String url, String request_tag)
    {
        if (getOrderByUserId == null)
        {
            getOrderByUserId = new GetDataDAO<LogisticsOrderData>(context,
                    LogisticsOrderData.class, new DefaultAOCallBack<LogisticsOrderData>(
                    interfaceView, context)
            {
                @Override
                public void dealWithSuccess(LogisticsOrderData obj)
                {
                    interfaceView.updateView(obj);
                }
            });
        }
        interfaceView.isShowDialog(true);
        setTagForRequest(request_tag);
        getOrderByUserId.getData(url, request_tag);
    }

    @Override
    public void getCustomerCare(String url, String request_tag) {
        if (getCustomerCareData == null)
        {
            getCustomerCareData = new GetDataDAO<BaseData>(context,
                    BaseData.class, new DefaultAOCallBack<BaseData>(
                    interfaceView, context)
            {
                @Override
                public void dealWithSuccess(BaseData obj) {
                    interfaceView.updateView(obj);
                }
            });
        }
        interfaceView.isShowDialog(true);
        setTagForRequest(request_tag);
        getCustomerCareData.getData(url, request_tag);
    }

    @Override
    public void getAppraise(String url, String request_tag) {
        if (getAppraiseData == null)
        {
            getAppraiseData = new GetDataDAO<BaseData>(context,
                    BaseData.class, new DefaultAOCallBack<BaseData>(
                    interfaceView, context)
            {
                @Override
                public void dealWithSuccess(BaseData obj) {
                    interfaceView.updateView(obj);
                }
            });
        }
        interfaceView.isShowDialog(true);
        setTagForRequest(request_tag);
        getAppraiseData.getData(url, request_tag);
    }

    /**
     * 将当前的Request tag保存起来
     */
    public void setTagForRequest(String tag)
    {
        if (!Tags.isEmpty())
        {
            Iterator<String> it = Tags.iterator();
            while (it.hasNext())
            {
                String sTag = it.next();
                if (!it.equals(tag))
                {// 若list中没有tag，添加
                    if (!Tags.contains(sTag))
                    {
                        Tags.add(tag);
                    }
                }
            }

        } else
        {
            Tags.add(tag);
        }
    }

    @Override
    public void finishRequest()
    {
        for (String tag : Tags)
        {
            BankSteelApplication.requestQueue.cancelAll(tag);
        }
    }


}
