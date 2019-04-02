package com.androdev.prototyperadiostreaming.view.fragment;

import android.media.MediaPlayer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androdev.prototyperadiostreaming.R;
import com.androdev.prototyperadiostreaming.adapter.LiveAdapter;
import com.androdev.prototyperadiostreaming.base.BaseFragment;
import com.androdev.prototyperadiostreaming.utils.Util;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.AppController;
import butterknife.BindView;
import butterknife.OnClick;
import model.RadioStation;

/**
 * Created by Septian Adi Wijaya on 01/04/19
 */
public class LiveFragment extends BaseFragment {

    @BindView(R.id.tv_radio_url)
    TextView tvRadioUrl;
    @BindView(R.id.pb_monitor)
    ProgressBar pbMonitor;
    @BindView(R.id.list_radio)
    RecyclerView listRadio;
    @BindView(R.id.btn_play)
    ImageView btnPlay;
    @BindView(R.id.btn_stop)
    ImageView btnStop;

    private MediaPlayer player;
    private String reqResponse;
    private LiveAdapter adapter;

    private List<RadioStation> data;
    private String mCurrentPlaying;
    private String url_radio = null;
    private String url_json = "https://raw.githubusercontent.com/4sskick/radio-station/master/radio.json";

    public static LiveFragment newInstance() {
        return new LiveFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.f_live;
    }

    @Override
    protected void setup(View view) {
        pbMonitor.setMax(100);
        pbMonitor.setVisibility(View.INVISIBLE);
        pbMonitor.setIndeterminate(true);

        if (url_radio == null || player == null) {
            btnStop.setEnabled(false);
            btnPlay.setEnabled(true);
            stateButton();

            pbMonitor.setVisibility(View.INVISIBLE);
            player = new MediaPlayer();
        }

        player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                pbMonitor.setIndeterminate(false);
                pbMonitor.setSecondaryProgress(100);
            }
        });

        data = new ArrayList<>();
        adapter = new LiveAdapter(data, model -> {
            url_radio = model.getUrl_title();

            tvRadioUrl.setText(url_radio);
            stopPlaying();

//            Log.e("tagReq", "setup: " + model.toString());

        });
        listRadio.setLayoutManager(new LinearLayoutManager(getContext()));
        listRadio.setAdapter(adapter);

        setupRequest();
        showLoading();
    }

    private void startPlaying() {
        if (url_radio == null) {
            //should show alert
        } else {
            tvRadioUrl.setText(url_radio);
            btnStop.setEnabled(true);
            btnPlay.setEnabled(false);
            pbMonitor.setVisibility(View.VISIBLE);

            stateButton();

            try {
                /*set data resource for streaming*/
                player.setDataSource(url_radio);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (player != null) {
                /*prepare to play stream asyncronously*/
                player.prepareAsync();

                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        player.start();
                        Util.buildNotification(getContext(), getActivity(), mCurrentPlaying);
                    }
                });
            }
        }
    }

    private void stopPlaying() {
        if (player.isPlaying()) {
            player.stop();
            player.reset();
        }

        btnStop.setEnabled(false);
        btnPlay.setEnabled(true);

        stateButton();

        pbMonitor.setIndeterminate(true);
        pbMonitor.setVisibility(View.INVISIBLE);
    }

    private void setupRequest() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET
                , url_json
                , null
                , new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.d("DEBUG", response.toString());

                try {
                    reqResponse = "";
                    /*get object array in json format*/
                    JSONArray arrayObj = response.getJSONArray("radio_station");
                    for (int i = 0; i < arrayObj.length(); i++) {

                        JSONObject obj = arrayObj.getJSONObject(i);
                        RadioStation rs = new RadioStation();
                        rs.setTitle(obj.getString("name"));
                        rs.setUrl_title(obj.getString("values"));

                        data.add(rs);
                    }
                    Log.d("DEBUG", reqResponse.toString());
                    adapter.setData(data);
                    hideLoading();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    Log.d("DEBUG", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("DEBUG", "Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hideLoading();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    private void reState() {
        if (player != null || url_radio != null) {
            if (player.isPlaying()) {
                player.stop();
                player.reset();

                btnStop.setEnabled(false);
                btnPlay.setEnabled(true);
                stateButton();

                pbMonitor.setVisibility(View.INVISIBLE);
            } else {
                btnStop.setEnabled(false);
                btnPlay.setEnabled(true);
                stateButton();
            }
        } else {
            btnStop.setEnabled(false);
            btnPlay.setEnabled(true);
            stateButton();

            pbMonitor.setVisibility(View.VISIBLE);
        }
    }

    private void stateButton() {
        if (btnPlay.isEnabled()) {
            btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.play_live));
        } else {
            btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.play_live_pressed));
        }

        if (btnStop.isEnabled()) {
            btnStop.setImageDrawable(getResources().getDrawable(R.drawable.stop_live));
        } else {
            btnStop.setImageDrawable(getResources().getDrawable(R.drawable.stop_live_pressed));
        }
    }

    @OnClick({R.id.btn_play, R.id.btn_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                startPlaying();
                break;
            case R.id.btn_stop:
                stopPlaying();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("DEBUG", "onDestroy");
        if (player != null) {
            player.release();
            player = null;
        }
    }

    /*stop player when app clicked another tab*/
    @Override
    public void onPause() {
        super.onPause();
        Log.d("DEBUG", "onPause");
        reState();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("DEBUG", "onResume");
        url_radio = null;
        reState();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("DEBUG", "onStart");
        reState();
    }
}
