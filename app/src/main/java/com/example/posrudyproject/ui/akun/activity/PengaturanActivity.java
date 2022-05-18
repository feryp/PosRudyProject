package com.example.posrudyproject.ui.akun.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penjualan.activity.PenjualanActivity;
import com.example.posrudyproject.ui.penjualan.activity.TransaksiSuksesActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class PengaturanActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    MaterialToolbar mToolbar;
    SwitchCompat pushNotif, emailNotif;
    AppCompatTextView printerSetting;
    SharedPreferences sharedPreferences;
    String PACKAGE_NAME = "com.example.posrudyproject";
    private BluetoothConnection selectedDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        //INIT VIEW
        initComponent();

        initToolbar();

        //SET LISTENER
        printerSetting.setOnClickListener(this);

        if (pushNotif != null){
            pushNotif.setOnCheckedChangeListener(this);
        }

        if (emailNotif != null){
            emailNotif.setOnCheckedChangeListener(this);
        }
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_pengaturan);
        pushNotif = findViewById(R.id.toogle_switch_push_notif);
        emailNotif = findViewById(R.id.toogle_switch_email);
        printerSetting = findViewById(R.id.tv_setting_printer);
    }

    @Override
    public void onClick(View view) {
        browseBluetoothDevice();
    }

    public void browseBluetoothDevice() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        final BluetoothConnection[] bluetoothDevicesList = (new BluetoothPrintersConnections()).getList();
        System.out.println(bluetoothDevicesList);
        if (bluetoothDevicesList != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            final String[] items = new String[bluetoothDevicesList.length + 1];
            items[0] = "Default printer";
            int i = 0;
            for (BluetoothConnection device : bluetoothDevicesList) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                items[++i] = device.getDevice().getName();
            }

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(PengaturanActivity.this);
            alertDialog.setTitle("Bluetooth printer selection");
            alertDialog.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int index = i - 1;
                    if (index == -1) {
                        selectedDevice = null;
                    } else {
                        selectedDevice = bluetoothDevicesList[index];
                    }
                    printerSetting.setText(items[i]);
                }
            });

            AlertDialog alert = alertDialog.create();
            alert.setCanceledOnTouchOutside(false);
            alert.show();

        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()){
            case R.id.toogle_switch_push_notif:
                Toast.makeText(this, "Notification Switch " + (isChecked ? "On" : "Off"),Toast.LENGTH_SHORT).show();
                if (isChecked) {
                    Toast.makeText(this, "Notifikasi Nyala", Toast.LENGTH_SHORT).show();
                    pushNotif.setChecked(true);
                    saveNotifSetting(true);
                } else {
                    Toast.makeText(this, "Notifikasi Mati", Toast.LENGTH_SHORT).show();
                    pushNotif.setChecked(false);
                    saveNotifSetting(false);
                }
                break;
            case R.id.toogle_switch_email:
                Toast.makeText(this, "Notification Switch " + (isChecked ? "On" : "Off"),Toast.LENGTH_SHORT).show();
                if (isChecked) {
                    Toast.makeText(this, "Notifikasi Nyala", Toast.LENGTH_SHORT).show();
                    emailNotif.setChecked(true);
                    saveNotifSetting(true);
                } else {
                    Toast.makeText(this, "Notifikasi Mati", Toast.LENGTH_SHORT).show();
                    emailNotif.setChecked(false);
                    saveNotifSetting(false);
                }
                break;
        }
    }

    private void saveNotifSetting(boolean notification) {
        sharedPreferences = getApplicationContext().getSharedPreferences(PACKAGE_NAME + "SETTING_NOTIF", Context.MODE_PRIVATE);
        sharedPreferences.getBoolean("notif", false);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("notif", notification);
        editor.apply();
    }
}