package com.mysteel.banksteel.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mysteel.banksteel.ao.IBuyCenter;
import com.mysteel.banksteel.ao.impl.BuyImpl;
import com.mysteel.banksteel.entity.BaseData;
import com.mysteel.banksteel.entity.CitysData;
import com.mysteel.banksteel.util.Constants;
import com.mysteel.banksteel.util.RequestUrl;
import com.mysteel.banksteel.util.Tools;
import com.mysteel.banksteel.view.adapters.HotContentAdapter;
import com.mysteel.banksteel.view.adapters.LetterGroupAdapter;
import com.mysteel.banksteel.view.interfaceview.IBuyView;
import com.mysteel.banksteel.view.ui.LetterList;
import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 城市的列表
 * Created by zoujian on 15/8/11.
 */
public class CityFragment extends BaseFragment implements IBuyView, LetterList.OnTouchingLetterChangedListener
{

    private LayoutInflater inflater;

    private EditText etSearch;
    /**
     * 热门内容视图
     */
    private View hotContentView;
    private GridView gvHotContent;

    /**
     * 所有城市
     */
    private ListView lvBrandCity;
    private LetterList lvLetter;
    private TextView tvLetterTip;
    private ProgressBar progressBar;

    private HotContentAdapter hotContentAdapter;
    private LetterGroupAdapter letterGroupAdapter;

    private IBuyCenter cityListImpl;
    private CitysData citys; // 接受服务器响应的城市数据

    private Map<String, Integer> letterPositionMap = new HashMap<String, Integer>(); // 字母在list中的位置和字母的对应关系
    private List<String> dataList = new ArrayList<String>(); // 用于adapter适配的城市列表数据

    public static final String RETURN_DATA = "returnData";
    private TextView tv_item_letter;//热门城市显示布局

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_citylist, null);
        initView(view);
        initData();
        EventBus.getDefault().register(this);
        return view;
    }

    private void initData()
    {
        cityListImpl = new BuyImpl(getActivity(), this);

        // 页面不同，拼接不同的请求地址
        String url = RequestUrl.getInstance(getActivity()).getUrl_queryAreaByType(getActivity());
        cityListImpl.getCityList(url, Constants.INTERFACE_getCitys);
    }

    private void initView(View view)
    {
        inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        etSearch = (EditText) view.findViewById(R.id.et_search_value);
        /** 加载热门内容视图 */
        hotContentView = inflater.inflate(R.layout.hot_city, null);
        tv_item_letter = (TextView) hotContentView.findViewById(R.id.tv_item_letter);
        gvHotContent = (GridView) hotContentView.findViewById(R.id.gv_hot_content);

        lvBrandCity = (ListView) view.findViewById(R.id.lv_brand_city);
        lvLetter = (LetterList) view.findViewById(R.id.lv_letter);
        tvLetterTip = (TextView) view.findViewById(R.id.tv_letter_tip);
        progressBar = (ProgressBar) view.findViewById(R.id.pb_progressbar);

        lvLetter.setOnTouchingLetterChangedListener(this);

        /** 头部热门城市的点击事件 */
        gvHotContent.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
//                Intent intent = new Intent();
//                intent.putExtra(RETURN_DATA, citys.getHotCitys().get(position));
//                Toast.makeText(getActivity(), citys.getHotCitys().get(position), Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(citys.getHotCitys().get(position), "CityFragment_city");
            }
        });

        /** list的点击事件 */
        lvBrandCity.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                if (Tools.isLetter(dataList.get(position - 1)))
                {
                    return;
                } else
                {
//                    Intent intent = new Intent();
//                    intent.putExtra(RETURN_DATA, dataList.get(position - 1));
//                    Toast.makeText(getActivity(), dataList.get(position - 1), Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(dataList.get(position - 1), "CityFragment_city");
                }

            }
        });
    }

    @Override
    public void updateView(BaseData data)
    {
        citys = (CitysData) data;

        // 上面的热门内容
        hotContentAdapter = new HotContentAdapter(getActivity(), citys.getHotCitys(), true);
        gvHotContent.setAdapter(hotContentAdapter);
        addHotContentToList(citys.getHotCitys());

        // 下面的列表数据
        sort(); // 排序
        int position = 1;
        if (citys != null && citys.getData() != null)
        {
            for (int i = 0; i < citys.getData().size(); i++)
            {
                dataList.add(citys.getData().get(i).getPy()); // 添加字母到list中
                // 城市
                dataList.addAll(citys.getData().get(i).getCitys()); // 添加城市到list中
                letterPositionMap.put(citys.getData().get(i).getPy(), position);
                position = dataList.size() + 1;
            }
        }
        letterGroupAdapter = new LetterGroupAdapter(getActivity(), dataList, true);
        lvBrandCity.setAdapter(letterGroupAdapter);
    }

    /**
     * 根据热门内容数量和item高度计算gridview的高度，并作为header添加到list中
     * 如果要做修改，请参照布局文件中GridView垂直间距（20）和Item的高度（50）
     *
     * @param list
     */
    private void addHotContentToList(List<String> list)
    {
        int itemCount = 0;
        if (list.size() > 0)
        {
            tv_item_letter.setVisibility(View.VISIBLE);
        }
        if (list != null)
        {
            itemCount = list.size();
            int rows = (itemCount + 3 - 1) / 3;
            int height = rows * 50 + (rows) * 20 + 40;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT, Tools.dip2px(getActivity(), height));
            gvHotContent.setLayoutParams(params);
            lvBrandCity.addHeaderView(hotContentView);
        }
    }

    /**
     * 对list按照拼音字母排序，服务器返回的数据ABCDE字母是乱序的。
     */
    public void sort()
    {
        Collections.sort(citys.getData(), new Comparator<CitysData.CityBean>()
        {
            @Override
            public int compare(CitysData.CityBean lhs, CitysData.CityBean rhs)
            {
                if (lhs.getPy() != null && rhs.getPy() != null)
                {
                    return lhs.getPy().compareToIgnoreCase(rhs.getPy());
                } else
                {
                    return 0;
                }
            }
        });
    }

    @Override
    public void isShowDialog(boolean flag)
    {
        if (flag)
        {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(100);
        } else
        {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onTouchingLetterChanged(String letter)
    {
        tvLetterTip.setText(letter);
        tvLetterTip.setVisibility(View.VISIBLE);
        if (!"☆".equals(letter) && letterPositionMap.get(letter) != null)
        {
            lvBrandCity.setSelection(letterPositionMap.get(letter));

        } else if ("☆".equals(letter))
        {
            lvBrandCity.setSelection(0);
        }
    }

    @Override
    public void onTouchingLetterUp()
    {
        tvLetterTip.setVisibility(View.GONE);
    }

}
