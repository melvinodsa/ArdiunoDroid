package com.ardiunodroid.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ardiunodroid.R;
import com.ardiunodroid.view.FontTextView;

import java.util.List;

/**
 * Created by hacker on 12/3/16.
 */
public class DeviceAdapter extends BaseAdapter {


    private final int layout;
    private final Context context;
    private List<BluetoothDevice> items;

    public DeviceAdapter(Context context, int layout, List<BluetoothDevice> items) {
        this.layout = layout;
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).describeContents();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater vi = LayoutInflater.from(context);
            view = vi.inflate(layout, null);
        }

        BluetoothDevice device = (BluetoothDevice) getItem(position);

        FontTextView name = (FontTextView) view.findViewById(R.id.device_name);
        FontTextView mac  = (FontTextView) view.findViewById(R.id.device_mac);
        name.setText(device.getName());
        mac.setText(device.getAddress());
        view.setTag(device);
        return view;

    }
}
