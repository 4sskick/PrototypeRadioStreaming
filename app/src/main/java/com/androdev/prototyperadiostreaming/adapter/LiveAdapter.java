package com.androdev.prototyperadiostreaming.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androdev.prototyperadiostreaming.R;

import java.util.List;

import butterknife.BindView;
import model.RadioStation;

/**
 * Created by Septian Adi Wijaya on 01/04/19
 */
public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.LiveViewHolder> {

    private List<RadioStation> data;

    public LiveAdapter(List<RadioStation> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public LiveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.i_live, viewGroup, false);
        return new LiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LiveViewHolder liveViewHolder, int i) {
        liveViewHolder.bind();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class LiveViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemName)
        TextView tvItemName;
        @BindView(R.id.urlItem)
        TextView tvItemUrl;

        public LiveViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind() {
            RadioStation model = data.get(getAdapterPosition());

            tvItemName.setText(model.getTitle());
            tvItemUrl.setText(model.getUrl_title());
        }
    }

    public interface OnItemListener {
        void onItemClicked(RadioStation model);
    }
}