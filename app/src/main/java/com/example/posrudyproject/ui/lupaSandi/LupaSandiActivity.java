package com.example.posrudyproject.ui.lupaSandi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.google.android.material.button.MaterialButton;

public class LupaSandiActivity extends AppCompatActivity implements View.OnClickListener{

    MaterialButton btnKirim;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_sandi);

        initComponent();

        openDialog();


        btnKirim.setOnClickListener(this);

    }

    @SuppressLint({"ObsoleteSdkInt", "UseCompatLoadingForDrawables"})
    private void openDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_konfirmasi);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_dialog));
        }

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        MaterialButton btnOke = dialog.findViewById(R.id.btn_oke);
        MaterialButton btnCancel = dialog.findViewById(R.id.btn_cancel_dialog);

        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LupaSandiActivity.this, "Oke", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void initComponent() {
        // init
        btnKirim = findViewById(R.id.btn_kirim);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_kirim:
                dialog.show();
                break;
        }
    }
}