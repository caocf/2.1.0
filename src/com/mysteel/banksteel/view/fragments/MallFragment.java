package com.mysteel.banksteel.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysteel.banksteeltwo.R;

import org.simple.eventbus.EventBus;

/**
 * 商城的fragment
 * Created by zoujian on 15/10/26.
 */
public class MallFragment extends BaseFragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_mall, null);
        EventBus.getDefault().register(this);
        return view;
    }
}
