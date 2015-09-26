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

    public HistoryActivityClass(Context _context, int _resource, AdapterItem _adapter) {
        super(_context, _resource, _adapter.getCalory());
        contextList = _context;
        adapterItem = _adapter;
    }

    //set items in listview
    @Override
    public View getView(int _position, View _convertView, ViewGroup _parent) {
        ViewHolder holder;
        if (_convertView == null) {
            holder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(contextList);
            _convertView = layoutInflater.inflate(R.layout.activity_history_array_list, null);
            holder.textViewDbDate = (TextView) _convertView.findViewById(R.id.textViewDbDate);
            holder.textViewDbDistance = (TextView) _convertView.findViewById(R.id.textViewDbDistance);
            holder.textViewDbCalory = (TextView) _convertView.findViewById(R.id.textViewDbCalory);
            holder.textViewDbSpeed = (TextView) _convertView.findViewById(R.id.textViewDbSpeed);
            holder.textViewDbTime = (TextView) _convertView.findViewById(R.id.textViewDbTime);
            holder.textViewDbTimePeriod = (TextView) _convertView.findViewById(R.id.textViewDbTimePeriod);
            holder.imageViewIcon = (ImageView) _convertView.findViewById(R.id.imageViewIcon);
            _convertView.setTag(holder);
            viewHolder = holder;
        } else {
            holder = (ViewHolder) _convertView.getTag();
            viewHolder = holder;
        }
        loader.loadImage(adapterItem.getImage().get(_position), holder.imageViewIcon);
        holder.textViewDbDate.setText(adapterItem.getDate().get(_position));
        holder.textViewDbDistance.setText(adapterItem.getDistance().get(_position));
        holder.textViewDbCalory.setText(adapterItem.getCalory().get(_position));
        holder.textViewDbSpeed.setText(adapterItem.getSpeed().get(_position));
        holder.textViewDbTime.setText(adapterItem.getTime().get(_position));
        holder.textViewDbTimePeriod.setText(adapterItem.getTimePeriod().get(_position));
        return _convertView;
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
