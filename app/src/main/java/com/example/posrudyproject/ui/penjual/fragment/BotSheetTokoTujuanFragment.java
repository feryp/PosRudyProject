package com.example.posrudyproject.ui.penjual.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penjual.adapter.TokoTujuanAdapter;
import com.example.posrudyproject.ui.penjual.model.PenjualItem;
import com.example.posrudyproject.ui.penjual.model.TokoItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class BotSheetTokoTujuanFragment extends BottomSheetDialogFragment implements OnItemClickListener {

    RecyclerView rvTokoTujuan;
    ImageButton btnClose;
    TokoTujuanAdapter adapter;
    List<TokoItem> tokoItems;
    List<PenjualItem> penjualItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bot_sheet_toko_tujuan, container, false);

        //INIT VIEW
        rvTokoTujuan = v.findViewById(R.id.rv_toko_botsheet);
        btnClose = v.findViewById(R.id.btn_close_botsheet);

        //Toko List
        tokoItems = new ArrayList<>();
        for (int i=0; i<50; i++){
            tokoItems.add(new TokoItem("Toko Pusat"));
        }

        //Setup Adapter
        adapter = new TokoTujuanAdapter(tokoItems, this);
        rvTokoTujuan.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTokoTujuan.setAdapter(adapter);
        rvTokoTujuan.setHasFixedSize(true);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Toast.makeText(getContext(), "Pilih " + tokoItems.get(position).getNamaToko(), Toast.LENGTH_SHORT).show();
        //SELECT ITEM FILTER
        openDialog();
        dismiss();
    }

    @SuppressLint({"SetTextI18n","UseCompatLoadingForDrawables"})
    private void openDialog() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
//        View mView = getLayoutInflater().inflate(R.layout.dialog_alert_message, null);
        View mView = View.inflate(getActivity(), R.layout.dialog_alert_message,null);

        //init view
        AppCompatTextView titleDialog = (AppCompatTextView) mView.findViewById(R.id.txt_title_dialog);
        AppCompatTextView pesanDialog = (AppCompatTextView) mView.findViewById(R.id.txt_pesan_dialog);
        MaterialButton btnOke = (MaterialButton) mView.findViewById(R.id.btn_oke_dialog);
        MaterialButton btnCancel = (MaterialButton) mView.findViewById(R.id.btn_cancel_dialog);

        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_rounded_white);
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        titleDialog.setText("Pindah Penjual");
        pesanDialog.setText("Pindahkan Alex Parkinson ke Toko Pusat");

        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
}