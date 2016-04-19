package com.androdev.prototyperadiostreaming;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import model.CustomListAdapter;
import model.RadioStation;


public class FragmentTabReal2 extends Fragment {

    private String url_radio = null;
    private String url_json = "https://raw.githubusercontent.com/4sskick/radio-station/master/radio.json";
    //private String url_json = "http://192.168.43.82/_project/_json/radio.json";
    private String jsonResponse;
    private String titleRadio;

    private MediaPlayer player;

    private ProgressBar playSeekBar;
    private TextView radioUrl;
    private ImageView buttonPlay;
    private ImageView buttonStopPlay;
    private ProgressDialog mProgresDialog;

    /*EXPERIMENTS LAYOUT*/
    private List<RadioStation> listStation = new ArrayList<RadioStation>();
    private CustomListAdapter adapter;
    ListView listViewRadio;
    /*END*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_fragment_tab_real2, container, false);
        Log.d("DEBUG", "onCreate");

        radioUrl = (TextView) root.findViewById(R.id.radio_url_tv);
        buttonPlay = (ImageView) root.findViewById(R.id.buttonPlay);
        buttonStopPlay = (ImageView) root.findViewById(R.id.buttonStop);
        playSeekBar = (ProgressBar) root.findViewById(R.id.progressBar1);
        playSeekBar.setMax(100);
        playSeekBar.setVisibility(View.INVISIBLE);
        playSeekBar.setIndeterminate(true);

        listViewRadio = (ListView) root.findViewById(R.id.list_radio);

        adapter = new CustomListAdapter(getActivity(), listStation);

        if(adapter.isEmpty()){
            listViewRadio.setAdapter(adapter);
        }else{
            listStation.clear();
            listViewRadio.setAdapter(adapter);
        }

        listViewRadio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                url_radio = ((TextView)view.findViewById(R.id.urlItem)).getText().toString();
                titleRadio = ((TextView)view.findViewById(R.id.itemname)).getText().toString();

                radioUrl.setText(url_radio);
                /*Toast.makeText(getActivity(), url_radio, Toast.LENGTH_SHORT).show();*/
                stopPlaying();
            }
        });

        mProgresDialog = new ProgressDialog(getActivity());
        mProgresDialog.setMessage("Loading...");
        mProgresDialog.setCancelable(false);

        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url_json, null,
                new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.d("DEBUG", response.toString());

                try {
                    jsonResponse = "";
                    /*get object array in json format*/
                    JSONArray arrayObj = response.getJSONArray("radio_station");
                    for (int i = 0; i < arrayObj.length(); i++) {

                        JSONObject obj = arrayObj.getJSONObject(i);
                        RadioStation rs = new RadioStation();
                        rs.setTitle(obj.getString("name"));
                        rs.setUrl_title(obj.getString("values"));

                        /*added in listview*/
                        listStation.add(rs);
                    }
                    Log.d("DEBUG", jsonResponse.toString());
                    adapter.notifyDataSetChanged();
                    hideDialog();
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
                hideDialog();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlaying();
            }
        });
        buttonStopPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
            }
        });

        if (url_radio == null || player == null) {
            buttonStopPlay.setEnabled(false);
            buttonPlay.setEnabled(true);
            stateButton();

            playSeekBar.setVisibility(View.INVISIBLE);
            player = new MediaPlayer();
        }

        player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                playSeekBar.setIndeterminate(false);
                playSeekBar.setSecondaryProgress(100);
            }
        });
        return root;
    }

    private void stopPlaying() {
        if (player.isPlaying()) {
            player.stop();
            player.reset();
        }

        buttonStopPlay.setEnabled(false);
        buttonPlay.setEnabled(true);
        playSeekBar.setIndeterminate(true);
        stateButton();

        playSeekBar.setVisibility(View.INVISIBLE);
    }

    private void startPlaying() {

        /*check url radio*/
        if (url_radio == null) {
            /*create dialog interface*/
            showAlert();
        } else {
            /*set interface*/
            radioUrl.setText(url_radio);
            buttonStopPlay.setEnabled(true);
            buttonPlay.setEnabled(false);
            stateButton();

            playSeekBar.setVisibility(View.VISIBLE);

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
                        buildNotification(titleRadio);
                    }
                });
            }
        }
    }

    /*stop the player when app is exit*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("DEBUG", "onDestroy");
        if(player != null){
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

    private void showDialog() {
        if(!mProgresDialog.isShowing()) {
            mProgresDialog.show();
        }
    }

    private void hideDialog() {
        if(mProgresDialog != null){
            mProgresDialog.dismiss();
            mProgresDialog = null;
        }
    }

    private void showAlert() {
        DialogInterface.OnClickListener mDialog = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        mBuilder.setMessage("Choose Radio Station First").setPositiveButton("OK", mDialog).show();
    }

    private void reState() {
        if (player != null || url_radio != null) {
            if (player.isPlaying()) {
                player.stop();
                player.reset();

                buttonStopPlay.setEnabled(false);
                buttonPlay.setEnabled(true);
                stateButton();

                playSeekBar.setVisibility(View.INVISIBLE);
            } else {
                buttonStopPlay.setEnabled(false);
                buttonPlay.setEnabled(true);
                stateButton();
            }
        } else {
            buttonStopPlay.setEnabled(false);
            buttonPlay.setEnabled(true);
            stateButton();

            playSeekBar.setVisibility(View.VISIBLE);
        }
    }

    private void buildNotification(String titleRadio) {
        /** Create an intent that will be fired when the user clicks the notification.
         * The intent needs to be packaged into a {@link android.app.PendingIntent} so that the
         * notification service can fire it on our behalf.
         */
        Intent intent = new Intent(getActivity().getApplicationContext(), TabRealActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, 1);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        /**
         * Use NotificationCompat.Builder to set up our notification.
         */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());

        /** Set the icon that will appear in the notification bar. This icon also appears
         * in the lower right hand corner of the notification itself.
         *
         * Important note: although you can use any drawable as the small icon, Android
         * design guidelines state that the icon should be simple and monochrome. Full-color
         * bitmaps or busy images don't render well on smaller screens and can end up
         * confusing the user.
         */
        builder.setSmallIcon(R.drawable.ic_launcher);

        // Set the intent that will fire when the user taps the notification.
        builder.setContentIntent(pendingIntent);

        // Set the notification to auto-cancel. This means that the notification will disappear
        // after the user taps it, rather than remaining until it's explicitly dismissed.
        builder.setAutoCancel(true);

        /**
         *Build the notification's appearance.
         * Set the large icon, which appears on the left of the notification. In this
         * sample we'll set the large icon to be the same as our app icon. The app icon is a
         * reasonable default if you don't have anything more compelling to use as an icon.
         */
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));

        /**
         * Set the text of the notification. This sample sets the three most commononly used
         * text areas:
         * 1. The content title, which appears in large type at the top of the notification
         * 2. The content text, which appears in smaller text below the title
         * 3. The subtext, which appears under the text on newer devices. Devices running
         *    versions of Android prior to 4.2 will ignore this field, so don't use it for
         *    anything vital!
         */
        builder.setContentTitle("Vibe Radio");
        builder.setContentText("Now Playing " + titleRadio);

        /**
         * Send the notification. This will immediately display the notification icon in the
         * notification bar.
         */
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    private void stateButton() {
        if (buttonPlay.isEnabled()) {
            buttonPlay.setImageDrawable(getResources().getDrawable(R.drawable.play_live));
        } else {
            buttonPlay.setImageDrawable(getResources().getDrawable(R.drawable.play_live_pressed));
        }

        if (buttonStopPlay.isEnabled()) {
            buttonStopPlay.setImageDrawable(getResources().getDrawable(R.drawable.stop_live));
        } else {
            buttonStopPlay.setImageDrawable(getResources().getDrawable(R.drawable.stop_live_pressed));
        }
    }
}
