package com.mysteel.banksteel.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysteel.banksteel.entity.Steel;
import com.mysteel.banksteel.view.activity.SelectTypeActivity;
import com.mysteel.banksteeltwo.R;

/**
 * 精准找货的页面
 * Created by zoujian on 15/7/23.
 */
public class AccurateFindFoodFragment extends BaseFragment implements View.OnClickListener, View.OnTouchListener
{

    private LinearLayout ll_construction_steel, ll_hot_rolled_coil, ll_cut_deal,
            ll_plate_roll, ll_specific_steel, ll_steeltube, ll_proximate_matter, ll_cold_coating,
            ll_alloy_structual, ll_billet, ll_stainless_steel, ll_other_steel;

    private ImageView img_construction_steel, img_hot_rolled_coil, img_cut_deal, img_plate_roll,
            img_specific_steel, img_steeltube, img_proximate_matter, img_cold_coating, img_alloy_structual,
            img_billet, img_stainless_steel, img_other_steel;

    private TextView tv_construction_steel, tv_hot_rolled_coil, tv_cut_deal, tv_plate_roll,
            tv_specific_steel, tv_steeltube, tv_proximate_matter, tv_cold_coating, tv_alloy_structual,
            tv_billet, tv_stainless_steel, tv_other_steel;

    public final String PARENT_STEEL = "parentSteel";

    Steel steel = new Steel();

