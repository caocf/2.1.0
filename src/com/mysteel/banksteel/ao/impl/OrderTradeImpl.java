package com.mysteel.banksteel.ao.impl;

import android.content.Context;

import com.mysteel.banksteel.ao.DefaultAOCallBack;
import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.dao.GetDataDAO;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.ConsultOrderData;
import com.mysteel.banksteel.entity.MatchBuyData;
import com.mysteel.banksteel.entity.OrderDetailData;
import com.mysteel.banksteel.entity.PageOrderData;
import com.mysteel.banksteel.entity.RegisterData;
import com.mysteel.banksteel.entity.TodayResourceCountData;
import com.mysteel.banksteel.entity.UserDetailsCountData;
import com.mysteel.banksteel.view.interfaceview.IOrderTradeView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 订单 匹配拉取数据的方法接口的实现类
 *
 * @author 作者 zoujian
 * @date 创建时间：2015-5-18 下午8:55:55
 */
public class OrderTradeImpl implements IOrderTrade
{

    private IOrderTradeView viewInterface;
    private Context context;
    private GetDataDAO<BaseData> getBaseDao;
    private GetDataDAO<PageOrderData> getPageOrderDao;
    private GetDataDAO<MatchBuyData> getMatchBuyDao;
    private GetDataDAO<RegisterData> getCreateOrderDao;
    private GetDataDAO<OrderDetailData> getOrderDetailDao;
    private GetDataDAO<BaseData> getIntentionDao;
    private GetDataDAO<TodayResourceCountData> getTodayResourceCount;
    private GetDataDAO<UserDetailsCountData> getUserDetailsCount;
    private GetDataDAO<BaseData> getHuanXinRegister;
    private GetDataDAO<ConsultOrderData> getConsultOrder;
    public ArrayList<String> Tags = new ArrayList<String>();

    public OrderTradeImpl(Context context, IOrderTradeView viewInterface)
    {
        this.context = context;
        this.viewInterface = viewInterface;
    }

    @Override
    public void getMatchBuy(String url, String request_tag)
    {
        if (getMatchBuyDao == null)
        {
            getMatchBuyDao = new GetDataDAO<MatchBuyData>(context,
                    MatchBuyData.class, new DefaultAOCallBack<MatchBuyData>(
                    viewInterface, context)
            {

                @Override
                public void dealWithSuccess(MatchBuyData obj)
                {
                    viewInterface.updateView(obj);
                    viewInterface.isShowDialog(false);
                }
            });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getMatchBuyDao.getData(url, request_tag);
    }

    @Override
    public void getHumanServe(String url, String request_tag)
    {
        if (getBaseDao == null)
        {
            getBaseDao = new GetDataDAO<BaseData>(context, BaseData.class,
                    new DefaultAOCallBack<BaseData>(viewInterface, context)
                    {

                        @Override
                        public void dealWithSuccess(BaseData obj)
                        {
                            viewInterface.updateView(obj);
                            viewInterface.isShowDialog(false);
                        }
                    });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getBaseDao.getData(url, request_tag);
    }

    @Override
    public void getPageOrder(String url, String request_tag)
    {
        if (getPageOrderDao == null)
        {
            getPageOrderDao = new GetDataDAO<PageOrderData>(context,
                    PageOrderData.class, new DefaultAOCallBack<PageOrderData>(
                    viewInterface, context)
            {

                @Override
                public void dealWithSuccess(PageOrderData obj)
                {
                    viewInterface.updateView(obj);
                    viewInterface.isShowDialog(false);
                }
            });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getPageOrderDao.getData(url, request_tag);
    }

    @Override
    public void getCreateOrder(String url, String request_tag)
    {
        if (getCreateOrderDao == null)
        {
            getCreateOrderDao = new GetDataDAO<RegisterData>(context,
                    RegisterData.class, new DefaultAOCallBack<RegisterData>(
                    viewInterface, context)
            {

                @Override
                public void dealWithSuccess(RegisterData obj)
                {
                    viewInterface.updateView(obj);
                    viewInterface.isShowDialog(false);
                }
            });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getCreateOrderDao.getData(url, request_tag);
    }

    @Override
    public void getOrderDetail(String url, String request_tag)
    {
        if (getOrderDetailDao == null)
        {
            getOrderDetailDao = new GetDataDAO<OrderDetailData>(context,
                    OrderDetailData.class,
                    new DefaultAOCallBack<OrderDetailData>(viewInterface,
                            context)
                    {

                        @Override
                        public void dealWithSuccess(OrderDetailData obj)
                        {
                            viewInterface.updateView(obj);
                            viewInterface.isShowDialog(false);
                        }
                    });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getOrderDetailDao.getData(url, request_tag);
    }

    @Override
    public void getCreateIntentionOrder(String url, String request_tag)
    {
        if (getIntentionDao == null)
        {
            getIntentionDao = new GetDataDAO<BaseData>(context,
                    BaseData.class,
                    new DefaultAOCallBack<BaseData>(viewInterface,
                            context)
                    {
                        @Override
                        public void dealWithSuccess(BaseData obj)
                        {
                            viewInterface.updateView(obj);
                            viewInterface.isShowDialog(false);
                        }
                    });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getIntentionDao.getData(url, request_tag);
    }

    @Override
    public void getTodayResourceCount(String url, String request_tag)
    {
        if (getTodayResourceCount == null)
        {
            getTodayResourceCount = new GetDataDAO<TodayResourceCountData>(context,
                    TodayResourceCountData.class,
                    new DefaultAOCallBack<TodayResourceCountData>(viewInterface,
                            context)
                    {
                        @Override
                        public void dealWithSuccess(TodayResourceCountData obj)
                        {
                            viewInterface.updateView(obj);
                            viewInterface.isShowDialog(false);
                        }
                    });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getTodayResourceCount.getData(url, request_tag);
    }

    @Override
    public void getUserDetailsCount(String url, String request_tag)
    {
        if (getUserDetailsCount == null)
        {
            getUserDetailsCount = new GetDataDAO<UserDetailsCountData>(context,
                    UserDetailsCountData.class,
                    new DefaultAOCallBack<UserDetailsCountData>(viewInterface,
                            context)
                    {
                        @Override
                        public void dealWithSuccess(UserDetailsCountData obj)
                        {
                            viewInterface.updateView(obj);
                            viewInterface.isShowDialog(false);
                        }
                    });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getUserDetailsCount.getData(url, request_tag);
    }

    @Override
    public void getHuanXinRegister(String url, String request_tag)
    {
        if (getHuanXinRegister == null)
        {
            getHuanXinRegister = new GetDataDAO<BaseData>(context,
                    BaseData.class,
                    new DefaultAOCallBack<BaseData>(viewInterface,
                            context)
                    {
                        @Override
                        public void dealWithSuccess(BaseData obj)
                        {
                            viewInterface.updateView(obj);
                            viewInterface.isShowDialog(false);
                        }
                    });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getHuanXinRegister.getData(url, request_tag);
    }

    @Override
    public void getConsultOrder(String url, String request_tag)
    {
        if (getConsultOrder == null)
        {
            getConsultOrder = new GetDataDAO<ConsultOrderData>(context,
                    ConsultOrderData.class,
                    new DefaultAOCallBack<ConsultOrderData>(viewInterface,
                            context)
                    {
                        @Override
                        public void dealWithSuccess(ConsultOrderData obj)
                        {
                            viewInterface.updateView(obj);
                            viewInterface.isShowDialog(false);
                        }
                    });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getConsultOrder.getData(url, request_tag);
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
