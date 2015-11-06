package com.mysteel.banksteel.view.activity;

import java.util.ArrayList;

import org.simple.eventbus.Subscriber;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mysteel.banksteel.ao.IBuyCenter;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.RegisterData;
import com.mysteel.banksteel.entity.SpecsAndMaterialData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.SharePreferenceUtil;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.ui.swipebacklayout.SwipeBackActivity;
import com.mysteel.banksteeltwo.R;

/**
 * 求购详情页面
 *
 * @author:huoguodong
 * @date：2015-5-5 下午3:36:52
 */
public class BuyDetailInfoActivity extends SwipeBackActivity implements
        OnClickListener, IBuyView
{

    /**
     * DEBUG.
     */
    private static final boolean DEBUG = true & BankSteelApplication.GLOBAL_DEBUG;
    /**
     * TAG.
     */
    private static final String TAG = "BuyDetailInfoActivity";
    private View ivMaterial; // 材质
    private View ivBrand; // 品牌和产地
    private View ivCity; // 城市
    private View ivStandard;// 规格

    private TextView tvSteelType; // 钢铁品种
    private EditText etCity;
    private EditText etBrand;
    private Button btnPublishDemand; // 发布求购按钮

    // private EditText etStandard;// 规格输入框
    private EditText etNumber;// 数量
    private EditText etLinkman;// 联系人
    private EditText etLinkPhone;// 联系电话

    private IBuyCenter buyCenterImpl;

    private EditText etMaterial;
    private EditText etSpecs;
    private EditText et_beizhu;

    /**
     * 品牌厂商
     */
    public final static String SCREEN_TAG_BRAND = "brand";
    public final static int REQUEST_CODE_BRADD = 101;
    /**
     * 城市
     */
    public final static String SCREEN_TAG_CITY = "city";
    public final static int REQUEST_CODE_CITY = 102;

    /**
     * 从选择品种 和 我的求购2个页面跳转过来，避免新开页面写相同的逻辑，公用一个界面，但必须将所有参数传齐
     */
    private String parentSteel;// 父品种
    private String childSteel;// 子品种
    private String childSteelId;// 子品种ID
    private String material;// 材质
    String spec;// 规格
    String brand;// 品牌和产地
    String city;// 城市
    String qty;// 求购数量
    String contacter;// 联系人
    String phone;// 联系号码

    public final static String CHILD_STEEL_ID = "childSteelId";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_detail);

        parentSteel = getIntent().getStringExtra(
                SelectTypeActivity.PARENT_STEEL);
        childSteel = getIntent().getStringExtra(SelectTypeActivity.CHILD_STEEL);
        childSteelId = getIntent().getStringExtra(SelectTypeActivity.CHILD_ID);// breedId
        material = getIntent().getStringExtra("material");
        spec = getIntent().getStringExtra("spec");
        brand = getIntent().getStringExtra("brand");
        city = getIntent().getStringExtra("city");
        qty = getIntent().getStringExtra("qty");
        contacter = getIntent().getStringExtra("contacter");
        phone = getIntent().getStringExtra("phone");

        initViews();
        initData();
    }

    /**
     * 初始化页面组件
     */
    @Override
    protected void initViews()
    {
        super.initViews();

        ivMaterial = findViewById(R.id.iv_material_forward);// 材质
        etMaterial = (EditText) findViewById(R.id.et_material_value);// 显示材质
        ivBrand = findViewById(R.id.iv_brand_forward);// 品牌/产地
        ivCity = findViewById(R.id.iv_city_forward);// 城市

        ivStandard = findViewById(R.id.iv_dimension_forward);// 规格
        etSpecs = (EditText) findViewById(R.id.et_dimension_value);// 显示规格

        // etStandard = (EditText) findViewById(R.id.tv_dimension_value);// 规格
        etNumber = (EditText) findViewById(R.id.tv_buy_number_value);// 数量
        etLinkman = (EditText) findViewById(R.id.et_contact_person_value);// 联系人
        etLinkPhone = (EditText) findViewById(R.id.tv_contact_phone_value);// 联系号码

        tvSteelType = (TextView) findViewById(R.id.tv_type_value);
        etCity = (EditText) findViewById(R.id.et_city_value);// 城市
        etBrand = (EditText) findViewById(R.id.et_brand_value);// 品牌/产地
        btnPublishDemand = (Button) findViewById(R.id.btn_publish_demand);
        et_beizhu = (EditText) findViewById(R.id.et_beizhu);
        
        et_beizhu.addTextChangedListener(new TextWatcher()
		{

			private CharSequence temp;
			private int selectionStart;
			private int selectionEnd;
			private static final int NUM = 40;

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count)
			{
				temp = s;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{
			}

			@Override
			public void afterTextChanged(Editable s)
			{
//				wordNumber = NUM - s.length();
				selectionStart = et_beizhu.getSelectionStart();
				selectionEnd = et_beizhu.getSelectionEnd();
				if (temp.length() > NUM)
				{
					s.delete(selectionStart - 1, selectionEnd);
					int tempSelection = selectionEnd;
					et_beizhu.setText(s);
					et_beizhu.setSelection(tempSelection);
					Toast.makeText(BuyDetailInfoActivity.this, "最多输入40个字!", Toast.LENGTH_SHORT).show();
				}

			}
		});

        menuBtn.setVisibility(View.GONE);
        imBack.setVisibility(View.VISIBLE);
        tvTitle.setText("发布求购");
        // etCity.setInputType(InputType.TYPE_NULL);

        backLayout.setOnClickListener(this);
        ivMaterial.setOnClickListener(this);
        ivStandard.setOnClickListener(this);
        ivBrand.setOnClickListener(this);
        ivCity.setOnClickListener(this);
        btnPublishDemand.setOnClickListener(this);
        buyCenterImpl = new BuyImpl(this);

        /** 设置联系人和联系电话 */
        String name = SharePreferenceUtil.getString(this, Constants.USER_NAME);
        String phone = SharePreferenceUtil.getString(this,
                Constants.PREFERENCES_CELLPHONE);
        etLinkman.setText(name);
        etLinkPhone.setText(phone);
    }

    /**
     * 根据intent传递过来的值初始页面
     */
    private void initData()
    {

        String SteelType = TextUtils.isEmpty(parentSteel) ? childSteel
                : (parentSteel + ", " + childSteel);
        tvSteelType.setText(SteelType);
        if (!TextUtils.isEmpty(material))
        {
            etMaterial.setText(material);
        }
        if (!TextUtils.isEmpty(spec))
        {
            etSpecs.setText(spec);
        }
        if (!TextUtils.isEmpty(brand))
        {
            etBrand.setText(brand);
        }
        if (!TextUtils.isEmpty(city))
        {
            etCity.setText(city);
        }
        if (!TextUtils.isEmpty(qty))
        {
            etNumber.setText(qty);
        }
        if (!TextUtils.isEmpty(contacter))
        {
            etLinkman.setText(contacter);
        }
        if (!TextUtils.isEmpty(phone))
        {
            etLinkPhone.setText(phone);
        }
    }

    @Override
    public void onClick(View v)
    {
        Intent intent;
        switch (v.getId())
        {
            case R.id.iv_material_forward:// 获取材质
                getMaterial();
                break;
            case R.id.iv_dimension_forward:
                getSpecs();
                break;
            case R.id.iv_brand_forward:
                intent = new Intent(this, BrandCityActivity.class);
                intent.putExtra(Constants.NEXT_SCREEN_TAG, SCREEN_TAG_BRAND);
                intent.putExtra(CHILD_STEEL_ID, childSteelId);
                startActivityForResult(intent, REQUEST_CODE_BRADD);
                break;
            case R.id.iv_city_forward:
                intent = new Intent(this, BrandCityActivity.class);
                intent.putExtra(Constants.NEXT_SCREEN_TAG, SCREEN_TAG_CITY);
                startActivityForResult(intent, REQUEST_CODE_CITY);
                break;
            case R.id.menu_layout:
                // if (TextUtils.isEmpty(parentSteel))
                // {// 从发布求购页面来直接销毁当前页
                finish();
                // }
            /*
             * else {// 若是从选择品种页面来，就返回到精准找货首页 intent = new Intent(this,
			 * PublishDemandActivity.class); startActivity(intent); }
			 */
                break;
            case R.id.btn_publish_demand:// 发布求购的点击事件
                // intent = new Intent(this, SelectProSourceActivity.class);
                // intent.putExtra("ID", "438");
                // startActivity(intent);
                if (Tools.isLogin(this))
                {
                    publishStanBuyCommit();
                } else
                {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }

                break;
            default:
                break;
        }
    }

    /**
     * 发布求购提交按钮
     */
    private void publishStanBuyCommit()
    {
        String category = childSteel;// 品名
        String material = etMaterial.getText().toString().trim();// 材质
        String spec = etSpecs.getText().toString().trim();// 规格
        String brand = etBrand.getText().toString().trim();// 品牌和产地
        String city = etCity.getText().toString().trim();// 城市
        String qty = etNumber.getText().toString().trim();// 求购数量
        String contacter = etLinkman.getText().toString().trim();// 联系人
        String phone = etLinkPhone.getText().toString().trim();// 联系号码
        String resource = "2";// 来源 来源:0-网站快捷需求 1-会员中心 2-手机端
        String company = SharePreferenceUtil.getString(this, Constants.USER_MEMBER_NAME);// 公司名称
        String note = et_beizhu.getText().toString().trim().replaceAll(" ", "");

        if (TextUtils.isEmpty(material))
        {
            Tools.showToast(this, "请输入或者选择材质！");
            return;
        }
        if (TextUtils.isEmpty(spec))
        {
            Tools.showToast(this, "请输入或者选择规格！");
            return;
        }
        // if (TextUtils.isEmpty(brand))
        // {
        // Tools.showToast(this, "请输入或者选择品牌、产地！");
        // return;
        // }
        if (TextUtils.isEmpty(qty))
        {
            Tools.showToast(this, "请填写求购数量！");
            return;
        }
        if (TextUtils.isEmpty(city))
        {
            Tools.showToast(this, "请填写城市信息！");
            return;
        }
        if (TextUtils.isEmpty(contacter))
        {
            Tools.showToast(this, "请填写联系人信息！");
            return;
        }
        if (!Tools.checkIsPhoneNumber(phone) || TextUtils.isEmpty(phone))
        {
            Tools.showToast(this, "请填写正确格式的联系方式！");
            return;
        }

//        final String url = RequestUrl.getInstance(this).getUrl_publishStanBuy(
//                this, childSteelId, category, material, spec, brand, city, qty,
//                contacter, phone, resource, company);

        String message = "品名：" + childSteel + "\n" + "材质：" + material + "\n"
                + "规格：" + spec + "\n" + "品牌和产地："
                + (("".equals(brand)) ? "不限" : brand) + "\n" + "交货地：" + city
                + "\n" + "求购数：" + qty + "吨" + "\n" + "联系人：" + contacter + "\n"
                + "联系号码：" + phone;

        String SteelType = TextUtils.isEmpty(parentSteel) ? childSteel
                : (parentSteel + "-" + childSteel + " " + spec + " " + material);

        Intent i = new Intent(this, CommitBuyActivity.class);
        i.putExtra("parentSteel", parentSteel);
        i.putExtra("childSteel", childSteel);
        i.putExtra("spec", spec);
        i.putExtra("material", material);
        i.putExtra("childSteelId", childSteelId);
        i.putExtra("qty", qty);
        i.putExtra("city", city);
        i.putExtra("contacter", contacter);
        i.putExtra("phone", phone);
        i.putExtra("brand", (("".equals(brand)) ? "不限" : brand));
        i.putExtra("note", note);
        startActivity(i);
//		TwoButtonDialog.Builder alert = new TwoButtonDialog.Builder(
//				BuyDetailInfoActivity.this);
//		alert.setMessage(message);
//		alert.setNegativeButton(
//				// 取消
//				getResources().getString(R.string.cancel_publish),
//				new DialogInterface.OnClickListener()
//				{
//					@Override
//					public void onClick(DialogInterface dialog, int which)
//					{
//
//						dialog.dismiss();
//					}
//
//				});
//		alert.setPositiveButton(getResources().getString(R.string.make_sure),
//				new DialogInterface.OnClickListener()
//				{// 确定
//
//					@Override
//					public void onClick(DialogInterface dialog, int which)
//					{
//						buyCenterImpl.getPublishStanBuy(url,
//								Constants.INTERFACE_publishStanBuy);
//						dialog.dismiss();
//					}
//
//				});
//		alert.create().show();

    }

    /**
     * 获取规格的方法
     */
    private void getSpecs()
    {
        String url = RequestUrl.getInstance(this).getUrl_getSpecsByBreedId(
                this, childSteelId);
        buyCenterImpl.getSpecsAndMaterial(url,
                Constants.INTERFACE_getSpecsByBreedId);
    }

    /**
     * 获取材质的方法
     */
    private void getMaterial()
    {
        String url = RequestUrl.getInstance(this).getUrl_getMaterialByBreedId(
                this, childSteelId);
        buyCenterImpl.getSpecsAndMaterial(url,
                Constants.INTERFACE_getMaterialByBreedId);
    }

    /**
     * 获取选择的材质 厂商和城市
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case REQUEST_CODE_CITY:
                    if (DEBUG)
                    {
                        Log.d(TAG, "返回的数据：" + data.getStringExtra(BrandCityActivity.RETURN_DATA));
                    }
                    etCity.setText(data
                            .getStringExtra(BrandCityActivity.RETURN_DATA));
                    break;
                case REQUEST_CODE_BRADD:
                    if (DEBUG)
                    {
                        Log.d(TAG, "返回的数据：" + data.getStringExtra(BrandCityActivity.RETURN_DATA));
                    }
                    etBrand.setText(data
                            .getStringExtra(BrandCityActivity.RETURN_DATA));
                default:
                    break;
            }
        }
    }

    @Override
    public void updateView(BaseData data)
    {
        if ("buy.publishStanBuy".equals(data.getCmd()))
        {// 发布求购
            RegisterData rdata = (RegisterData) data;
            String id = rdata.getData();// 发布求购后返回的id
            Intent intent = new Intent(this, MatchFindGood.class);
            intent.putExtra("ID", id);
            if (DEBUG)
            {
                Log.d(TAG, "发布求购成功后得到的id是：" + id);
            }
            startActivity(intent);
        } else
        {
            SpecsAndMaterialData smData = (SpecsAndMaterialData) data;
            editSpecsAndMaterial(smData);
        }

    }

    @Override
    public void isShowDialog(boolean flag)
    {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        buyCenterImpl.finishRequest();
    }

    /**
     * 选择材质 或者 规格
     */
    private void editSpecsAndMaterial(SpecsAndMaterialData data)
    {
        ArrayList<String> list = (ArrayList<String>) data.getData();
        if (list == null)
        {
            if ("buy.getMaterialByBreedId".equals(data.getCmd()))
            {
                Tools.showToast(this, "暂时没有材质可选，请手动输入！");
            } else
            {
                Tools.showToast(this, "暂时没有规格可选，请手动输入！");
            }
            return;
        }
        int count = list.size();
        final String[] items = new String[count];
        for (int i = 0; i < count; i++)
        {
            items[i] = list.get(i);
        }
        AlertDialog.Builder builder = new Builder(this);
        if ("buy.getMaterialByBreedId".equals(data.getCmd()))
        {// 材质
            builder.setTitle("选择材质").setItems(items,
                    new DialogInterface.OnClickListener()
                    {

                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            etMaterial.setText(items[which]);
                        }
                    });
        } else if ("buy.getSpecsByBreedId".equals(data.getCmd()))
        {// 规格
            builder.setTitle("选择规格").setItems(items,
                    new DialogInterface.OnClickListener()
                    {

                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            etSpecs.setText(items[which]);
                        }
                    });
        }
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }


    @Subscriber(tag = "startActivity")
    private void scoreChange(String str)
    {
        Intent intent = new Intent(this, OrderCentreActivity.class);
        startActivity(intent);
    }

}