    private float xDown,yDown,xMove,yMove;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_accurate_findfood, null);
        initView(view);
        return view;
    }

    /**
     * 初始化组建
     *
     * @param view
     */
    private void initView(View view)
    {
        ll_construction_steel = (LinearLayout) view.findViewById(R.id.ll_construction_steel);//建筑钢材
        ll_construction_steel.setOnClickListener(this);
        img_construction_steel = (ImageView) view.findViewById(R.id.img_construction_steel);
        tv_construction_steel = (TextView) view.findViewById(R.id.tv_construction_steel);
        ll_construction_steel.setOnTouchListener(this);

        ll_hot_rolled_coil = (LinearLayout) view.findViewById(R.id.ll_hot_rolled_coil);//热轧板卷
        ll_hot_rolled_coil.setOnClickListener(this);
        img_hot_rolled_coil = (ImageView) view.findViewById(R.id.img_hot_rolled_coil);
        tv_hot_rolled_coil = (TextView) view.findViewById(R.id.tv_hot_rolled_coil);
        ll_hot_rolled_coil.setOnTouchListener(this);

        ll_cut_deal = (LinearLayout) view.findViewById(R.id.ll_cut_deal);//中厚板
        ll_cut_deal.setOnClickListener(this);
        img_cut_deal = (ImageView) view.findViewById(R.id.img_cut_deal);
        tv_cut_deal = (TextView) view.findViewById(R.id.tv_cut_deal);
        ll_cut_deal.setOnTouchListener(this);

        ll_plate_roll = (LinearLayout) view.findViewById(R.id.ll_plate_roll);//冷轧板卷
        ll_plate_roll.setOnClickListener(this);
        img_plate_roll = (ImageView) view.findViewById(R.id.img_plate_roll);
        tv_plate_roll = (TextView) view.findViewById(R.id.tv_plate_roll);
        ll_plate_roll.setOnTouchListener(this);

        ll_specific_steel = (LinearLayout) view.findViewById(R.id.ll_specific_steel);//品种钢
        ll_specific_steel.setOnClickListener(this);
        img_specific_steel = (ImageView) view.findViewById(R.id.img_specific_steel);
        tv_specific_steel = (TextView) view.findViewById(R.id.tv_specific_steel);
        ll_specific_steel.setOnTouchListener(this);

        ll_steeltube = (LinearLayout) view.findViewById(R.id.ll_steeltube);//管材
        ll_steeltube.setOnClickListener(this);
        img_steeltube = (ImageView) view.findViewById(R.id.img_steeltube);
        tv_steeltube = (TextView) view.findViewById(R.id.tv_steeltube);
        ll_steeltube.setOnTouchListener(this);

        ll_proximate_matter = (LinearLayout) view.findViewById(R.id.ll_proximate_matter);//型材
        ll_proximate_matter.setOnClickListener(this);
        img_proximate_matter = (ImageView) view.findViewById(R.id.img_proximate_matter);
        tv_proximate_matter = (TextView) view.findViewById(R.id.tv_proximate_matter);
        ll_proximate_matter.setOnTouchListener(this);

        ll_cold_coating = (LinearLayout) view.findViewById(R.id.ll_cold_coating);//涂镀
        ll_cold_coating.setOnClickListener(this);
        img_cold_coating = (ImageView) view.findViewById(R.id.img_cold_coating);
        tv_cold_coating = (TextView) view.findViewById(R.id.tv_cold_coating);
        ll_cold_coating.setOnTouchListener(this);

        ll_alloy_structual = (LinearLayout) view.findViewById(R.id.ll_alloy_structual);//优特钢
        ll_alloy_structual.setOnClickListener(this);
        img_alloy_structual = (ImageView) view.findViewById(R.id.img_alloy_structual);
        tv_alloy_structual = (TextView) view.findViewById(R.id.tv_alloy_structual);
        ll_alloy_structual.setOnTouchListener(this);

        ll_billet = (LinearLayout) view.findViewById(R.id.ll_billet);//钢坯
        ll_billet.setOnClickListener(this);
        img_billet = (ImageView) view.findViewById(R.id.img_billet);
        tv_billet = (TextView) view.findViewById(R.id.tv_billet);
        ll_billet.setOnTouchListener(this);

        ll_stainless_steel = (LinearLayout) view.findViewById(R.id.ll_stainless_steel);//不锈钢
        ll_stainless_steel.setOnClickListener(this);
        img_stainless_steel = (ImageView) view.findViewById(R.id.img_stainless_steel);
        tv_stainless_steel = (TextView) view.findViewById(R.id.tv_stainless_steel);
        ll_stainless_steel.setOnTouchListener(this);

        ll_other_steel = (LinearLayout) view.findViewById(R.id.ll_other_steel);//其他钢材
        ll_other_steel.setOnClickListener(this);
        img_other_steel = (ImageView) view.findViewById(R.id.img_other_steel);
        tv_other_steel = (TextView) view.findViewById(R.id.tv_other_steel);
        ll_other_steel.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(getActivity(), SelectTypeActivity.class);

        switch (v.getId())
        {
            case R.id.ll_construction_steel://建筑钢材
                steel.setId("01");
                steel.setName("建筑用钢");
                steel.setImageId(R.drawable.tu_jianzuyonggang);
                intent.putExtra(PARENT_STEEL, steel);
                startActivity(intent);
                break;

            case R.id.ll_hot_rolled_coil://热轧板卷
                steel.setId("02");
                steel.setName("热轧板卷");
                steel.setImageId(R.drawable.tu_rezabanjuan);
                intent.putExtra(PARENT_STEEL, steel);
                startActivity(intent);
                break;

            case R.id.ll_cut_deal://中厚板
                steel.setId("03");
                steel.setName("中厚板");
                steel.setImageId(R.drawable.tu_zhonghouban);
                intent.putExtra(PARENT_STEEL, steel);
                startActivity(intent);
                break;

            case R.id.ll_plate_roll://冷轧板卷
                steel.setId("04");
                steel.setName("冷轧板卷");
                steel.setImageId(R.drawable.tu_banjuan);
                intent.putExtra(PARENT_STEEL, steel);
                startActivity(intent);
                break;

            case R.id.ll_specific_steel://品种钢
                steel.setId("12");
                steel.setName("品种钢");
                steel.setImageId(R.drawable.tu_pingzhonggang);
                intent.putExtra(PARENT_STEEL, steel);
                startActivity(intent);
                break;

            case R.id.ll_steeltube://管材
                steel.setId("06");
                steel.setName("管材");
                steel.setImageId(R.drawable.tu_guancai);
                intent.putExtra(PARENT_STEEL, steel);
                startActivity(intent);
                break;

            case R.id.ll_proximate_matter://型材
                steel.setId("07");
                steel.setName("型材");
                steel.setImageId(R.drawable.tu_xingcai);
                intent.putExtra(PARENT_STEEL, steel);
                startActivity(intent);
                break;

            case R.id.ll_cold_coating://涂镀
                steel.setId("05");
                steel.setName("涂镀");
                steel.setImageId(R.drawable.tu_lengdu);
                intent.putExtra(PARENT_STEEL, steel);
                startActivity(intent);
                break;

            case R.id.ll_alloy_structual://优特钢
                steel.setId("10");
                steel.setName("优特钢");
                steel.setImageId(R.drawable.tu_youtegang);
                intent.putExtra(PARENT_STEEL, steel);
                startActivity(intent);
                break;

            case R.id.ll_billet://钢坯
                steel.setId("11");
                steel.setName("钢坯");
                steel.setImageId(R.drawable.tu_gangpi);
                intent.putExtra(PARENT_STEEL, steel);
                startActivity(intent);
                break;

            case R.id.ll_stainless_steel://不锈钢
                steel.setId("09");
                steel.setName("不锈钢");
                steel.setImageId(R.drawable.tu_buxiugang);
                intent.putExtra(PARENT_STEEL, steel);
                startActivity(intent);
                break;

            case R.id.ll_other_steel://其他钢材
                steel.setId("08");
                steel.setName("其他钢材");
                steel.setImageId(R.drawable.tu_otherguancai);
                intent.putExtra(PARENT_STEEL, steel);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        switch (v.getId())
        {
            case R.id.ll_construction_steel://建筑钢材

                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    ll_construction_steel.setBackgroundResource(R.color.font_white_one);
                    img_construction_steel.setBackgroundResource(R.drawable.tu_jianzuyonggang);
                    tv_construction_steel.setTextColor(getResources().getColor(R.color.font_black_one));
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    xDown = event.getRawX();
                    yDown = event.getRawY();
                    ll_construction_steel.setBackgroundResource(R.color.main_imred);
                    img_construction_steel.setBackgroundResource(R.drawable.tu_jianzuyonggang_white);
                    tv_construction_steel.setTextColor(getResources().getColor(R.color.font_white_one));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    xMove = event.getRawX();
                    yMove = event.getRawY();

                    if(xDown != xMove && yDown != yMove){
                        ll_construction_steel.setBackgroundResource(R.color.font_white_one);
                        img_construction_steel.setBackgroundResource(R.drawable.tu_jianzuyonggang);
                        tv_construction_steel.setTextColor(getResources().getColor(R.color.font_black_one));
                    }
//                    return false;
                }

                break;

            case R.id.ll_hot_rolled_coil://热轧板卷

                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    ll_hot_rolled_coil.setBackgroundResource(R.color.font_white_one);
                    img_hot_rolled_coil.setBackgroundResource(R.drawable.tu_rezabanjuan);
                    tv_hot_rolled_coil.setTextColor(getResources().getColor(R.color.font_black_one));
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    xDown = event.getRawX();
                    yDown = event.getRawY();
                    ll_hot_rolled_coil.setBackgroundResource(R.color.main_imred);
                    img_hot_rolled_coil.setBackgroundResource(R.drawable.tu_rezabanjuan_white);
                    tv_hot_rolled_coil.setTextColor(getResources().getColor(R.color.font_white_one));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    xMove = event.getRawX();
                    yMove = event.getRawY();

                    if(xDown != xMove && yDown != yMove){
                        ll_hot_rolled_coil.setBackgroundResource(R.color.font_white_one);
                        img_hot_rolled_coil.setBackgroundResource(R.drawable.tu_rezabanjuan);
                        tv_hot_rolled_coil.setTextColor(getResources().getColor(R.color.font_black_one));
                    }
                }
                break;

            case R.id.ll_cut_deal://中厚板

                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    ll_cut_deal.setBackgroundResource(R.color.font_white_one);
                    img_cut_deal.setBackgroundResource(R.drawable.tu_zhonghouban);
                    tv_cut_deal.setTextColor(getResources().getColor(R.color.font_black_one));
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    xDown = event.getRawX();
                    yDown = event.getRawY();
                    ll_cut_deal.setBackgroundResource(R.color.main_imred);
                    img_cut_deal.setBackgroundResource(R.drawable.tu_zhonghouban_white);
                    tv_cut_deal.setTextColor(getResources().getColor(R.color.font_white_one));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    xMove = event.getRawX();
                    yMove = event.getRawY();

                    if(xDown != xMove && yDown != yMove){
                        ll_cut_deal.setBackgroundResource(R.color.font_white_one);
                        img_cut_deal.setBackgroundResource(R.drawable.tu_zhonghouban);
                        tv_cut_deal.setTextColor(getResources().getColor(R.color.font_black_one));
                    }
                }
                break;

            case R.id.ll_plate_roll://冷轧板卷

                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    ll_plate_roll.setBackgroundResource(R.color.font_white_one);
                    img_plate_roll.setBackgroundResource(R.drawable.tu_banjuan);
                    tv_plate_roll.setTextColor(getResources().getColor(R.color.font_black_one));
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    xDown = event.getRawX();
                    yDown = event.getRawY();
                    ll_plate_roll.setBackgroundResource(R.color.main_imred);
                    img_plate_roll.setBackgroundResource(R.drawable.tu_banjuan_white);
                    tv_plate_roll.setTextColor(getResources().getColor(R.color.font_white_one));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    xMove = event.getRawX();
                    yMove = event.getRawY();

                    if(xDown != xMove && yDown != yMove){
                        ll_plate_roll.setBackgroundResource(R.color.font_white_one);
                        img_plate_roll.setBackgroundResource(R.drawable.tu_banjuan);
                        tv_plate_roll.setTextColor(getResources().getColor(R.color.font_black_one));
                    }
                }
                break;

            case R.id.ll_specific_steel://品种钢

                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    ll_specific_steel.setBackgroundResource(R.color.font_white_one);
                    img_specific_steel.setBackgroundResource(R.drawable.tu_pingzhonggang);
                    tv_specific_steel.setTextColor(getResources().getColor(R.color.font_black_one));
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    xDown = event.getRawX();
                    yDown = event.getRawY();
                    ll_specific_steel.setBackgroundResource(R.color.main_imred);
                    img_specific_steel.setBackgroundResource(R.drawable.tu_pingzhonggang_white);
                    tv_specific_steel.setTextColor(getResources().getColor(R.color.font_white_one));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    xMove = event.getRawX();
                    yMove = event.getRawY();

                    if(xDown != xMove && yDown != yMove){
                        ll_specific_steel.setBackgroundResource(R.color.font_white_one);
                        img_specific_steel.setBackgroundResource(R.drawable.tu_pingzhonggang);
                        tv_specific_steel.setTextColor(getResources().getColor(R.color.font_black_one));
                    }
                }
                break;

            case R.id.ll_steeltube://管材
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    ll_steeltube.setBackgroundResource(R.color.font_white_one);
                    img_steeltube.setBackgroundResource(R.drawable.tu_guancai);
                    tv_steeltube.setTextColor(getResources().getColor(R.color.font_black_one));
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    xDown = event.getRawX();
                    yDown = event.getRawY();
                    ll_steeltube.setBackgroundResource(R.color.main_imred);
                    img_steeltube.setBackgroundResource(R.drawable.tu_guancai_white);
                    tv_steeltube.setTextColor(getResources().getColor(R.color.font_white_one));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    xMove = event.getRawX();
                    yMove = event.getRawY();

                    if(xDown != xMove && yDown != yMove){
                        ll_steeltube.setBackgroundResource(R.color.font_white_one);
                        img_steeltube.setBackgroundResource(R.drawable.tu_guancai);
                        tv_steeltube.setTextColor(getResources().getColor(R.color.font_black_one));
                    }
                }
                break;

            case R.id.ll_proximate_matter://型材
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    ll_proximate_matter.setBackgroundResource(R.color.font_white_one);
                    img_proximate_matter.setBackgroundResource(R.drawable.tu_xingcai);
                    tv_proximate_matter.setTextColor(getResources().getColor(R.color.font_black_one));
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    xDown = event.getRawX();
                    yDown = event.getRawY();
                    ll_proximate_matter.setBackgroundResource(R.color.main_imred);
                    img_proximate_matter.setBackgroundResource(R.drawable.tu_xingcai_white);
                    tv_proximate_matter.setTextColor(getResources().getColor(R.color.font_white_one));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    xMove = event.getRawX();
                    yMove = event.getRawY();

                    if(xDown != xMove && yDown != yMove){
                        ll_proximate_matter.setBackgroundResource(R.color.font_white_one);
                        img_proximate_matter.setBackgroundResource(R.drawable.tu_xingcai);
                        tv_proximate_matter.setTextColor(getResources().getColor(R.color.font_black_one));
                    }
                }
                break;

            case R.id.ll_cold_coating://涂镀
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    ll_cold_coating.setBackgroundResource(R.color.font_white_one);
                    img_cold_coating.setBackgroundResource(R.drawable.tu_lengdu);
                    tv_cold_coating.setTextColor(getResources().getColor(R.color.font_black_one));
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    xDown = event.getRawX();
                    yDown = event.getRawY();
                    ll_cold_coating.setBackgroundResource(R.color.main_imred);
                    img_cold_coating.setBackgroundResource(R.drawable.tu_lengdu_white);
                    tv_cold_coating.setTextColor(getResources().getColor(R.color.font_white_one));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    xMove = event.getRawX();
                    yMove = event.getRawY();

                    if(xDown != xMove && yDown != yMove){
                        ll_cold_coating.setBackgroundResource(R.color.font_white_one);
                        img_cold_coating.setBackgroundResource(R.drawable.tu_lengdu);
                        tv_cold_coating.setTextColor(getResources().getColor(R.color.font_black_one));
                    }
                }
                break;

            case R.id.ll_alloy_structual://优特钢
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    ll_alloy_structual.setBackgroundResource(R.color.font_white_one);
                    img_alloy_structual.setBackgroundResource(R.drawable.tu_youtegang);
                    tv_alloy_structual.setTextColor(getResources().getColor(R.color.font_black_one));
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    xDown = event.getRawX();
                    yDown = event.getRawY();
                    ll_alloy_structual.setBackgroundResource(R.color.main_imred);
                    img_alloy_structual.setBackgroundResource(R.drawable.tu_youtegang_white);
                    tv_alloy_structual.setTextColor(getResources().getColor(R.color.font_white_one));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    xMove = event.getRawX();
                    yMove = event.getRawY();

                    if(xDown != xMove && yDown != yMove){
                        ll_alloy_structual.setBackgroundResource(R.color.font_white_one);
                        img_alloy_structual.setBackgroundResource(R.drawable.tu_youtegang);
                        tv_alloy_structual.setTextColor(getResources().getColor(R.color.font_black_one));
                    }
                }
                break;

            case R.id.ll_billet://钢坯
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    ll_billet.setBackgroundResource(R.color.font_white_one);
                    img_billet.setBackgroundResource(R.drawable.tu_gangpi);
                    tv_billet.setTextColor(getResources().getColor(R.color.font_black_one));
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    xDown = event.getRawX();
                    yDown = event.getRawY();
                    ll_billet.setBackgroundResource(R.color.main_imred);
                    img_billet.setBackgroundResource(R.drawable.tu_gangpi_white);
                    tv_billet.setTextColor(getResources().getColor(R.color.font_white_one));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    xMove = event.getRawX();
                    yMove = event.getRawY();

                    if(xDown != xMove && yDown != yMove){
                        ll_billet.setBackgroundResource(R.color.font_white_one);
                        img_billet.setBackgroundResource(R.drawable.tu_gangpi);
                        tv_billet.setTextColor(getResources().getColor(R.color.font_black_one));
                    }
                }
                break;

            case R.id.ll_stainless_steel://不锈钢
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    ll_stainless_steel.setBackgroundResource(R.color.font_white_one);
                    img_stainless_steel.setBackgroundResource(R.drawable.tu_buxiugang);
                    tv_stainless_steel.setTextColor(getResources().getColor(R.color.font_black_one));
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    xDown = event.getRawX();
                    yDown = event.getRawY();
                    ll_stainless_steel.setBackgroundResource(R.color.main_imred);
                    img_stainless_steel.setBackgroundResource(R.drawable.tu_buxiugang_white);
                    tv_stainless_steel.setTextColor(getResources().getColor(R.color.font_white_one));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    xMove = event.getRawX();
                    yMove = event.getRawY();

                    if(xDown != xMove && yDown != yMove){
                        ll_stainless_steel.setBackgroundResource(R.color.font_white_one);
                        img_stainless_steel.setBackgroundResource(R.drawable.tu_buxiugang);
                        tv_stainless_steel.setTextColor(getResources().getColor(R.color.font_black_one));
                    }
                }
                break;

            case R.id.ll_other_steel://其他钢材
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    ll_other_steel.setBackgroundResource(R.color.font_white_one);
                    img_other_steel.setBackgroundResource(R.drawable.tu_otherguancai);
                    tv_other_steel.setTextColor(getResources().getColor(R.color.font_black_one));
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    xDown = event.getRawX();
                    yDown = event.getRawY();
                    ll_other_steel.setBackgroundResource(R.color.main_imred);
                    img_other_steel.setBackgroundResource(R.drawable.tu_otherguancai_white);
                    tv_other_steel.setTextColor(getResources().getColor(R.color.font_white_one));
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    xMove = event.getRawX();
                    yMove = event.getRawY();

                    if(xDown != xMove && yDown != yMove){
                        ll_other_steel.setBackgroundResource(R.color.font_white_one);
                        img_other_steel.setBackgroundResource(R.drawable.tu_otherguancai);
                        tv_other_steel.setTextColor(getResources().getColor(R.color.font_black_one));
                    }
                }
                break;
        }
        return false;
    }
}
