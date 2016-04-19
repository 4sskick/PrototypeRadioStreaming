package com.androdev.prototyperadiostreaming;

import android.app.Fragment;
import android.app.ActionBar;
import android.app.FragmentTransaction;

/**
 * Created by Administrator on 6/7/2015.
 */

public class TabsListener implements ActionBar.TabListener {

    Fragment fragment;

    /*public TabsListener(Fragment fragment) {
        this.fragment = fragment;
    }
*/
    public TabsListener(Fragment fragment) {
        this.fragment = fragment;
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.remove(fragment);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
