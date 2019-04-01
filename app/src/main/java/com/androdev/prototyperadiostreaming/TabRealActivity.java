package com.androdev.prototyperadiostreaming;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class TabRealActivity extends
        AppCompatActivity {

    private FragmentTabHost mTabHost;

    private static final String PREFS = "default_prefs";
    private static final String CURRENT_TAB = "current_tab";
    private int mCurrent_tab = 0;

    public static void startActivity(AppCompatActivity activity, int toTab) {
        Intent a = new Intent(activity, TabRealActivity.class);
        a.putExtra(Intent.EXTRA_TEXT, toTab);
        activity.startActivity(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_real);

        /*cek intent value*/
        Intent intent = this.getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            mCurrent_tab = intent.getIntExtra(intent.EXTRA_TEXT, mCurrent_tab);
        }

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.home, R.drawable.home)),
                FragmentTabReal.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.live, R.drawable.favorite_bookmark)),
                FragmentTabReal2.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.news, R.drawable.star)),
                FragmentTabReal3.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab4").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.info, R.drawable.user)),
                FragmentTabReal4.class, null);

        restoreCurrentTab();
    }

    private void restoreCurrentTab() {
        SharedPreferences sp = this.getSharedPreferences(PREFS,
                Context.MODE_PRIVATE);
        int current = sp.getInt(CURRENT_TAB, mCurrent_tab);
        mTabHost.setCurrentTab(current);
    }

    private View getTabIndicator(Context context, int title, int icon) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_indicator, null);
        ImageView iv = (ImageView) view.findViewById(R.id.tab_icon);
        iv.setImageResource(icon);
        TextView tv = (TextView) view.findViewById(R.id.tab_title);
        tv.setText(title);
        return view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab_real, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
