package com.androdev.prototyperadiostreaming.view.fragment;

import android.view.View;

import com.androdev.prototyperadiostreaming.R;
import com.androdev.prototyperadiostreaming.base.BaseFragment;

/**
 * Created by Septian Adi Wijaya on 01/04/19
 */
public class NewsFragment extends BaseFragment {

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.f_news;
    }

    @Override
    protected void setup(View view) {

    }
}
