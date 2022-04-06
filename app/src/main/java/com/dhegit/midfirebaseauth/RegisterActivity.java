package com.dhegit.midfirebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    EditText etNama, etEmail, etKataSandi, etKonfirmasiKataSandi;
    Button btnDaftar;
    TextView tvMasuk;
    FirebaseAuth mAuth;
    ProgressDialog pgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNama = findViewById(R.id.et_nama);
        etEmail = findViewById(R.id.et_email);
        etKataSandi = findViewById(R.id.et_kata_sandi);
        etKonfirmasiKataSandi = findViewById(R.id.et_konfirmasi_kata_sandi);
        btnDaftar = findViewById(R.id.btn_daftar);
        tvMasuk = findViewById(R.id.tv_masuk);

        tvMasuk.setOnClickListener(view -> {
            finish();
        });

        btnDaftar.setOnClickListener(view -> {
            if (etNama.getText().length()>0 && etEmail.getText().length()>0 && etKataSandi.getText().length()>0) {
                if (etKataSandi.getText().toString().equals(etKonfirmasiKataSandi.getText().toString())) {
                    daftar(etNama.getText().toString(), etEmail.getText().toString(), etKataSandi.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Silakan masukkan kata sandi yang sama", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Silakan isi semua data", Toast.LENGTH_SHORT).show();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        pgDialog = new ProgressDialog(RegisterActivity.this);
        pgDialog.setTitle("Loading");
        pgDialog.setMessage("Silakan tunggu");
        pgDialog.setCancelable(false);
    }

    private void daftar(String nama, String email, String kataSandi) {
        // Memproses Login Pengguna yang Ada
        pgDialog.show();
        mAuth.createUserWithEmailAndPassword(email, kataSandi).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult()!=null) {
                    // Memperbahrui Profil Pengguna
                    FirebaseUser fUser = task.getResult().getUser();
                    if (fUser != null) {
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setDisplayName(nama)
                                .build();
                        fUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                reload();
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Buat akun gagal!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                pgDialog.dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Jika user sudah terdaftar maka diarahkan ke MainActivity.class
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    private void reload() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

}