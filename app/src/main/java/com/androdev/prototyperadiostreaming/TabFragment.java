package com.androdev.prototyperadiostreaming;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class TabFragment extends Fragment {
    protected static final String TAG = "DEBUG";
    private static final String PREFS = "default_prefs";
    private static final String CURRENT_TAB = "current_tab";
    private Tab[] mTabs = new Tab[]{
            new Tab("Tab 1", "Home", R.drawable.home, R.id.tab1),
            new Tab("Tab 2", "Bookmarks", R.drawable.favorite_bookmark, R.id.tab2),
            new Tab("Tab 3", "Favorites", R.drawable.star, R.id.tab3),
            new Tab("Tab 4", "Users", R.drawable.user, R.id.tab4),
            new Tab("Tab 5", "Bastkets", R.drawable.shopping_basket, R.id.tab5)
    };

    private TabHost mTabHost;
    private LayoutInflater mFactory;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFactory = LayoutInflater.from(activity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveCurrentTab();
    }

    private void saveCurrentTab() {
        SharedPreferences sp = getActivity().getSharedPreferences(PREFS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt(CURRENT_TAB, mTabHost.getCurrentTab());
        ed.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tabs, container, false);
        mTabHost = (TabHost) root.findViewById(android.R.id.tabhost);
        setupTabs(mTabHost);
        restoreCurrentTab();
        return root;
    }

    private void restoreCurrentTab() {
        SharedPreferences sp = getActivity().getSharedPreferences(PREFS,
                Context.MODE_PRIVATE);
        final int current = sp.getInt(CURRENT_TAB, 2);
        mTabHost.setCurrentTab(current);
    }

    private void setupTabs(final TabHost tabHost) {
        tabHost.setup();
        for (final Tab tab : mTabs) {
            TabHost.TabSpec spec = createTab(tabHost, tab);
            tabHost.addTab(spec);
            attachFragment(tab);
        }

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.d(TAG, "which is showing ? tab id " + tabId);
            }
        });
    }

    private void attachFragment(final Tab tab) {
        Fragment frag = new FragmentTab();
        Bundle args = new Bundle();
        args.putString(FragmentTab.EXTRA_TITLE, tab.mTitle);
        frag.setArguments(args);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(tab.mStub, frag, tab.mTag);
        ft.commit();
    }

    private TabHost.TabSpec createTab(final TabHost tabHost, final Tab tab) {
        TabHost.TabSpec spec = tabHost.newTabSpec(tab.mTag);
        View indicator = mFactory.inflate(R.layout.tab_indicator, null,
                false);
        ImageView icon = (ImageView) indicator.findViewById(R.id.tab_icon);
        icon.setImageResource(tab.mIcon);

        TextView title = (TextView) indicator.findViewById(R.id.tab_title);
        title.setText(tab.mTitle);

        spec.setIndicator(indicator);
        spec.setContent(tab.mStub);

        Log.d(TAG, "mStub "+tab.mStub);
        return spec;
    }

    private class Tab {
        public String mTag;
        public String mTitle;
        public int mIcon;
        public int mStub;

        public Tab(final String tag, String title, int icon, int stub) {
            mTag = tag;
            mTitle = title;
            mIcon = icon;
            mStub = stub;
        }

        @Override
        public String toString() {
            return "[tag " + mTag + "]";
        }
    }

}
