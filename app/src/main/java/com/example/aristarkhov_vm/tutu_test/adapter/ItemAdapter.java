package com.example.aristarkhov_vm.tutu_test.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.aristarkhov_vm.tutu_test.model.Station;
import com.example.aristarkhov_vm.tutu_test.R;

import java.util.List;

/**
 * Created by noyr on 15-Oct-16.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    //
    public interface OnItemClickListener {
        void onItemClick(Station station);
    }
    private List<Station> stationList;
    private final OnItemClickListener listener;

    public ItemAdapter(List<Station> stationList, OnItemClickListener listener)
    {
        this.stationList = stationList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //holder.stationName.setText(stationList.get(position).getStationTitle() + ", " + stationList.get(position).getCityTitle() + ", " + stationList.get(position).getCountryTitle());
        holder.bind(stationList.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return stationList.size();
    }

    public void clear() {
        stationList.clear();
        notifyDataSetChanged();
    }
    public void refresh(List<Station> stationList)
    {
        this.stationList = stationList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView stationName;
        public CardView cardView;
        public MyViewHolder(View view) {
            super(view);
            cardView = (CardView)view.findViewById(R.id.card_view);
            stationName = (TextView) view.findViewById(R.id.stationName);
        }
        public void bind(final Station item, final OnItemClickListener listener) {
            //stationName.setText(stationList.get(position).getStationTitle() + ", " + stationList.get(position).getCityTitle() + ", " + stationList.get(position).getCountryTitle());
            stationName.setText(item.getStationTitle() + ", " + item.getCityTitle() + ", " + item.getCountryTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}