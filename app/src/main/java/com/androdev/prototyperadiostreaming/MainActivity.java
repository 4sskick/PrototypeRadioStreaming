package com.androdev.prototyperadiostreaming;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import app.AppController;


public class MainActivity extends AppCompatActivity {

    private String url_radio = "http://url.radiostreamlive.com/radiocountrylive/low_ad.asx";
    //private String url_json = "https://github.com/jcheype/NabAlive/blob/master/applications/application-radio/src/main/resources/radio.json";
    private String url_json = "https://raw.githubusercontent.com/jcheype/NabAlive/master/applications/application-radio/src/main/resources/radio.json";
    private ProgressBar playSeekBar;
    private TextView tvRadioUrl;
    private Button buttonPlay;
    private Button buttonStopPlay;
    private MediaPlayer player;

    private String jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUIElement();
        initializeMediaPlayer();
    }

    private void initializeMediaPlayer() {
        player = new MediaPlayer();
        try{
            player.setDataSource(url_radio);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }catch(IllegalStateException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                playSeekBar.setIndeterminate(false);
                playSeekBar.setSecondaryProgress(100);
                Log.d("DEBUG", "" + percent);
            }
        });
    }

    private void initializeUIElement() {
        playSeekBar = (ProgressBar)findViewById(R.id.progressBar1);
        playSeekBar.setMax(100);
        playSeekBar.setVisibility(View.INVISIBLE);
        playSeekBar.setIndeterminate(true);

        buttonPlay = (Button)findViewById(R.id.buttonPlay);
        buttonStopPlay = (Button)findViewById(R.id.buttonStop);
        buttonStopPlay.setEnabled(false);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == buttonPlay) {
                    startPlaying();
                } else if (v == buttonStopPlay) {
                    stopPlaying();
                }
            }
        });
        buttonStopPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == buttonPlay) {
                    startPlaying();
                } else if (v == buttonStopPlay) {
                    stopPlaying();
                }
            }
        });
    }

    private void stopPlaying() {
        if(player.isPlaying()){
            player.stop();
            player.release();
            initializeMediaPlayer();
        }

        buttonPlay.setEnabled(true);
        buttonStopPlay.setEnabled(false);

        playSeekBar.setIndeterminate(true);
        playSeekBar.setVisibility(View.INVISIBLE);
    }

    private void startPlaying() {
        buttonStopPlay.setEnabled(true);
        buttonPlay.setEnabled(false);

        playSeekBar.setVisibility(View.VISIBLE);

        player.prepareAsync();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                player.start();
            }
        });

        /*JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url_json, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("DEBUG", response.toString());
                        try {
                            jsonResponse = "";
                            JSONObject valueObj = response.getJSONObject("fields");
                            String OUI_FM = valueObj.getString("OUI FM");
                            String RTL = valueObj.getString("RTL");
                            String Europe_1 = valueObj.getString("Europe 1");


                            jsonResponse = "";
                            jsonResponse += "OUT FM: "+OUI_FM+"\n\n";
                            jsonResponse += "RTL: "+RTL+"\n\n";
                            jsonResponse += "Europe 1: "+Europe_1+"\n\n";

                            Log.d("DEBUG",""+jsonResponse);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d("DEBUG", "Error: " + volleyError.getMessage());
                Toast.makeText(getApplicationContext(),
                        volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/

        JsonArrayRequest req = new JsonArrayRequest(url_json, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("DEBUG", response.toString());

                try {
                    jsonResponse = "";


                    for (int i = 0; i < response.length(); i++) {
                        //JSONObject values_obj = (JSONObject) response.get(i);
                        JSONObject values_obj = response.getJSONObject(i);

                        JSONArray valueArray = values_obj.optJSONArray("values");
                        /*String RTL = valueArray.getString("RTL");
                        String Fun_Radio = valueArray.getString("Fun Radio");
                        String Radio_Nova = valueArray.getString("Radio Nova");*/
                        for(int j = 0; j< valueArray.length();j++){
                            Log.d("DEBUG", valueArray.optString(j));
                        }

                    }
                    /*jsonResponse += "RTL: " + RTL + "\n\n";
                    jsonResponse += "Fun Radio: " + Fun_Radio + "\n\n";
                    jsonResponse += "Radio Nova: " + Radio_Nova + "\n\n";

                    Log.d("DEBUG", jsonResponse.toString());*/
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    Log.d("DEBUG",e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("DEBUG", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(player.isPlaying()){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
