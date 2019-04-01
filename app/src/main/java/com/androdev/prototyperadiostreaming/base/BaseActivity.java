package com.androdev.prototyperadiostreaming.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Septian Adi Wijaya on 01/04/19
 */
public abstract class BaseActivity extends AppCompatActivity {

    public Activity mActivity;

    protected abstract int getLayout();

    protected abstract void setup();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        mActivity = this;
        setup();
    }
}
