package com.androdev.prototyperadiostreaming.view.fragment;

import android.view.View;

import com.androdev.prototyperadiostreaming.R;
import com.androdev.prototyperadiostreaming.base.BaseFragment;

/**
 * Created by Septian Adi Wijaya on 01/04/19
 */
public class InfoFragment extends BaseFragment {

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.f_info;
    }

    @Override
    protected void setup(View view) {

    }
}
