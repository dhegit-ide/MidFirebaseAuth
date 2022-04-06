package com.dhegit.midfirebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etKataSandi;
    private Button btnMasuk, btnBuatAkun;
    private String strEmail, strKataSandi;
    FirebaseAuth mAuth;
    ProgressDialog pgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email);
        etKataSandi = findViewById(R.id.et_kata_sandi);
        btnMasuk = findViewById(R.id.btn_masuk);
        btnBuatAkun = findViewById(R.id.btn_buat_akun);

        strEmail = etEmail.getText().toString();
        strKataSandi = etKataSandi.getText().toString();

        btnBuatAkun.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        });

        btnMasuk.setOnClickListener(view -> {
            if (etEmail.getText().length() > 0 && etKataSandi.getText().length() > 0) {
                masuk(etEmail.getText().toString(), etKataSandi.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), "Silakan isi semua data", Toast.LENGTH_SHORT).show();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        pgDialog = new ProgressDialog(LoginActivity.this);
        pgDialog.setTitle("Loading");
        pgDialog.setMessage("Silakan tunggu");
        pgDialog.setCancelable(false);
    }

    private void masuk(String email, String kataSandi) {
        // CODING LOGIN
        pgDialog.show();
        mAuth.signInWithEmailAndPassword(email, kataSandi).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult()!=null) {
                    if (task.getResult().getUser() != null) {
                        reload();
                    } else {
                        Toast.makeText(getApplicationContext(), "Login gagal!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Login gagal!", Toast.LENGTH_SHORT).show();
                }
                pgDialog.dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    private void reload() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}