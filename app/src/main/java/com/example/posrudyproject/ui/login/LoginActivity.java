package com.example.posrudyproject.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.lupaSandi.LupaSandiActivity;
import com.example.posrudyproject.ui.main.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText etEmail, etPassword;
    MaterialButton btnLogin, btnLupaPassword;
    TextInputLayout etLayout;

    String USER = "admin";
    String PASSWORD = "admin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //INIT VIEW
        initComponent();

        //SET LISTENER
        btnLogin.setOnClickListener(this);
        btnLupaPassword.setOnClickListener(this);


    }

    public void initComponent(){
        // init
        etEmail = findViewById(R.id.et_nama_pengguna_login);
        etPassword = findViewById(R.id.et_kata_sandi_login);
        etLayout = findViewById(R.id.layout_et_nama_pengguna);
        btnLogin = findViewById(R.id.btn_masuk);
        btnLupaPassword = findViewById(R.id.btn_lupa_sandi);

    }

    public void validation(){
        if(Objects.requireNonNull(etEmail.getText()).toString().equals(USER) &&
                Objects.requireNonNull(etPassword.getText()).toString().equals(PASSWORD)){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_masuk:
//                validation();
                Intent login = new Intent(this, MainActivity.class);
                startActivity(login);
                break;

            case R.id.btn_lupa_sandi:
                Intent lupaSandi = new Intent(this, LupaSandiActivity.class);
                startActivity(lupaSandi);
                break;
        }
    }
}