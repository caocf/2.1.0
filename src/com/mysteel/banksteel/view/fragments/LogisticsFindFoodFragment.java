package com.mysteel.banksteel.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.mysteel.banksteel.ao.impl.TransportManagerImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.LogisticsSelfOrder;
import com.mysteel.banksteel.entity.TransportQuotData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.activity.ChooseFoodTypeActivity;
import com.mysteel.banksteel.view.activity.LoadPopupWheel;
import com.mysteel.banksteel.view.activity.LoginActivity;
import com.mysteel.banksteel.view.activity.LogisticsChooseCityActivity;
import com.mysteel.banksteel.view.activity.LogisticsSelfOrderActivity;
import com.mysteel.banksteel.view.interfaceview.ITransportManagerView;
import com.mysteel.banksteel.view.ui.popwindow.LoadPopWindow;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.HashMap;
import java.util.Map;

/**
 * 物流中 找货页面
 * Created by zoujian on 15/8/11.
 */
public class LogisticsFindFoodFragment extends BaseFragment implements View.OnClickListener,ITransportManagerView
{
    RelativeLayout rl_qishi_layout, rl_zhongdian_layout, rl_house_layout;
    private TextView tv_address_qishi, tv_mudi_qishi,tv_house_type;
    private int tag = 0;//标识起始地和目的地的请求 0:起始地 1:目的地

    private EditText edit_qty; //货物数量XX吨
    private TextView tv_zhuang_val; //装
    private TextView tv_xie_val; //卸

    private TextView tv_quotprice; //优惠价格
    private TextView tv_favorableprice; //优惠价格

    private LinearLayout ll_bottom;
    private TextView artificial_service; //人工服务
    private TextView self_order; //自助下单

    private String price = ""; //物流价格
    private String discountPrice = ""; //优惠价格
    private LogisticsSelfOrder data = new LogisticsSelfOrder(); //页面的值

    private TransportManagerImpl transportManagerImpl;

