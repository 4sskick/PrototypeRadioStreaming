package com.androdev.prototyperadiostreaming.view.activity.front_screen;

import android.view.View;

import com.androdev.prototyperadiostreaming.R;
import com.androdev.prototyperadiostreaming.base.BaseActivity;
import com.androdev.prototyperadiostreaming.view.activity.main_screen.MainScreenActivity;

import butterknife.OnClick;

public class FrontActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.a_front;
    }

    @Override
    protected void setup() {

    }

    @OnClick({R.id.imgbtn_live, R.id.imgbtn_news})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_live:
//                TabRealActivity.startActivity(FrontActivity.this, 1);
                MainScreenActivity.startActivity(this, 1);
                break;
            case R.id.imgbtn_news:
//                TabRealActivity.startActivity(FrontActivity.this, 2);
                MainScreenActivity.startActivity(this, 2);
                break;
        }
    }
}
