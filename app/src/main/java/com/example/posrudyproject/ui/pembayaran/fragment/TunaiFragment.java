package com.example.posrudyproject.ui.pembayaran.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.posrudyproject.R;
import com.google.android.material.button.MaterialButton;

public class TunaiFragment extends Fragment implements View.OnClickListener {

    AppCompatEditText etUangDiterima;
    MaterialButton btnUangPas, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btn00, btnDel, btnClear, btnLanjut;

    String curr;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tunai, container, false);

        curr = "";

        //INIT VIEW
        etUangDiterima = v.findViewById(R.id.et_uang_diterima);
        btnUangPas = v.findViewById(R.id.btn_uang_pas);
        btn1 = v.findViewById(R.id.btn_1);
        btn2 = v.findViewById(R.id.btn_2);
        btn3 = v.findViewById(R.id.btn_3);
        btn4 = v.findViewById(R.id.btn_4);
        btn5 = v.findViewById(R.id.btn_5);
        btn6 = v.findViewById(R.id.btn_6);
        btn7 = v.findViewById(R.id.btn_7);
        btn8 = v.findViewById(R.id.btn_8);
        btn9 = v.findViewById(R.id.btn_9);
        btn0 = v.findViewById(R.id.btn_0);
        btn00 = v.findViewById(R.id.btn_00);

        btnClear = v.findViewById(R.id.btn_clear);
        btnDel = v.findViewById(R.id.btn_delete);
        btnLanjut = v.findViewById(R.id.btn_lanjut);

        //SET LISTENER
        btnUangPas.setOnClickListener(TunaiFragment.this);
        btn1.setOnClickListener(TunaiFragment.this);
        btn2.setOnClickListener(TunaiFragment.this);
        btn3.setOnClickListener(TunaiFragment.this);
        btn4.setOnClickListener(TunaiFragment.this);
        btn5.setOnClickListener(TunaiFragment.this);
        btn6.setOnClickListener(TunaiFragment.this);
        btn7.setOnClickListener(TunaiFragment.this);
        btn8.setOnClickListener(TunaiFragment.this);
        btn9.setOnClickListener(TunaiFragment.this);
        btn0.setOnClickListener(TunaiFragment.this);
        btn00.setOnClickListener(TunaiFragment.this);
        btnClear.setOnClickListener(TunaiFragment.this);
        btnDel.setOnClickListener(TunaiFragment.this);
        btnLanjut.setOnClickListener(TunaiFragment.this);



        return v;
    }

    @SuppressLint("SetTextI18n")
    public void display(){
        etUangDiterima.setText("Rp " + curr);
    }
    public void clear(){
        curr = "";
    }
    public void delete(){
        if (!curr.isEmpty()){
            if (curr.charAt(curr.length() - 1) == ' '){
                curr = curr.substring(0, curr.length()-3);
            }
            else {
                curr = curr.substring(0, curr.length()-1);
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_uang_pas:
                //Function uang pas
                break;
            case R.id.btn_0:
                curr = curr + "0";
                display();
                break;
            case R.id.btn_1:
                curr = curr + "1";
                display();
                break;
            case R.id.btn_2:
                curr = curr + "2";
                display();
                break;
            case R.id.btn_3:
                curr = curr + "3";
                display();
                break;
            case R.id.btn_4:
                curr = curr + "4";
                display();
                break;
            case R.id.btn_5:
                curr = curr + "5";
                display();
                break;
            case R.id.btn_6:
                curr = curr + "6";
                display();
                break;
            case R.id.btn_7:
                curr = curr + "7";
                display();
                break;
            case R.id.btn_8:
                curr = curr + "8";
                display();
                break;
            case R.id.btn_9:
                curr = curr + "9";
                display();
                break;
            case R.id.btn_00:
                curr = curr + "00";
                display();
                break;
            case R.id.btn_clear:
                clear();
                display();
                break;
            case R.id.btn_delete:
                delete();
                display();
                break;
            case R.id.btn_lanjut:
                //Function konfimasi pembayaran
                break;

        }
    }
}