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

import java.util.List;

/**
 * Created by Administrator on 6/17/2015.
 */
public class CustomListAdapter extends BaseAdapter {

    private FragmentActivity fragment;
    private LayoutInflater inflater;
    private List<RadioStation> listStation;

    public CustomListAdapter(FragmentActivity fragment, List<RadioStation> listStation){
        this.fragment = fragment;
        this.listStation = listStation;
    }

    @Override
    public int getCount() {
        return listStation.size();
    }

    @Override
    public String getItem(int position) {
        return String.valueOf(listStation.get(position));
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
            convertView = inflater.inflate(R.layout.list_station, null);
        }

        TextView title = (TextView)convertView.findViewById(R.id.itemname);
        TextView url_description = (TextView)convertView.findViewById(R.id.urlItem);

        RadioStation rs = listStation.get(position);

        title.setText(rs.getTitle());
        url_description.setText(rs.getUrl_title());

        //check for odd or even to set alternate colors to the row background
        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#e9e2f2"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        return convertView;
    }
}
