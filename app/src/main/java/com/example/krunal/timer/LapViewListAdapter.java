package com.example.krunal.timer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by krunal on 1/3/2018.
 */

public class LapViewListAdapter extends ArrayAdapter<Lap>{
    Context context;
    int layoutResourceId;
    ArrayList<Lap> data = new ArrayList<Lap>();

    public LapViewListAdapter( Context context, int layoutResourceId, ArrayList<Lap> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LapHolder holder = null;

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lap_time_list_view, parent, false);
            holder = new LapHolder();
            holder.lapNumber = (TextView) view.findViewById(R.id.lap_number);
            holder.lapTime = (TextView) view.findViewById(R.id.lap_time);

        Lap lap = data.get(position);
        holder.lapTime.setText(lap.getLapTime());
        holder.lapNumber.setText(lap.getNumberOfLap());
        return  view;
    }

    static class LapHolder {
        TextView lapNumber;
        TextView lapTime;
    }
}

