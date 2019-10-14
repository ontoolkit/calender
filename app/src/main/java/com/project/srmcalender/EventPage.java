package com.project.srmcalender;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.srmcalender.Adaptor.ProductList;
import com.project.srmcalender.Pojo.Event;

import java.util.ArrayList;

public class EventPage extends AppCompatActivity {

    private TextView thedate;
    private EditText edit_bottom;
    private Button btn_submit;
    String date;
    DatabaseReference databaseReference;
    private String name = "check";
    FirebaseAuth mAuth;
    ArrayList<Event> events;
    ListView product;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);
        mAuth = FirebaseAuth.getInstance();
        edit_bottom = (EditText) findViewById(R.id.edit_bottom);
        thedate = (TextView) findViewById(R.id.thedate);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        Intent intent = getIntent();
        date = intent.getExtras().getString("thedate");
        String val = intent.getStringExtra("val");
        events = new ArrayList<>();
        product = (ListView) findViewById(R.id.product);
        if (val.equals("1")) {
            edit_bottom.setEnabled(true);
            btn_submit.setVisibility(View.VISIBLE);


            product.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    final Event event = events.get(position);
                    final String g_id = event.getId();

                    AlertDialog.Builder builder = new AlertDialog.Builder(EventPage.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view1 = inflater.inflate(R.layout.dialogview, null);
                    Button edit = (Button) view1.findViewById(R.id.edit);
                    Button delete = (Button) view1.findViewById(R.id.delete);
                    builder.setView(view1);

                    dialog = builder.create();
                    dialog.show();

                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String date = event.getDate();
                            String e = event.getEventdesc();
                            String id = event.getId();
                            createalertdialog(id, e, date);
                            Toast.makeText(EventPage.this, "Edit Option", Toast.LENGTH_SHORT).show();
                        }
                    });

                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            removed(g_id);
                            Toast.makeText(EventPage.this, "Delete Option", Toast.LENGTH_SHORT).show();
                        }
                    });



                    return false;
                }
            });

        } else {
            edit_bottom.setVisibility(View.GONE);
            btn_submit.setVisibility(View.GONE);
        }



        thedate.setText(date);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edit_bottom.getText().toString().trim();
                databaseReference = FirebaseDatabase.getInstance().getReference("events");
                String key = databaseReference.push().getKey();
                Event event = new Event(date, content, key);
                databaseReference.child(key).setValue(event);
                Toast.makeText(EventPage.this, "Event was Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void createalertdialog(final String id, String e, final String date) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(EventPage.this);
        LayoutInflater inflater = getLayoutInflater();
        View view2 = inflater.inflate(R.layout.edioption, null);
        final EditText edit_code = (EditText) view2.findViewById(R.id.edit_code);
        Button finish_update = (Button) view2.findViewById(R.id.finish_update);
        builder1.setView(view2);

        edit_code.setText(e);
        final AlertDialog dialog1 = builder1.create();
        dialog1.show();
        finish_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m = edit_code.getText().toString().trim();
                databaseReference = FirebaseDatabase.getInstance().getReference("events");
                Event event = new Event(date, m, id);
                databaseReference.child(id).setValue(event);
                Toast.makeText(EventPage.this, "Event Updated Successfully", Toast.LENGTH_SHORT).show();
                dialog1.dismiss();
                dialog.dismiss();
            }
        });



    }

    private void removed(String g_id) {
        databaseReference = FirebaseDatabase.getInstance().getReference("events");
        databaseReference.child(g_id).removeValue();
        dialog.dismiss();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference = FirebaseDatabase.getInstance().getReference("events");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                events.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Event eve = dataSnapshot1.getValue(Event.class);
                        if (eve.getDate().equals(date)) {
                            events.add(eve);
                        }
                    }
                } else {
                    Toast.makeText(EventPage.this, "Data Not Found", Toast.LENGTH_SHORT).show();
                }

                ProductList productList = new ProductList(EventPage.this, events);
                product.setAdapter(productList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(EventPage.this, "Please Check Your internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
