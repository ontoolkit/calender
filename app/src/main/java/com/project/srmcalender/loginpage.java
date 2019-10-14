package com.project.srmcalender;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginpage extends AppCompatActivity {

    private EditText Rgno;
    private EditText Password;
    private Button Logi, btnaddadmin;
    private TextView Info;
    private int counter = 5;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        Rgno = (EditText) findViewById(R.id.etrgno);
        Password = (EditText) findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.tvInfo);
        Logi = (Button) findViewById(R.id.btnLogin);
        btnaddadmin = (Button) findViewById(R.id.btnaddadmin);
        mAuth = FirebaseAuth.getInstance();

        Logi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Rgno.getText().toString();
                String password = Password.getText().toString();
                signin(email, password);

            }
        });

        btnaddadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Rgno.getText().toString();
                String password = Password.getText().toString();
                signup(email, password);
            }
        });

    }

    private void signin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String id = user.getUid();
                            startActivity(new Intent(loginpage.this, AdminPage.class));
                            finish();
//                            Toast.makeText(loginpage.this, ""+id, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(loginpage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signup(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Welcome User", Toast.LENGTH_SHORT).show();
                            reset();
                        } else {
                            Toast.makeText(loginpage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void reset()
    {
        Rgno.setText("");
        Password.setText("");
    }
}
