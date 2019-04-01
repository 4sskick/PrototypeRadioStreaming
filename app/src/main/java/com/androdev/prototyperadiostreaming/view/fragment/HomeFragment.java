package com.androdev.prototyperadiostreaming.view.fragment;

import android.view.View;

import com.androdev.prototyperadiostreaming.R;
import com.androdev.prototyperadiostreaming.base.BaseFragment;

/**
 * Created by Septian Adi Wijaya on 01/04/19
 */
public class HomeFragment extends BaseFragment {

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.f_home;
    }

    @Override
    protected void setup(View view) {

    }
}
