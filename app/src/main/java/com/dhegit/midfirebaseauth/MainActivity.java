package com.dhegit.midfirebaseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private TextView tvNama;
    private FirebaseUser fUser;
    private FloatingActionButton btnKeluar, btnTanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNama = findViewById(R.id.tv_nama);
        btnKeluar = findViewById(R.id.btn_keluar);
        btnTanggal = findViewById(R.id.btn_tanggal);
        fUser = FirebaseAuth.getInstance().getCurrentUser();

        if (fUser != null) {
            tvNama.setText(fUser.getDisplayName());
        } else {
            tvNama.setError("User tidak ditemukan");
        }

        btnKeluar.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });

        btnTanggal.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), DateActivity.class));
        });

    }
}