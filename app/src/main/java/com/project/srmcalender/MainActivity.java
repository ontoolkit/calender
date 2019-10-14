package com.project.srmcalender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button Upcoming;
    private Button Cal;
    private ImageView Logo;
    private TextView Srmt;
    private Button Log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Upcoming = (Button) findViewById(R.id.etupcoming);
        Upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openupcoming();
            }
        });
        Cal = (Button)findViewById(R.id.etcalendar);
        Cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2cal();
            }
        });
        Logo = (ImageView)findViewById(R.id.etimage);
        Srmt = (TextView)findViewById(R.id.etevent);
        Log =(Button)findViewById(R.id.loginb);
        Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openloginpage();
            }
        });


    }
    public void openActivity2cal(){
        Intent intent=new Intent (this, Activity2cal.class);
        intent.putExtra("val", "0");
        startActivity(intent);
    }
    public void openloginpage(){
        Intent intent=new Intent (this, loginpage.class);
        intent.putExtra("val", "0");
        startActivity(intent);
    }
    public void openupcoming() {
        Intent intent=new Intent (this, upcoming.class);
        intent.putExtra("value", "0");
        startActivity(intent);
    }


}
