package com.example.neytro.test10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Neytro on 2015-07-17.
 */
public class HistoryActivityClass extends ArrayAdapter<String> {
    private Context contextList;
    AdapterItem adapterItem;
    private ViewHolder viewHolder = new ViewHolder();
    private LoadingImageClass loader = new LoadingImageClass();

    public HistoryActivityClass(Context context, int resource, AdapterItem a) {
        super(context, resource, a.getCalory());
        contextList = context;
        adapterItem = a;
    }

    //set items in listview
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(contextList);
            convertView = layoutInflater.inflate(R.layout.activity_history_array_list, null);
            holder.textViewDbDate = (TextView) convertView.findViewById(R.id.textViewDbDate);
            holder.textViewDbDistance = (TextView) convertView.findViewById(R.id.textViewDbDistance);
            holder.textViewDbCalory = (TextView) convertView.findViewById(R.id.textViewDbCalory);
            holder.textViewDbSpeed = (TextView) convertView.findViewById(R.id.textViewDbSpeed);
            holder.textViewDbTime = (TextView) convertView.findViewById(R.id.textViewDbTime);
            holder.textViewDbTimePeriod = (TextView) convertView.findViewById(R.id.textViewDbTimePeriod);
            holder.imageViewIcon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
            loader.loadImage(adapterItem.getImage().get(position), holder.imageViewIcon);
            convertView.setTag(holder);
            viewHolder = holder;
        } else {
            holder = (ViewHolder) convertView.getTag();
            viewHolder = holder;
        }
        holder.textViewDbDate.setText(adapterItem.getDate().get(position));
        holder.textViewDbDistance.setText(adapterItem.getDistance().get(position));
        holder.textViewDbCalory.setText(adapterItem.getCalory().get(position));
        holder.textViewDbSpeed.setText(adapterItem.getSpeed().get(position));
        holder.textViewDbTime.setText(adapterItem.getTime().get(position));
        holder.textViewDbTimePeriod.setText(adapterItem.getTimePeriod().get(position));
        return convertView;
    }

    //keep reference for items
    static class ViewHolder {
        TextView textViewDbDate;
        TextView textViewDbTime;
        TextView textViewDbTimePeriod;
        TextView textViewDbDistance;
        TextView textViewDbCalory;
        TextView textViewDbSpeed;
        ImageView imageViewIcon;
    }
}
