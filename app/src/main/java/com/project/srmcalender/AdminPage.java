package com.project.srmcalender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminPage extends AppCompatActivity {

    Button logout, etupcoming, etcalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        logout = (Button) findViewById(R.id.logout);
        etupcoming = (Button) findViewById(R.id.etupcoming);
        etcalendar = (Button) findViewById(R.id.etcalendar);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goout();
            }
        });

        etcalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPage.this, Activity2cal.class);
                intent.putExtra("val","1");
                startActivity(intent);
            }
        });

        etupcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPage.this, upcoming.class);
                intent.putExtra("value","2");
                startActivity(intent);
            }
        });

    }

    private void goout() {
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goout();
        Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
    }
}
