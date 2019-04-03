package com.androdev.prototyperadiostreaming.view.activity.main_screen;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.androdev.prototyperadiostreaming.R;
import com.androdev.prototyperadiostreaming.adapter.GenericPagerAdapter;
import com.androdev.prototyperadiostreaming.base.BaseActivity;
import com.androdev.prototyperadiostreaming.base.BaseFragment;
import com.androdev.prototyperadiostreaming.view.fragment.HomeFragment;
import com.androdev.prototyperadiostreaming.view.fragment.InfoFragment;
import com.androdev.prototyperadiostreaming.view.fragment.LiveFragment;
import com.androdev.prototyperadiostreaming.view.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Septian Adi Wijaya on 01/04/19
 */
public class MainScreenActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.layout_bottom)
    FrameLayout layoutBottom;
    @BindView(R.id.action_home)
    LinearLayout actionHome;
    @BindView(R.id.action_live)
    LinearLayout actionLive;
    @BindView(R.id.action_news)
    LinearLayout actionNews;
    @BindView(R.id.action_info)
    LinearLayout actionInfo;

    private int mToTab = 0;

    private GenericPagerAdapter genericPagerAdapter;
    private List<BaseFragment> fragments;
    private ViewPager.OnPageChangeListener onPageChangeListener;

    public static void startActivity(BaseActivity activity, int toTab) {
        Intent a = new Intent(activity, MainScreenActivity.class);
        a.putExtra(Intent.EXTRA_TEXT, toTab);
        activity.startActivity(a);
    }

    @Override
    protected int getLayout() {
        return R.layout.a_screen_main;
    }

    @Override
    protected void setup() {

        if (getIntent() != null) {
            if (getIntent().hasExtra(Intent.EXTRA_TEXT)) {
                mToTab = getIntent().getIntExtra(Intent.EXTRA_TEXT, mToTab);

                switch (mToTab) {
                    case 0:
                        actionHome.setSelected(true);
                        break;
                    case 1:
                        actionLive.setSelected(true);
                        break;
                    case 2:
                        actionNews.setSelected(true);
                        break;
                    case 3:
                        actionInfo.setSelected(true);
                        break;
                }
            }
        }

        fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance());
        fragments.add(LiveFragment.newInstance());
        fragments.add(NewsFragment.newInstance());
        fragments.add(InfoFragment.newInstance());

        genericPagerAdapter = new GenericPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(genericPagerAdapter);
        onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        };
        viewPager.addOnPageChangeListener(onPageChangeListener);
        viewPager.setCurrentItem(mToTab);
    }

    @OnClick({R.id.action_home, R.id.action_live, R.id.action_news, R.id.action_info})
    public void onViewClicked(View view) {

        actionHome.setSelected(false);
        actionLive.setSelected(false);
        actionNews.setSelected(false);
        actionInfo.setSelected(false);

        switch (view.getId()) {
            case R.id.action_home:
                mToTab = 0;
                actionHome.setSelected(true);
                break;
            case R.id.action_live:
                mToTab = 1;
                actionLive.setSelected(true);
                break;
            case R.id.action_news:
                mToTab = 2;
                actionNews.setSelected(true);
                break;
            case R.id.action_info:
                mToTab = 3;
                actionInfo.setSelected(true);
                break;
        }
        viewPager.setCurrentItem(mToTab);

    }
}
