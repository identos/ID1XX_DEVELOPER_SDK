package com.identos.android.id100.demo;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.identos.android.id100.demo.util.Utils;
import com.identos.android.id100.library.ccid.UsbReader;

import java.util.ArrayList;

public class UsbReaderAdapter extends ArrayAdapter<UsbReader> {
    public UsbReaderAdapter(Context context, ArrayList<UsbReader> readers) {
        super(context, 0, readers);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_data_entry, parent, false);

            holder = new ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.text_title);
            holder.valueTextView = (TextView) convertView.findViewById(R.id.text_value);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        UsbDevice device = getItem(position).getDevice();

        holder.titleTextView.setText(Utils.getUsbDeviceName(device));

        String value = "Vendor ID: " + device.getVendorId() + "\nProduct ID: " + device.getProductId();
        holder.valueTextView.setText(value);

        return convertView;
    }

    private class ViewHolder {
        public TextView titleTextView;
        public TextView valueTextView;
    }
}