package com.example.posrudyproject.ui.penyimpanan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.databinding.ActivityMainBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.Arrays;

public class PemindahanBarangActivity extends AppCompatActivity implements View.OnClickListener{

    MaterialToolbar mToolbar;
    MaterialButton btnSelanjutnya, btnSebelumnya;
    StepView stepView;

    int stepIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemindahan_barang);

        //INIT VIEW
        initComponent();

        initToolbar();

        //Set Step View
        stepView.getState()
                .animationType(StepView.ANIMATION_ALL)
                .stepsNumber(3)
                .steps(new ArrayList<String>(){{
                    add("Pilih Barang");
                    add("Data Toko");
                    add("Detail Dokumen");
                }})
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .typeface(ResourcesCompat.getFont(this, R.font.work_sans_medium))
                .commit();


        btnSelanjutnya.setOnClickListener(this);
        btnSebelumnya.setOnClickListener(this);

    }



    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_pemindahan_barang);
        stepView = findViewById(R.id.step_view);
        btnSelanjutnya = findViewById(R.id.btn_selanjutnya);
        btnSebelumnya = findViewById(R.id.btn_sebelumnya);

    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View view) {
        if (view == btnSelanjutnya){
            stepIndex++;
            stepView.go(stepIndex, true);
        }
        else if (view == btnSebelumnya) {
           //FUNCTION BACK
        }
    }
}