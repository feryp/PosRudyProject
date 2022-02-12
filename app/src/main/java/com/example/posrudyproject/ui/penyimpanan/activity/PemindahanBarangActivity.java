package com.example.posrudyproject.ui.penyimpanan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penyimpanan.fragment.DataTokoFragment;
import com.example.posrudyproject.ui.penyimpanan.fragment.DetailPemindahanFragment;
import com.example.posrudyproject.ui.penyimpanan.fragment.PilihPemindahanBarangFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;

public class PemindahanBarangActivity extends AppCompatActivity implements View.OnClickListener{

    MaterialToolbar mToolbar;
    View layoutBtnStep1, layoutBtnStep2, layoutBtnStep3;
    MaterialButton btnLanjutStep1, btnLanjutStep2, btnKembaliStep2, btnKembaliStep3, btnSelesai;
    StepView stepView;

    FragmentManager fragmentManager = null;
    int stepIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemindahan_barang);
        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_pemindahan_barang, PilihPemindahanBarangFragment.class, null)
                    .commit();
        }

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

        //SET LISTENER
        btnLanjutStep1.setOnClickListener(this);
        btnLanjutStep2.setOnClickListener(this);
        btnKembaliStep2.setOnClickListener(this);
        btnKembaliStep3.setOnClickListener(this);
        btnSelesai.setOnClickListener(this);

    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_pemindahan_barang);
        stepView = findViewById(R.id.step_view_pemindahan_barang);
        btnLanjutStep1 = findViewById(R.id.btn_lanjut_step_1);
        btnLanjutStep2 = findViewById(R.id.btn_lanjut_step_2);
        btnKembaliStep2 = findViewById(R.id.btn_kembali_step_2);
        btnKembaliStep3 = findViewById(R.id.btn_kembali_step_3);
        btnSelesai = findViewById(R.id.btn_selesai);
        layoutBtnStep1 = findViewById(R.id.layout_button1);
        layoutBtnStep2 = findViewById(R.id.layout_button2);
        layoutBtnStep3 = findViewById(R.id.layout_button3);

    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_lanjut_step_1:
                stepIndex++;
                stepView.go(stepIndex, true);
                changeStepFragment(DataTokoFragment.class);
                layoutBtnStep1.setVisibility(View.GONE);
                layoutBtnStep2.setVisibility(View.VISIBLE);

                break;
            case R.id.btn_lanjut_step_2:
                stepIndex++;
                stepView.go(stepIndex, true);
                changeStepFragment(DetailPemindahanFragment.class);
                layoutBtnStep1.setVisibility(View.GONE);
                layoutBtnStep2.setVisibility(View.GONE);
                layoutBtnStep3.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_kembali_step_2:
                stepIndex--;
                stepView.go(stepIndex, true);
                changeStepFragment(PilihPemindahanBarangFragment.class);
                layoutBtnStep1.setVisibility(View.VISIBLE);
                layoutBtnStep2.setVisibility(View.GONE);
                layoutBtnStep3.setVisibility(View.GONE);
                break;
            case R.id.btn_kembali_step_3:
                stepIndex--;
                stepView.go(stepIndex, true);
                changeStepFragment(DataTokoFragment.class);
                layoutBtnStep1.setVisibility(View.GONE);
                layoutBtnStep2.setVisibility(View.VISIBLE);
                layoutBtnStep3.setVisibility(View.GONE);
                break;
            case R.id.btn_selesai:
                openDialog();
                break;
        }
    }

    private void openDialog() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View mView = View.inflate(this, R.layout.dialog_alert_message,null);

        //init view
        AppCompatTextView titleDialog = mView.findViewById(R.id.txt_title_dialog);
        AppCompatTextView pesanDialog = mView.findViewById(R.id.txt_pesan_dialog);
        MaterialButton btnOke = mView.findViewById(R.id.btn_oke_dialog);
        MaterialButton btnCancel = mView.findViewById(R.id.btn_cancel_dialog);

        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_rounded_white);
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        titleDialog.setText(getString(R.string.title_dialog_pemindahan));
        pesanDialog.setText(getString(R.string.pesan_dialog_pemindahan));

        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FUNCTION CUSTOM FINISH
                alertDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    protected void changeStepFragment(@NonNull Class<? extends Fragment> fragmentClass){
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_pemindahan_barang, fragmentClass, null)
                .addToBackStack("name")
                .commit();
    }
}