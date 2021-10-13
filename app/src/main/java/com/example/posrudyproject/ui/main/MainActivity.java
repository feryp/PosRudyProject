package com.example.posrudyproject.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.posrudyproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}