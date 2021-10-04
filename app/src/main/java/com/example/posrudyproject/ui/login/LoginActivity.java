package com.example.posrudyproject.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.main.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText etEmail, etPassword;
    MaterialButton btnLogin;
    TextInputLayout etLayout;

    String USER = "admin";
    String PASSWORD = "admin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponent();

        btnLogin.setOnClickListener(view -> {
            validation();
        });

    }

    public void initComponent(){
        // init
        etEmail = findViewById(R.id.et_nama_pengguna_login);
        etPassword = findViewById(R.id.et_kata_sandi_login);
        etLayout = findViewById(R.id.layout_et_nama_pengguna);
        btnLogin = findViewById(R.id.btn_masuk);

    }

    public void validation(){
        if(Objects.requireNonNull(etEmail.getText()).toString().equals(USER) &&
                Objects.requireNonNull(etPassword.getText()).toString().equals(PASSWORD)){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }

}