    private String qty = "";
    private String breedName = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_logistics_inquiry, null);
        EventBus.getDefault().register(this);
        initView(view);
        initData();
        return view;
    }



    private void initView(View view)
    {
        Bundle bundle = getArguments();
        qty = bundle.getString("qty");
        breedName = bundle.getString("breedName");

        rl_qishi_layout = (RelativeLayout) view.findViewById(R.id.rl_qishi_layout);//起点布局
        rl_qishi_layout.setOnClickListener(this);
        rl_zhongdian_layout = (RelativeLayout) view.findViewById(R.id.rl_zhongdian_layout);//终点布局
        rl_zhongdian_layout.setOnClickListener(this);
        rl_house_layout = (RelativeLayout) view.findViewById(R.id.rl_house_layout);//货物类型
        rl_house_layout.setOnClickListener(this);

        tv_address_qishi = (TextView) view.findViewById(R.id.tv_address_qishi);
        tv_mudi_qishi = (TextView) view.findViewById(R.id.tv_mudi_qishi);
        tv_house_type = (TextView) view.findViewById(R.id.tv_house_type);

        edit_qty = (EditText) view.findViewById(R.id.edit_qty);
        tv_zhuang_val = (TextView) view.findViewById(R.id.tv_zhuang_val);
        tv_xie_val = (TextView) view.findViewById(R.id.tv_xie_val);
        tv_zhuang_val.setOnClickListener(this);
        tv_xie_val.setOnClickListener(this);

        tv_quotprice = (TextView) view.findViewById(R.id.tv_quotprice);
        tv_favorableprice = (TextView) view.findViewById(R.id.tv_favorableprice);

        ll_bottom = (LinearLayout) view.findViewById(R.id.ll_bottom);
        artificial_service = (TextView) view.findViewById(R.id.artificial_service);
        self_order = (TextView) view.findViewById(R.id.self_order);
        artificial_service.setOnClickListener(this);
        self_order.setOnClickListener(this);
        self_order.setClickable(false);

        if(!TextUtils.isEmpty(breedName)){
            tv_house_type.setText(breedName);
            data.setFoodType(breedName);
        }
        if(!TextUtils.isEmpty(qty)){
            edit_qty.setText(qty);
            data.setFoodNum(qty);
        }
    }

    public void initData() {
        transportManagerImpl = new TransportManagerImpl(getActivity(),this);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getTransportPrice();
            }
        };

        edit_qty.addTextChangedListener(textWatcher);
        tv_zhuang_val.addTextChangedListener(textWatcher);
        tv_xie_val.addTextChangedListener(textWatcher);
    }

    @Override
    public void onClick(View v)
    {
        Intent i = null;
        switch (v.getId())
        {
            case R.id.rl_qishi_layout:
                tag = 0;
                i = new Intent(getActivity(), LogisticsChooseCityActivity.class);
                startActivity(i);
                break;
            case R.id.rl_zhongdian_layout:
                tag = 1;
                i = new Intent(getActivity(), LogisticsChooseCityActivity.class);
                startActivity(i);
                break;
            case R.id.rl_house_layout:
                i = new Intent(getActivity(), ChooseFoodTypeActivity.class);
                startActivity(i);
                break;
            case R.id.tv_zhuang_val:
                //selLoadPop("1装","2装",true);
                selLoadPop(true);
                break;
            case R.id.tv_xie_val:
                //selLoadPop("1卸","2卸",false);
                selLoadPop(false);
                break;
            case R.id.artificial_service:
                rengongService();
                break;
            case R.id.self_order:
                getSelfOrder();
                break;
        }
    }


    /**
     * 选择装卸数 滚轮
     * @param flag true=装  false=卸
     */
    private void selLoadPop(final boolean flag){
        String[] nums = new String[2];
        if(flag){
            nums[0] = "1装";
            nums[1] = "2装";
        }else{
            nums[0] = "1卸";
            nums[1] = "2卸";
        }

        LoadPopupWheel popupWheel = new LoadPopupWheel(getActivity(), new LoadPopupWheel.PopupValueLinstener() {
            @Override
            public void change(int firstValue) {
                if(flag){
                    tv_zhuang_val.setText(firstValue+"");
                }else{
                    tv_xie_val.setText(firstValue+"");
                }
            }
        });
        View rootView = getActivity().getWindow().getDecorView();
        int cur = 0;
        if(flag){
            cur = Integer.parseInt(tv_zhuang_val.getText().toString())-1;
        }else{
            cur = Integer.parseInt(tv_xie_val.getText().toString())-1;
        }
        popupWheel.setData(nums,cur,0);
        popupWheel.setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWheel.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);

    }

    /**
     * 选择装卸数
     * @param content1
     * @param content2
     * @param flag
     */
    private void selLoadPop(String content1,String content2,final boolean flag){
        LoadPopWindow popWindow = new LoadPopWindow();
        popWindow.showPopupWindow(getActivity(), getActivity());
        popWindow.setData(content1, content2);
        popWindow.setListener(new LoadPopWindow.SelectPopListener() {
            @Override
            public void request(String str) {
                if (flag) {
                    tv_zhuang_val.setText(str);
                } else {
                    tv_xie_val.setText(str);
                }
            }
        });

    }

    /**
     * 自助下单
     */
    public void getSelfOrder() {
            if (!Tools.isLogin(getActivity()))
            {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                return;
            }
            if(TextUtils.isEmpty(data.getStartAddress())){
                Tools.showToast(getActivity(),"起点不能为空");
                return;
            }
            if(TextUtils.isEmpty(data.getEndAddress())){
                Tools.showToast(getActivity(),"终点不能为空");
                return;
            }
            if(TextUtils.isEmpty(data.getFoodType())){
                Tools.showToast(getActivity(),"货物不能为空");
                return;
            }
            String foodNum = edit_qty.getText().toString();
            if(TextUtils.isEmpty(foodNum)){
                Tools.showToast(getActivity(),"请填写数量");
                return;
            }else{
                data.setFoodNum(foodNum);
            }

            String load = tv_zhuang_val.getText().toString();
            if(TextUtils.isEmpty(load)){
                Tools.showToast(getActivity(),"请选择装数");
                return;
            }else{
                data.setLoad(load);
            }

            String unload = tv_xie_val.getText().toString();
            if(TextUtils.isEmpty(unload)){
                Tools.showToast(getActivity(),"请选择卸数");
                return;
            }else{
                data.setUnload(unload);
            }

            Intent intent = new Intent(getActivity(), LogisticsSelfOrderActivity.class);
            intent.putExtra("data", data);
            getActivity().startActivity(intent);
    }

    /**
     * 人工服务
     */
    private void rengongService(){
        if (!Tools.isLogin(getActivity()))
        {
            Intent i = new Intent(getActivity(), LoginActivity.class);
            startActivity(i);
            return;
        }

        if(TextUtils.isEmpty(data.getStartAddress())){
            Tools.showToast(getActivity(),"起点不能为空");
            return;
        }
        if(TextUtils.isEmpty(data.getEndAddress())){
            Tools.showToast(getActivity(),"终点不能为空");
            return;
        }
        if(TextUtils.isEmpty(data.getFoodType())){
            Tools.showToast(getActivity(),"货物不能为空");
            return;
        }
        String foodNum = edit_qty.getText().toString();
        if(TextUtils.isEmpty(foodNum)){
            Tools.showToast(getActivity(),"请填写数量");
            return;
        }else{
            data.setFoodNum(foodNum);
        }

        String load = tv_zhuang_val.getText().toString();
        if(TextUtils.isEmpty(load)){
            Tools.showToast(getActivity(),"请选择装数");
            return;
        }else{
            data.setLoad(load);
        }

        String unload = tv_xie_val.getText().toString();
        if(TextUtils.isEmpty(unload)){
            Tools.showToast(getActivity(),"请选择卸数");
            return;
        }else{
            data.setUnload(unload);
        }

        String[] startAttr = data.getStartAddress().split("\\|");
        String[] endAttr = data.getEndAddress().split("\\|");
        String name = SharePreferenceUtil.getString(getActivity(), Constants.USER_NAME);
        String phone = SharePreferenceUtil.getString(getActivity(), Constants.PREFERENCES_CELLPHONE);

        Map<String,String> map = new HashMap<String, String>();
        map.put("startSiteCity", startAttr[0]);
        map.put("startSiteDistrict",startAttr[1]);
        map.put("endSiteCity", endAttr[0]);
        map.put("endSiteDistrict", endAttr[1]);
        map.put("breed", data.getFoodType());
        map.put("weight", data.getFoodNum());
        map.put("load", data.getLoad());
        map.put("unload", data.getUnload());
        map.put("userName",name);
        map.put("phone",phone);
        String url = RequestUrl.getInstance(getActivity()).getUrl_CustomerCare(getActivity(), map);
        LogUtils.e(url);
        transportManagerImpl.getCustomerCare(url, Constants.INTERFACE_customerCare);

    }

    /**
     * 查询报价
     */
     private void getTransportPrice(){
         //先设置为不可点击
         self_order.setBackgroundResource(R.drawable.set_btn_gray);
         self_order.setClickable(false);
         tv_quotprice.setText("");
         tv_favorableprice.setText("");

         String foodNum = edit_qty.getText().toString();
         String load = tv_zhuang_val.getText().toString();
         String unload = tv_xie_val.getText().toString();

         LogUtils.e("货物的数量"+foodNum);

         if(TextUtils.isEmpty(data.getStartAddress())||
            TextUtils.isEmpty(data.getEndAddress())||
            TextUtils.isEmpty(data.getFoodType())||
            TextUtils.isEmpty(foodNum)||
            TextUtils.isEmpty(load)||
            TextUtils.isEmpty(unload)||
            "0".equals(foodNum)
            ){
             LogUtils.e("没进入这里吗");
             //按钮变灰  不可点击
             self_order.setBackgroundResource(R.drawable.set_btn_gray);
             self_order.setClickable(false);
             tv_quotprice.setText("");
             tv_favorableprice.setText("");
             return;
         }

         data.setFoodNum(foodNum);
         data.setLoad(load);
         data.setUnload(unload);

         String[] startAttr = data.getStartAddress().split("\\|");
         String[] endAttr = data.getEndAddress().split("\\|");

         Map<String,String> map = new HashMap<String, String>();
         map.put("startSiteCity", startAttr[0]);
         map.put("startSiteDistrict",startAttr[1]);
         map.put("endSiteCity", endAttr[0]);
         map.put("endSiteDistrict", endAttr[1]);
         map.put("breed", data.getFoodType());
         map.put("weight", data.getFoodNum());
         map.put("load", data.getLoad());
         map.put("unload", data.getUnload());
         String url = RequestUrl.getInstance(getActivity()).getUrl_queryTransportQuot(getActivity(), map);
         LogUtils.e(url);
         transportManagerImpl.getQueryTransportQuot(url, Constants.INTERFACE_createTransportQuot);
     }

    /**
     * 获取city中的消息
     */
    @Subscriber(tag = "getCityArea")
    public void getCityArea(String str)
    {
        switch (tag)
        {
            case 0:
                tv_address_qishi.setText(str.replace("|"," "));
                data.setStartAddress(str);
                break;
            case 1:
                tv_mudi_qishi.setText(str.replace("|"," "));
                data.setEndAddress(str);
                break;
        }
        getTransportPrice();
    }


    /**
     * 获取city中的消息
     */
    @Subscriber(tag = "getFoodType")
    public void getFoodType(String str)
    {
        tv_house_type.setText(str);
        data.setFoodType(str);
        getTransportPrice();
    }


    @Override
    public void updateView(BaseData data) {
        String foodNum = edit_qty.getText().toString();
        String load = tv_zhuang_val.getText().toString();
        String unload = tv_xie_val.getText().toString();
        if(TextUtils.isEmpty(this.data.getStartAddress())||
                TextUtils.isEmpty(this.data.getEndAddress())||
                TextUtils.isEmpty(this.data.getFoodType())||
                TextUtils.isEmpty(foodNum)||
                TextUtils.isEmpty(load)||
                TextUtils.isEmpty(unload)||
                "0".equals(foodNum)
                ){
            //按钮变灰  不可点击
            self_order.setBackgroundResource(R.drawable.set_btn_gray);
            self_order.setClickable(false);
            tv_quotprice.setText("");
            tv_favorableprice.setText("");
            return;
        }

        if(data.getCmd().equals("transport.customerCare")){
             Tools.showToast(getActivity(),"提交成功!请等待客服或工作人员与您联系!");
             return;
        }else{
            TransportQuotData requestData = (TransportQuotData)data;
            //判断是否为0
            if("0".equals(requestData.getData().getResultMessage().getQuotePrice())||"0".equals(requestData.getData().getResultMessage().getFavorablePrice())){
                //按钮变灰  不可点击
                self_order.setBackgroundResource(R.drawable.set_btn_gray);
                self_order.setClickable(false);
                tv_quotprice.setText("");
                tv_favorableprice.setText("");
                return;
            }

            if(requestData.getData().getResultMessage()!=null){
                self_order.setBackgroundResource(R.drawable.set_btn_red);
                self_order.setClickable(true);
            }else{
                //按钮变灰  不可点击
                self_order.setBackgroundResource(R.drawable.set_btn_gray);
                self_order.setClickable(false);
                tv_quotprice.setText("");
                tv_favorableprice.setText("");
            }
            if(!TextUtils.isEmpty(requestData.getData().getResultMessage().getQuotePrice())){
                tv_quotprice.setText(requestData.getData().getResultMessage().getQuotePrice());
                this.data.setPrice(requestData.getData().getResultMessage().getQuotePrice());
            }
            if(!TextUtils.isEmpty(requestData.getData().getResultMessage().getFavorablePrice())){
                tv_favorableprice.setText(requestData.getData().getResultMessage().getFavorablePrice());
                this.data.setDiscountPrice(requestData.getData().getResultMessage().getFavorablePrice());
            }
            this.data.setTaxPrice0(requestData.getData().getResultMessage().getTaxPrice0());
            this.data.setTaxPrice6(requestData.getData().getResultMessage().getTaxPrice6());
            this.data.setTaxPrice11(requestData.getData().getResultMessage().getTaxPrice11());
        }
    }

    @Override
    public void isShowDialog(boolean flag) {

    }
}
