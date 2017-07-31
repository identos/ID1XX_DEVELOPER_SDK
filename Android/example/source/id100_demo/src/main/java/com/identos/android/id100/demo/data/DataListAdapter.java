package com.identos.android.id100.demo.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.identos.android.id100.demo.R;

import java.util.ArrayList;

class DataListAdapter extends ArrayAdapter<DataItem> {
    DataListAdapter(Context context, ArrayList<DataItem> list) {
        super(context, R.layout.list_item_data_entry, list);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        DataItem item = getItem(position);

        if(item != null) {
            if (item.isSection()) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_data_section, parent, false);
            } else {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_data_entry, parent, false);

                TextView valueTextView = (TextView) convertView.findViewById(R.id.text_value);
                valueTextView.setText(((DataItemEntry) item).getValue());
            }

            TextView titleTextView = (TextView) convertView.findViewById(R.id.text_title);
            titleTextView.setText(item.getTitle());
        }

        return convertView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        DataItem item = getItem(position);
        return item != null && !item.isSection();
    }
}