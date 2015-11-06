package com.mysteel.banksteel.ao.impl;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;

import com.mysteel.banksteel.ao.DefaultAOCallBack;
import com.mysteel.banksteel.ao.IBuyCenter;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.dao.GetDataDAO;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.CitysData;
import com.mysteel.banksteel.entity.DetailStanBuyData;
import com.mysteel.banksteel.entity.FoodTypeData;
import com.mysteel.banksteel.entity.HistoryStanBuyData;
import com.mysteel.banksteel.entity.MyQuotData;
import com.mysteel.banksteel.entity.OrderCount;
import com.mysteel.banksteel.entity.PageCustomData;
import com.mysteel.banksteel.entity.RegisterData;
import com.mysteel.banksteel.entity.SpecsAndMaterialData;
import com.mysteel.banksteel.entity.StanQuotDetailsData;
import com.mysteel.banksteel.entity.SteelData;
import com.mysteel.banksteel.entity.SubAreaData;
import com.mysteel.banksteel.entity.UncancelData;
import com.mysteel.banksteel.view.interfaceview.IBuyView;

/**
 * 求购功能接口实现类
 *
 * @author 作者 zoujian
 * @date 创建时间：2015-5-17 下午3:11:42
 */
public class BuyImpl implements IBuyCenter
{

    private Context context;
    private IBuyView viewInterface;
    private GetDataDAO<CitysData> getDataDao;
    private GetDataDAO<SteelData> steelDataDao;
    private GetDataDAO<SpecsAndMaterialData> SpecsAndMaterialDao;
    private GetDataDAO<RegisterData> publishStanBuy;// 实体类与注册页可共用
    private GetDataDAO<BaseData> getBaseDao;
    private GetDataDAO<HistoryStanBuyData> getHistoryDataDao;// 获取历史订单列表
    private GetDataDAO<UncancelData> getUncancelData;// 首页下3条数据
    private GetDataDAO<DetailStanBuyData> getDetailStanBuy;// 我的求购中已报价
    private GetDataDAO<OrderCount> getOrderCountData;// 首页下3条数据
    private GetDataDAO<PageCustomData> getLocalQuotedData;// 同城报价
    private GetDataDAO<PageCustomData> getOpportunityMoreData;// 更多商机
    private GetDataDAO<StanQuotDetailsData> getstanQuotDetailsData;// 买家求购已经报价的卖家列表信息
    private GetDataDAO<BaseData> getSellQuotData;// 我要报价
    private GetDataDAO<MyQuotData> getMyQuotData;// 卖家报价单
    private GetDataDAO<SubAreaData> getSubAreaData;// 获得区
    private GetDataDAO<FoodTypeData> getProductType;// 获得货物类型

    public ArrayList<String> Tags = new ArrayList<String>();

    public BuyImpl(Context context)
    {
        this.context = context;
        this.viewInterface = (IBuyView) context;
    }

    public BuyImpl(Context context, IBuyView viewInterface)
    {
        this.context = context;
        this.viewInterface = viewInterface;
    }

