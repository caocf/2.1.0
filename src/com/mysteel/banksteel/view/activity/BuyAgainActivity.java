package com.mysteel.banksteel.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mysteel.banksteel.ao.IBuyCenter;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.ConsultOrderData;
import com.mysteel.banksteel.entity.HistoryStanBuyData.DataEntity.PaginationEntity.DatasEntity;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteel.view.ui.popwindow.ActionItem;
import com.mysteel.banksteel.view.ui.popwindow.TitlePopup;
import com.mysteel.banksteel.view.ui.sweetalert.SweetAlertDialog;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;

/**
 * 求购单详情：重新发布
 * Created by zoujian on 15/7/27.
 */
public class BuyAgainActivity extends SwipeBackActivity implements View.OnClickListener,
        TitlePopup.OnItemOnClickListener,IBuyView
{

    private ConsultOrderData.DatasEntity data;
    private Button btn_refind;//底部按钮
    //品名,规格,材质,品牌产地,交货地,求购数量,单价,总价,成交时间,库存量
    private TextView tv_partname, tv_spec, tv_material, tv_origin, tv_delivery,
            tv_number, tv_validity, tv_time, tv_check;

    private TitlePopup titlePopup;
    private IBuyCenter buyCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyagain);
        Bundle b = getIntent().getExtras();
        data = (ConsultOrderData.DatasEntity) b.getSerializable("Datas");
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

        tv_partname = (TextView) findViewById(R.id.tv_partname);//品名
        tv_spec = (TextView) findViewById(R.id.tv_spec);//规格
        tv_material = (TextView) findViewById(R.id.tv_material);//材质
        tv_origin = (TextView) findViewById(R.id.tv_origin);//品牌产地
        tv_delivery = (TextView) findViewById(R.id.tv_delivery);//交货地
        tv_number = (TextView) findViewById(R.id.tv_number);//求购数量
        tv_validity = (TextView) findViewById(R.id.tv_validity);//这里是有效期了
        tv_time = (TextView) findViewById(R.id.tv_time);//成交时间
        tv_check = (TextView) findViewById(R.id.tv_check);//查看同类资源
        btn_refind = (Button) findViewById(R.id.btn_refind);
        btn_refind.setOnClickListener(this);
        tv_check.setOnClickListener(this);

        rightLayout.setOnClickListener(this);
        titlePopup = new TitlePopup(this, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titlePopup.setItemOnClickListener(this);
        titlePopup.addAction(new ActionItem(this, "取消求购", R.drawable.cancle_buy));
        buyCenter = new BuyImpl(this);

    }


    private void changeView()
    {
        tv_partname.setText(data.getBreed());
        tv_spec.setText(data.getSpec());
        tv_material.setText(data.getMaterial());
        tv_origin.setText(data.getBrand());
        tv_delivery.setText(data.getCity());
        tv_number.setText(data.getQty());
        tv_validity.setText("已失效");
        tv_time.setText(data.getPublishTime());
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.menu_layout:// 返回
                finish();
                break;
            case R.id.btn_refind://重新发布求购
                Intent i = new Intent(this, BuyDetailInfoActivity.class);
                i.putExtra("parentSteel", "");// 传空
                i.putExtra("childSteel", data.getBreed());
                i.putExtra("childId", data.getBreedId());
                i.putExtra("material", data.getMaterial());
                i.putExtra("spec", data.getSpec());
                i.putExtra("brand", data.getBrand());
                i.putExtra("city", data.getCity());
                i.putExtra("qty", data.getQty());
                i.putExtra("contacter", data.getContacter());
                i.putExtra("phone", data.getPhone());
                startActivity(i);
                break;

            case R.id.tv_check://查看同类资源
                sameResource();
                break;

            case R.id.share_layout://右上角图片点击事件
                titlePopup.show(v);
                break;
        }
    }

    /**
     * 查看同类资源
     */
    private void sameResource(){
        Intent i = new Intent(this, ResourceSameBuyersActivity.class);
        i.putExtra("pinlei", data.getBreed());
        i.putExtra("caizhi",data.getMaterial());
        i.putExtra("guige",data.getSpec());
        i.putExtra("pinpai",data.getBrand());
        i.putExtra("cunhuodi",data.getCity());
        startActivity(i);
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
                                String url = RequestUrl.getInstance(BuyAgainActivity.this).getUrl_getCancelStanBuy(BuyAgainActivity.this, data.getId());
                                buyCenter.getCancelStanBuy(url, Constants.INTERFACE_cancelStanBuy);
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public void updateView(BaseData data)
    {
        if ("buy.cancelStanBuy".equals(data.getCmd()))
        {//取消求购单
            if ("true".equals(data.getStatus()))
            {
                Toast.makeText(BuyAgainActivity.this, "取消求购成功！", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post("", "refreshList");
                finish();
            } else
            {
                Toast.makeText(BuyAgainActivity.this, data.getError(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void isShowDialog(boolean flag)
    {

    }
}
