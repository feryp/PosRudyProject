package com.example.posrudyproject.ui.printer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.posrudyproject.R;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ListAdapter extends BaseAdapter {

    // Adapter for holding devices found through scanning.
    public static final int PERMISSION_BLUETOOTH = 1;
    public static final int PERMISSION_BLUETOOTH_ADMIN = 1;
    public static final int PERMISSION_BLUETOOTH_CONNECT = 1;
    public static final int PERMISSION_BLUETOOTH_SCAN = 1;
    private LinkedList<BluetoothDevice> mLeDevices;
    private LayoutInflater mLayoutInflater;
    private Activity mContext;

    private Map<String, Integer> device_rssi = new HashMap<String, Integer>();

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            device_rssi.put(device.getAddress(), rssi);
        }
    };

    @SuppressLint("NewApi")
    public ListAdapter(Activity c) {
        super();
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        adapter.startLeScan(mLeScanCallback);
        mContext = c;
        mLeDevices = new LinkedList<BluetoothDevice>();
        mLayoutInflater = mContext.getLayoutInflater();
    }

    public void addDevice(BluetoothDevice device) {
        if (!mLeDevices.contains(device)) {// 注意:需判断重复
            mLeDevices.add(device);
            notifyDataSetChanged();
        }
    }

    public LinkedList<BluetoothDevice> getAllDeivces() {
        return mLeDevices;
    }

    public BluetoothDevice getDevice(int position) {
        return mLeDevices.get(position);
    }

    public void clear() {
        mLeDevices.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mLeDevices.size();
    }

    @Override
    public Object getItem(int i) {
        return mLeDevices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.BLUETOOTH}, PrinterActivity.PERMISSION_BLUETOOTH_SCAN);
        } else {
        // General ListView optimization code.
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.listitem_device, null);
            viewHolder = new ViewHolder();
            viewHolder.deviceAddress = (TextView) view
                    .findViewById(R.id.device_address);
            viewHolder.deviceName = (TextView) view
                    .findViewById(R.id.device_name);
            viewHolder.device_rssi = (TextView) view.findViewById(R.id.device_rssi);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        BluetoothDevice device = mLeDevices.get(i);


            final String deviceName = device.getName();
            if (deviceName != null && deviceName.length() > 0)
                viewHolder.deviceName.setText(deviceName);
            else
                viewHolder.deviceName.setText("unknown_device");
            viewHolder.deviceAddress.setText(device.getAddress());
            Integer rssi = device_rssi.get(device.getAddress());
            viewHolder.device_rssi.setText("RSSI:" + rssi);
        }
        return view;
    }

    class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
        TextView device_rssi;
    }


}
