package com.example.posrudyproject.ui.pembayaran.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.pembayaran.adapter.PembayaranKartuAdapter;
import com.example.posrudyproject.ui.pembayaran.adapter.PembayaranTransferAdapter;
import com.example.posrudyproject.ui.pembayaran.model.BankItem;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class NonTunaiFragment extends Fragment {

    private RecyclerView rvDebit, rvKredit, rvTransfer;
    private MaterialButton btnLanjut;
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
        for (int i=0; i<6; i++){
            bankItems.add(new BankItem(
                    R.drawable.im_bank_mandiri,
                    "Bank Mandiri",
                    "0700 000 899 992"));
        }

        //Setup adapter Debit
        cardAdapter = new PembayaranKartuAdapter(bankItems, getActivity());
        rvDebit.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvDebit.setAdapter(cardAdapter);
        rvDebit.setHasFixedSize(true);

        //Setup adapter Kartu Kredit
        cardAdapter = new PembayaranKartuAdapter(bankItems, getActivity());
        rvKredit.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvKredit.setAdapter(cardAdapter);
        rvKredit.setHasFixedSize(true);

        //Setup adapter Transfer Bank
        transferAdapter = new PembayaranTransferAdapter(bankItems, getActivity());
        rvTransfer.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTransfer.setAdapter(transferAdapter);
        rvTransfer.setHasFixedSize(true);

        return v;
    }
}