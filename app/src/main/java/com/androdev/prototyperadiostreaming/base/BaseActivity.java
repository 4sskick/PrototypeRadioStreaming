package com.androdev.prototyperadiostreaming.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.androdev.prototyperadiostreaming.R;

import butterknife.ButterKnife;

/**
 * Created by Septian Adi Wijaya on 01/04/19
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
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

    public synchronized ProgressDialog showLoading() {
        mProgressDialog = new ProgressDialog(this);
        if (mProgressDialog.getWindow() != null) {
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.c_progress);

        return mProgressDialog;
    }
}