    @Override
    public void getCityList(String url, String request_tag)
    {
        if (getDataDao == null)
        {
            getDataDao = new GetDataDAO<CitysData>(context, CitysData.class,
                    new DefaultAOCallBack<CitysData>(viewInterface, context)
                    {

                        @Override
                        public void dealWithSuccess(CitysData obj)
                        {
                            viewInterface.updateView(obj);
                            viewInterface.isShowDialog(false);
                        }
                    });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getDataDao.getData(url, request_tag);
    }

    @Override
    public void getSpecsAndMaterial(String url, String request_tag)
    {
        if (SpecsAndMaterialDao == null)
        {
            SpecsAndMaterialDao = new GetDataDAO<SpecsAndMaterialData>(context,
                    SpecsAndMaterialData.class,
                    new DefaultAOCallBack<SpecsAndMaterialData>(viewInterface,
                            context)
                    {

                        @Override
                        public void dealWithSuccess(SpecsAndMaterialData obj)
                        {
                            viewInterface.updateView(obj);
                            viewInterface.isShowDialog(false);
                        }
                    });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        SpecsAndMaterialDao.getData(url, request_tag);
    }

    @Override
    public void getSteelData(String url, String request_tag)
    {
        if (steelDataDao == null)
        {
            steelDataDao = new GetDataDAO<SteelData>(context, SteelData.class,
                    new DefaultAOCallBack<SteelData>(viewInterface, context)
                    {

                        @Override
                        public void dealWithSuccess(SteelData obj)
                        {
                            viewInterface.updateView(obj);
                            viewInterface.isShowDialog(false);
                        }
                    });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        steelDataDao.getData(url, request_tag);
    }

    @Override
    public void getPublishStanBuy(String url, String request_tag)
    {
        if (publishStanBuy == null)
        {
            publishStanBuy = new GetDataDAO<RegisterData>(context,
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
        publishStanBuy.getData(url, request_tag);
    }

    @Override
    public void getHistoryStanBuy(String url, String request_tag)
    {
        if (getHistoryDataDao == null)
        {
            getHistoryDataDao = new GetDataDAO<HistoryStanBuyData>(context,
                    HistoryStanBuyData.class, new DefaultAOCallBack<HistoryStanBuyData>(
                    viewInterface, context)
            {

                @Override
                public void dealWithSuccess(HistoryStanBuyData obj)
                {
                    viewInterface.updateView(obj);
                    viewInterface.isShowDialog(false);
                }
            });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getHistoryDataDao.getData(url, request_tag);
    }

    @Override
    public void getCancelStanBuy(String url, String request_tag)
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
    public void getUncancelStanBuy(String url, String request_tag)
    {
        if (getUncancelData == null)
        {
            getUncancelData = new GetDataDAO<UncancelData>(context,
                    UncancelData.class, new DefaultAOCallBack<UncancelData>(
                    viewInterface, context)
            {

                @Override
                public void dealWithSuccess(UncancelData obj)
                {
                    viewInterface.updateView(obj);
                    viewInterface.isShowDialog(false);
                }
            });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getUncancelData.getData(url, request_tag);
    }

    @Override
    public void getDetailStanBuy(String url, String request_tag)
    {
        if (getDetailStanBuy == null)
        {
            getDetailStanBuy = new GetDataDAO<DetailStanBuyData>(context,
                    DetailStanBuyData.class,
                    new DefaultAOCallBack<DetailStanBuyData>(viewInterface,
                            context)
                    {

                        @Override
                        public void dealWithSuccess(DetailStanBuyData obj)
                        {
                            viewInterface.updateView(obj);
                            viewInterface.isShowDialog(false);
                        }
                    });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getDetailStanBuy.getData(url, request_tag);
    }


    @Override
    public void getLocalQuotedBuy(String url, String request_tag)
    {
        if (getLocalQuotedData == null)
        {
            getLocalQuotedData = new GetDataDAO<PageCustomData>(context,
                    PageCustomData.class, new DefaultAOCallBack<PageCustomData>(
                    viewInterface, context)
            {
                @Override
                public void dealWithSuccess(PageCustomData obj)
                {
                    viewInterface.updateView(obj);
                    viewInterface.isShowDialog(false);
                }
            });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getLocalQuotedData.getData(url, request_tag);
    }

    @Override
    public void getOpportunityMoreBuy(String url, String request_tag)
    {
        if (getOpportunityMoreData == null)
        {
            getOpportunityMoreData = new GetDataDAO<PageCustomData>(context,
                    PageCustomData.class, new DefaultAOCallBack<PageCustomData>(
                    viewInterface, context)
            {
                @Override
                public void dealWithSuccess(PageCustomData obj)
                {
                    viewInterface.updateView(obj);
                    viewInterface.isShowDialog(false);
                }
            });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getOpportunityMoreData.getData(url, request_tag);
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

    @Override
    public void getorderCount(String url, String request_tag)
    {
        if (getOrderCountData == null)
        {
            getOrderCountData = new GetDataDAO<OrderCount>(context,
                    OrderCount.class, new DefaultAOCallBack<OrderCount>(
                    viewInterface, context)
            {

                @Override
                public void dealWithSuccess(OrderCount obj)
                {
                    viewInterface.updateView(obj);
                    viewInterface.isShowDialog(false);
                }
            });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getOrderCountData.getData(url, request_tag);

    }

    @Override
    public void getStanQuotDetails(String url, String request_tag)
    {
        if (getstanQuotDetailsData == null)
        {
            getstanQuotDetailsData = new GetDataDAO<StanQuotDetailsData>(context,
                    StanQuotDetailsData.class,
                    new DefaultAOCallBack<StanQuotDetailsData>(viewInterface,
                            context)
                    {

                        @Override
                        public void dealWithSuccess(StanQuotDetailsData obj)
                        {
                            viewInterface.updateView(obj);
                            viewInterface.isShowDialog(false);
                        }
                    });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getstanQuotDetailsData.getData(url, request_tag);
    }

    @Override
    public void getmyQuot(String url, String request_tag)
    {
        if (getMyQuotData == null)
        {
            getMyQuotData = new GetDataDAO<MyQuotData>(context,
                    MyQuotData.class,
                    new DefaultAOCallBack<MyQuotData>(viewInterface,
                            context)
                    {

                        @Override
                        public void dealWithSuccess(MyQuotData obj)
                        {
                            viewInterface.updateView(obj);
                            viewInterface.isShowDialog(false);
                        }
                    });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getMyQuotData.getData(url, request_tag);
    }

    @Override
    public void getSellQuot(String url, String request_tag)
    {
        if (getSellQuotData == null)
        {
            getSellQuotData = new GetDataDAO<BaseData>(context,
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
        getSellQuotData.getData(url, request_tag);


    }

    @Override
    public void getSubAreaByCode(String url, String request_tag)
    {
        if (getSubAreaData == null)
        {
            getSubAreaData = new GetDataDAO<SubAreaData>(context,
                    SubAreaData.class,
                    new DefaultAOCallBack<SubAreaData>(viewInterface,
                            context)
                    {
                        @Override
                        public void dealWithSuccess(SubAreaData obj)
                        {
                            viewInterface.updateView(obj);
                            viewInterface.isShowDialog(false);
                        }
                    });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getSubAreaData.getData(url, request_tag);
    }

    @Override
    public void getProductType(String url, String request_tag)
    {
        if (getProductType == null)
        {
            getProductType = new GetDataDAO<FoodTypeData>(context,
                    FoodTypeData.class,
                    new DefaultAOCallBack<FoodTypeData>(viewInterface,
                            context)
                    {
                        @Override
                        public void dealWithSuccess(FoodTypeData obj)
                        {
                            viewInterface.updateView(obj);
                            viewInterface.isShowDialog(false);
                        }
                    });
        }
        viewInterface.isShowDialog(true);
        setTagForRequest(request_tag);
        getProductType.getData(url, request_tag);
    }

}
