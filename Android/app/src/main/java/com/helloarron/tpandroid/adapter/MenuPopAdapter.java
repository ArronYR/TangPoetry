package com.helloarron.tpandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.helloarron.tpandroid.R;

/**
 * Created by arron on 2017/3/19.
 */

public class MenuPopAdapter extends BaseAdapter {

    private String[] list;
    private LayoutInflater inflater;

    public MenuPopAdapter(Context context, String[] list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return list[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_menu_pop, null);
            holder = new Holder();
            holder.tvItem = (TextView) convertView.findViewById(R.id.tv_menu_item);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tvItem.setText(list[position].toString());
        return convertView;
    }

    class Holder {
        TextView tvItem;
    }
}
