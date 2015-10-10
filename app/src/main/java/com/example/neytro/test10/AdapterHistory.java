package com.example.neytro.test10;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Created by Neytro on 2015-07-17.
 */
public class AdapterHistory extends ArrayAdapter<String> implements View.OnClickListener {
    private Context contextList;
    AdapterItem adapterItem;
    private ViewHolder viewHolder = new ViewHolder();
    private ClassLoadingImage loader = new ClassLoadingImage();

    public AdapterHistory(Context context, int resource, AdapterItem adapter) {
        super(context, resource, adapter.getCalory());
        contextList = context;
        adapterItem = adapter;
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
            holder.imageViewIcon.setOnClickListener(this);
            convertView.setTag(holder);
            viewHolder = holder;
        } else {
            holder = (ViewHolder) convertView.getTag();
            viewHolder = holder;
        }
        loader.loadImage(adapterItem.getImage().get(position), holder.imageViewIcon);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewIcon:
                Intent intent = new Intent(getContext(), ActivityZoomImage.class);
                getContext().startActivity(intent);
        }
    }
}
