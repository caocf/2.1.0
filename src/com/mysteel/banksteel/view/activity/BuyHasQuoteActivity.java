package com.mysteel.banksteel.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.IBuyCenter;
import com.mysteel.banksteel.ao.IOrderTrade;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.ConsultOrderData;
import com.mysteel.banksteel.entity.HistoryStanBuyData.DataEntity.PaginationEntity.DatasEntity;
import com.mysteel.banksteel.entity.StanQuotDetailsData;
import com.mysteel.banksteel.entity.StanQuotDetailsData.DataEntity.Datas;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.DateUtil;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.BuyHasQuoteAdapter;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.ui.SwipeListView;
import com.mysteel.banksteel.view.ui.SwipeListView.IXListViewListener;
import com.mysteel.banksteel.view.ui.popwindow.ActionItem;
import com.mysteel.banksteel.view.ui.popwindow.TitlePopup;
import com.mysteel.banksteel.view.ui.sweetalert.SweetAlertDialog;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * 求购单详情：买家已经收到报价
 * Created by zoujian on 15/7/27.
 */
public class BuyHasQuoteActivity extends SwipeBackActivity implements View.OnClickListener, IXListViewListener,
        IBuyView, TitlePopup.OnItemOnClickListener, ListView.OnItemClickListener
{

    private SwipeListView listview;
    private BuyHasQuoteAdapter adapter;
    private TextView tv_baojia_num;

    private ConsultOrderData.DatasEntity data;
    //品名,规格,材质,品牌产地,交货地,求购数量,单价,总价,成交时间,库存量, 查看同类资源
    private TextView tv_partname, tv_spec, tv_material, tv_origin, tv_delivery,
            tv_number, tv_validity, tv_time, tv_check;

    private IBuyCenter buyCenter;
    private String url = "";
    private String page = "1";
    private String size = "10";
    //报价卖家的信息列表数据
    private StanQuotDetailsData SQdata;
    private ArrayList<Datas> dataEntity = new ArrayList<Datas>();

    private TitlePopup titlePopup;

    private String orderId, resource;//求购单id 和 来源

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyhasquote);
        Bundle b = getIntent().getExtras();
        data = (ConsultOrderData.DatasEntity) b.getSerializable("Datas");
        orderId = data.getId();
        resource = data.getResource();
        initViews();
        changeView();
    }

    protected void initViews()
    {
        super.initViews();
        tvTitle.setText("求购单详情");
        backLayout.setOnClickListener(this);
        tvRightText.setVisibility(View.GONE);
        share_img_yixiang.setVisibility(View.VISIBLE);
        share_img_yixiang.setBackgroundResource(R.drawable.right_img);
        rightLayout.setOnClickListener(this);
        titlePopup = new TitlePopup(this, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titlePopup.setItemOnClickListener(this);
//        titlePopup.addAction(new ActionItem(this, "分享信息", R.drawable.share_buy));
        titlePopup.addAction(new ActionItem(this, "取消求购", R.drawable.cancle_buy));

        tv_baojia_num = (TextView) findViewById(R.id.tv_baojia_num);
        String nums = data.getQuotNum();
        nums = (TextUtils.isEmpty(nums)) ? "0" : nums;
        String num = "已收到 " + "<font color='#69ccff'>" + nums + "</font>" + " 份报价";
        tv_baojia_num.setText(Html.fromHtml(num));

        tv_partname = (TextView) findViewById(R.id.tv_partname);//品名
        tv_spec = (TextView) findViewById(R.id.tv_spec);//规格
        tv_material = (TextView) findViewById(R.id.tv_material);//材质
        tv_origin = (TextView) findViewById(R.id.tv_origin);//品牌产地
        tv_delivery = (TextView) findViewById(R.id.tv_delivery);//交货地
        tv_number = (TextView) findViewById(R.id.tv_number);//求购数量
        tv_validity = (TextView) findViewById(R.id.tv_validity);//这里是有效期了
        tv_time = (TextView) findViewById(R.id.tv_time);//成交时间
        tv_check = (TextView) findViewById(R.id.tv_check);//查看同类资源
        tv_check.setOnClickListener(this);

        buyCenter = new BuyImpl(this);
        adapter = new BuyHasQuoteAdapter(this, dataEntity);
        listview = (SwipeListView) findViewById(R.id.lv_list);
        listview.setAdapter(adapter);
        listview.setXListViewListener(this);
        listview.setOnItemClickListener(this);
        listview.setisNeedSwipe(true);
        if (null != listview)
        {
            listview.hideFoot();
        }

        getStanQuotDetails();

    }

    public void getStanQuotDetails()
    {
        page = "1";
        url = RequestUrl.getInstance(this).getUrl_getStanQuotDetails(this, data.getId());
        LogUtils.e(url);
        buyCenter.getStanQuotDetails(url, Constants.INTERFACE_stanQuotDetails);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.menu_layout:// 返回
                finish();
                break;

            case R.id.share_layout://右上角图片点击事件
                titlePopup.show(v);
                break;
            case R.id.tv_check:
                sameResource();
                break;
        }

    }


    /**
     * 查看同类资源
     */
    private void sameResource()
    {
        Intent i = new Intent(this, ResourceSameBuyersActivity.class);
//        i.putExtra("datas", data);
        i.putExtra("pinlei", data.getBreed());
        i.putExtra("caizhi",data.getMaterial());
        i.putExtra("guige",data.getSpec());
        i.putExtra("pinpai",data.getBrand());
        i.putExtra("cunhuodi",data.getCity());
        startActivity(i);
    }

    private void changeView()
    {
        tv_partname.setText(data.getBreed());
        tv_spec.setText(data.getSpec());
        tv_material.setText(data.getMaterial());
        tv_origin.setText(data.getBrand());
        tv_delivery.setText(data.getCity());
        tv_number.setText(data.getQty() + "吨");
        tv_time.setText(data.getPublishTime());
//        tv_validity.setText(Tools.getDateToString(data.getDueTime()));
        String resultDate = "还剩";
//	    	long time = Long.parseLong(datasEntity.getDueTime());
//	        Date dateTemp = new Date(time);
        Date dateTemp = DateUtil.getDate("yyyy-MM-dd HH:mm:ss", data.getDueTime());
        long[] diffDate = DateUtil.dateDiffEx(new Date(), dateTemp);
        if (diffDate[0] != 0)
        {
            resultDate += diffDate[0] + "天";
        }
        if (diffDate[0] != 1)
        {
            resultDate += diffDate[1] + "小时";
        }
        if (diffDate[0] != 2)
        {
            resultDate += diffDate[2] + "分";
        }
        tv_validity.setText(resultDate);
    }

    @Override
    public void onRefresh()
    {
        getStanQuotDetails();
    }

    @Override
    public void onLoadMore()
    {
        if (SQdata == null || SQdata.getData() == null || SQdata.getData().getDatas() == null)
        {
            return;
        }

        if (TextUtils.isEmpty(SQdata.getData().getPagenum())
                || TextUtils.isEmpty(SQdata.getData().getPage()))
        {
            return;
        }
        if (Integer.parseInt(page) >= Integer.parseInt(SQdata.getData().getPagenum()))
        {
            onLoad();
            Tools.showToast(this, "已经到最后一页!");
            return;
        }
        page = Integer.parseInt(page) + 1 + "";
//        String url1 = RequestUrl.getInstance(this).getUrl_getPageOrder(
//                this, page, size, "");
//        orderTrade.getPageOrder(url1, Constants.INTERFACE_pageOrder);
        url = RequestUrl.getInstance(this).getUrl_getStanQuotDetails(this, data.getId());
        buyCenter.getStanQuotDetails(url, Constants.INTERFACE_stanQuotDetails);
    }

    @Override
    public void updateView(BaseData data)
    {
        if ("quot.stanQuotDetails".equals(data.getCmd()))
        {
            SQdata = (StanQuotDetailsData) data;
            if (SQdata.getData() == null || SQdata.getData().getDatas() == null)
            {
                onLoad();
                return;
            }
            ArrayList<Datas> listdata = SQdata.getData().getDatas();

            if ("1".equals(page))
            {
                dataEntity = listdata;
            } else
            {
                dataEntity.addAll(listdata);
            }
            adapter.reSetList(dataEntity);
            onLoad();
        } else if ("buy.cancelStanBuy".equals(data.getCmd()))
        {//取消求购单
            if ("true".equals(data.getStatus()))
            {
                Toast.makeText(BuyHasQuoteActivity.this, "取消求购成功！", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post("", "refreshList");
                finish();
            } else
            {
                Toast.makeText(BuyHasQuoteActivity.this, data.getError(), Toast.LENGTH_SHORT).show();
            }

        }


    }

    @Override
    public void isShowDialog(boolean flag)
    {

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        buyCenter.finishRequest();
        titlePopup.cleanAction();
        IOrderTrade orderTrade = adapter.getOrderTrade();
        if (orderTrade != null)
        {
            orderTrade.finishRequest();
        }
    }

    /**
     * 更新listview头部刷新时间
     */
    public void onLoad()
    {
        listview.stopRefresh();
        listview.stopLoadMore();
        listview.setRefreshTime(new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.CHINESE).format(new Date()));
    }

    @Override
    public void onItemClick(ActionItem item, int position)
    {
        switch (position)
        {
            case 0://取消求购

                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("取消求购单")
                        .setContentText("确定取消求购单吗?")
                        .setCancelText("取 消")
                        .setConfirmText("确 定")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener()
                        {
                            @Override
                            public void onClick(SweetAlertDialog sDialog)
                            {

                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                        {
                            @Override
                            public void onClick(SweetAlertDialog sDialog)
                            {
//                                Toast.makeText(BuyHasQuoteActivity.this, "拉取数据", Toast.LENGTH_SHORT).show();
                                String url = RequestUrl.getInstance(BuyHasQuoteActivity.this).getUrl_getCancelStanBuy(BuyHasQuoteActivity.this, data.getId());
                                buyCenter.getCancelStanBuy(url, Constants.INTERFACE_cancelStanBuy);
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
//        if (position <= 0 || position > adapter.getData().size())
//        {
//            return;
//        }
//        Datas data = adapter.getData().get(position - 1);
    	if ((position - 2 ) < 0 || (position - 2 ) > adapter.getData().size())
        {
            return;
        }
        Datas data = adapter.getData().get(position - 2);

        Intent i = new Intent(this, BuyQuoteActivity.class);
        i.putExtra("DATAS", data);
        i.putExtra("tv_partname", tv_partname.getText().toString());
        i.putExtra("tv_spec", tv_spec.getText().toString());
        i.putExtra("tv_material", tv_material.getText().toString());
        i.putExtra("tv_origin", tv_origin.getText().toString());
        i.putExtra("tv_delivery", tv_delivery.getText().toString());
        i.putExtra("tv_number", tv_number.getText().toString());
        i.putExtra("tv_validity", tv_validity.getText().toString());
        i.putExtra("tv_time", tv_time.getText().toString());
        i.putExtra("orderId", orderId);
        i.putExtra("resource", resource);
        startActivity(i);
    }

    @Subscriber(tag = "BuyHasQuoteActivity_finish")
    public void finishActivity(String str)
    {
        finish();
    }
}
