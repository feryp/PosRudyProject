package com.example.posrudyproject.ui.pembayaran.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.pembayaran.adapter.PembayaranKartuAdapter;
import com.example.posrudyproject.ui.pembayaran.adapter.PembayaranTransferAdapter;
import com.example.posrudyproject.ui.pembayaran.model.BankItem;
import com.example.posrudyproject.ui.penjualan.activity.TransaksiSuksesActivity;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class NonTunaiFragment extends Fragment implements OnItemClickListener {

    private RecyclerView rvDebit, rvKredit, rvTransfer;
    private List<BankItem> bankItems;
    private PembayaranKartuAdapter cardAdapter;
    private PembayaranTransferAdapter transferAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_non_tunai, container, false);

        //INIT VIEW
        rvDebit = v.findViewById(R.id.rv_debit_bank);
        rvKredit = v.findViewById(R.id.rv_kartu_kredit);
        rvTransfer = v .findViewById(R.id.rv_transfer_bank);

        //CARD BANK LIST
        bankItems = new ArrayList<>();
        bankItems.add(new BankItem(R.drawable.im_bank_mandiri,"Bank Mandiri","0700 000 899 992"));
        bankItems.add(new BankItem(R.drawable.im_bank_mandiri,"Bank BCA","0700 000 899 992"));
        bankItems.add(new BankItem(R.drawable.im_bank_mandiri,"Bank BRI","0700 000 899 992"));
        bankItems.add(new BankItem(R.drawable.im_bank_mandiri,"Bank BNI","0700 000 899 992"));
        bankItems.add(new BankItem(R.drawable.im_bank_mandiri,"Bank BTN","0700 000 899 992"));
        bankItems.add(new BankItem(R.drawable.im_bank_mandiri,"Bank Danamon","0700 000 899 992"));
        bankItems.add(new BankItem(R.drawable.im_bank_mandiri,"Bank CIMB","0700 000 899 992"));
        bankItems.add(new BankItem(R.drawable.im_bank_mandiri,"Bank Mega","0700 000 899 992"));
        bankItems.add(new BankItem(R.drawable.im_bank_mandiri,"Bank BJB","0700 000 899 992"));
        bankItems.add(new BankItem(R.drawable.im_bank_mandiri,"Bank DKI","0700 000 899 992"));

        //Setup adapter Debit
        cardAdapter = new PembayaranKartuAdapter(bankItems, this);
        rvDebit.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvDebit.setAdapter(cardAdapter);
        rvDebit.setHasFixedSize(true);

        //Setup adapter Kartu Kredit
        cardAdapter = new PembayaranKartuAdapter(bankItems, this);
        rvKredit.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvKredit.setAdapter(cardAdapter);
        rvKredit.setHasFixedSize(true);

        //Setup adapter Transfer Bank
        transferAdapter = new PembayaranTransferAdapter(bankItems, this);
        rvTransfer.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTransfer.setAdapter(transferAdapter);
        rvTransfer.setHasFixedSize(true);

        return v;
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Intent bayarNonTunai = new Intent(getActivity(), TransaksiSuksesActivity.class);
        startActivity(bayarNonTunai);
    }
}