package model;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androdev.prototyperadiostreaming.R;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import app.AppController;

/**
 * Created by Administrator on 6/24/2015.
 */
public class CustomListAdapterNews extends BaseAdapter {

    private FragmentActivity fragment;
    private LayoutInflater inflater;
    private List<NewsStation> newsStation;

    ImageLoader imageLoader = AppController.getInstance().getmImageLoader();

    public CustomListAdapterNews(FragmentActivity fragment, List<NewsStation> newsStation){
        this.fragment = fragment;
        this.newsStation = newsStation;
    }

    @Override
    public int getCount() {
        return newsStation.size();
    }

    @Override
    public Object getItem(int position) {
        return newsStation.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater) fragment.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_news, null);
        }

        NetworkImageView thumbnailImg = (NetworkImageView)convertView.findViewById(R.id.icon);
        TextView titleNews = (TextView)convertView.findViewById(R.id.itemNews);
        TextView descNews = (TextView)convertView.findViewById(R.id.descriptionNews);

        /*getting list data for news*/
        NewsStation n = newsStation.get(position);

        /*set thumbnail img*/
        thumbnailImg.setImageUrl(n.getThumbnailUrl(), imageLoader);

        /*set titile*/
        titleNews.setText(n.getTitle());

        /*set description*/
        descNews.setText(n.getDesc());

        //check for odd or even to set alternate colors to the row background
        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#e9e2f2"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        return convertView;
    }
}
