package com.androdev.prototyperadiostreaming;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class MainScreenActivity extends ActionBarActivity {

    private ImageView imgbtn_live;
    private ImageView imgbtn_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        imgbtn_live = (ImageView)findViewById(R.id.imgbtn_live);
        imgbtn_news = (ImageView)findViewById(R.id.imgbtn_news);

        imgbtn_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent liveActivity = new Intent(MainScreenActivity.this, TabRealActivity.class);
                liveActivity.putExtra(Intent.EXTRA_TEXT, 1);
                startActivity(liveActivity);
            }
        });
        imgbtn_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent liveActivity = new Intent(MainScreenActivity.this, TabRealActivity.class);
                liveActivity.putExtra(Intent.EXTRA_TEXT, 2);
                startActivity(liveActivity);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
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
