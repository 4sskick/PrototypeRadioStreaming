package com.androdev.prototyperadiostreaming.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.androdev.prototyperadiostreaming.base.BaseFragment;

import java.util.List;

/**
 * Created by Septian Adi Wijaya on 01/04/19
 */
public class GenericPagerAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> fragments;

    public GenericPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getFragmentTitle();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void setFragments(List<BaseFragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public void setFragmentTitle(List<String> titles) {
        for (int i = 0; i < getCount(); i++) {
            getFragments().get(i).setFragmentTitle(titles.get(i));
        }
    }

    public List<BaseFragment> getFragments() {
        return fragments;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        // refresh all fragments when data set changed
        return FragmentStatePagerAdapter.POSITION_NONE;
    }
}
