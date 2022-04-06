package com.dhegit.midfirebaseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;

public class DateActivity extends AppCompatActivity {
    private TextView tvTanggal;
    private Button btnKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        tvTanggal = findViewById(R.id.tv_tanggal);
        btnKembali = findViewById(R.id.btn_kembali);

        Calendar kalender = Calendar.getInstance();
        String tanggalSekarang = DateFormat.getDateInstance(android.icu.text.DateFormat.FULL).format(kalender.getTime());
        tvTanggal.setText(tanggalSekarang);

        btnKembali.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });
    }
}