package com.androdev.prototyperadiostreaming;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.AppController;
import model.CustomListAdapterNews;
import model.NewsStation;


public class FragmentTabReal3 extends Fragment {

    private String news;

    //private String url_json = "http://192.168.43.82/_project/_json/radio.json";
    private String url_json = "https://raw.githubusercontent.com/4sskick/radio-station/master/radio.json";
    private String jsonResponse;
    private String title;
    private String desc;

    private List<NewsStation> listNews = new ArrayList<NewsStation>();
    private CustomListAdapterNews adapter;
    private ListView listViewNews;
    private ProgressDialog mProgresDialog;

    ImageLoader imageLoader = AppController.getInstance().getmImageLoader();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_fragment_tab_real3, container, false);

        listViewNews = (ListView)root.findViewById(R.id.list_news);

        adapter = new CustomListAdapterNews(getActivity(), listNews);

        /*check if the adapter has item on it*/
        if(adapter.isEmpty()){
            listViewNews.setAdapter(adapter);
        }else{
            listNews.clear();
            listViewNews.setAdapter(adapter);
        }

        listViewNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                title = ((TextView)view.findViewById(R.id.itemNews)).getText().toString();
                desc = ((TextView)view.findViewById(R.id.descriptionNews)).getText().toString();
                /*Toast.makeText(getActivity(), title, Toast.LENGTH_SHORT).show();*/
                final Dialog mDialog = new Dialog(getActivity());

                /*set window*/
                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.setContentView(R.layout.custom_dialog_news);

                NetworkImageView imgDialog = (NetworkImageView)mDialog.findViewById(R.id.imgThum);
                Button dialog_btnShare = (Button)mDialog.findViewById(R.id.dialog_btnShare);

                TextView dialog_title = (TextView)mDialog.findViewById(R.id.dialog_title);
                TextView dialog_tgl = (TextView)mDialog.findViewById(R.id.dialog_tgl);
                TextView dialog_value = (TextView)mDialog.findViewById(R.id.dialog_value);

                /*getting list data for news*/
                NewsStation n = listNews.get(position);

                /*set thumbnail img*/
                imgDialog.setImageUrl(n.getThumbnailUrl(), imageLoader);

                /*set title*/
                dialog_title.setText(n.getTitle());

                /*set tgl*/
                dialog_tgl.setText(n.getTgl());

                /*set value for news*/
                dialog_value.setText(n.getValue());

                dialog_btnShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    /*click to share*/
                    public void onClick(View v) {
                        shareIt(desc);
                    }
                });
                mDialog.show();
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
                            JSONArray arrayObj = response.getJSONArray("feed");
                            for (int i = 0; i < arrayObj.length(); i++) {

                                JSONObject obj = arrayObj.getJSONObject(i);
                                NewsStation ns = new NewsStation();

                                ns.setThumbnailUrl(obj.getString(("img")));
                                ns.setTitle(obj.getString("judul"));
                                ns.setDesc(obj.getString("description"));
                                ns.setTgl(obj.getString("tanggal"));
                                ns.setValue(obj.getString("isi"));

                            /*added in listview*/
                                listNews.add(ns);
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

        return root;
    }

    private void shareIt(String descBody) {
        //your code here
        String url = "http://indoviberadio.com";

        Intent shareingIntent = new Intent(Intent.ACTION_SEND);
        /*set type to share*/
        shareingIntent.setType("text/plain");
        /*subject for email share*/
        shareingIntent.putExtra(Intent.EXTRA_SUBJECT, "Radio Vibe Application Debug");
        /*content to shared*/
        shareingIntent.putExtra(Intent.EXTRA_TEXT, descBody+" #VibeIndoApp \n"+url);
        startActivity(Intent.createChooser(shareingIntent, "Share via"));
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

    /*coloring custom listview*/
    private class CustomRowAdapter extends ArrayAdapter {

        private String[] data_judul;
        private String[] data_description;

        public CustomRowAdapter(Context context, int layout, int id, String[] data_judul, String[] data_description) {
            super(context, layout, id, data_description);

            /*set values*/
            this.data_judul = data_judul;
            this.data_description = data_description;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = convertView;
            ViewHolder holder;

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.i_news, null);

                holder = new ViewHolder();
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            //get reference to the row
            /*View view = super.getView(position, convertView, parent);*/
            holder.titleNews = (TextView) view.findViewById(R.id.itemNews);
            holder.descriptionNews = (TextView) view.findViewById(R.id.descriptionNews);

            holder.titleNews.setText(data_judul[position]);
            holder.descriptionNews.setText(data_description[position]);

            //check for odd or even to set alternate colors to the row background
            if (position % 2 == 0) {
                view.setBackgroundColor(Color.parseColor("#e9e2f2"));
            } else {
                view.setBackgroundColor(Color.parseColor("#ffffff"));
            }

            return view;
        }
    }
}
