package com.example.posrudyproject.ui.akun.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.google.android.material.appbar.MaterialToolbar;

public class PengaturanActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    MaterialToolbar mToolbar;
    SwitchCompat pushNotif, emailNotif;
    AppCompatTextView printerSetting;
    SharedPreferences sharedPreferences;
    String PACKAGE_NAME = "com.example.posrudyproject";

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