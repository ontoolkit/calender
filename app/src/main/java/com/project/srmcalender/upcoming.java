package com.project.srmcalender;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.srmcalender.Adaptor.ListAdaptor;
import com.project.srmcalender.Pojo.GetEventDetails;

import java.io.IOException;
import java.util.ArrayList;

public class upcoming extends AppCompatActivity {
    private static final String TAG = "upcoming";
    private TextView up;
    EditText text_add;
    private String name = "check";
    LinearLayout imageupload;
    Uri urimageurl;
    ImageView image;
    String path;
    Button image_upload;
    ListView imaguploadlist;
    private static int CHOOSE_FILE = 101;
    DatabaseReference mDatabaseReference;
    ArrayList<GetEventDetails> getEventDetailss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);
        up = (TextView) findViewById(R.id.upcoming);
        imageupload = (LinearLayout) findViewById(R.id.imageupload);
        image_upload = (Button) findViewById(R.id.image_upload);
        image = (ImageView) findViewById(R.id.image);
        text_add = (EditText) findViewById(R.id.text_add);
        imaguploadlist = (ListView) findViewById(R.id.imaguploadlist);
        getEventDetailss = new ArrayList<>();

        Intent intent = getIntent();
        String val = intent.getStringExtra("value");

        if (val.equals("2")) {
            imageupload.setVisibility(View.VISIBLE);

            imaguploadlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    GetEventDetails getEventDetails = getEventDetailss.get(position);
                    final String selected = getEventDetails.getId();
                    Toast.makeText(upcoming.this, selected, Toast.LENGTH_SHORT).show();


                    AlertDialog.Builder adb = new AlertDialog.Builder(upcoming.this);
                    adb.setTitle("Do you want to remove the details");
                    adb.setIcon(android.R.drawable.ic_dialog_alert);
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            deletefunction(selected);
                        }
                    });
                    adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    adb.show();


                    return false;
                }
            });

        } else {
            imageupload.setVisibility(View.GONE);
        }



        image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String getted = text_add.getText().toString().trim();
                int val = getted.length();
//                if(val !=0)
//                {
                final StorageReference storageReference = FirebaseStorage.getInstance().getReference("vetregister/" + System.currentTimeMillis() + ".jpg");
                if (urimageurl != null) {
                    storageReference.putFile(urimageurl)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    String myprofileurl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                                    Log.d(TAG, "Profile image uploading url " + myprofileurl);

                                    storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            path = task.getResult().toString();
                                            Log.i("URL", path);
                                            storeuserinfo(getted);
                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Check Your Internet Connection.", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });
    }

    private void deletefunction(String selected) {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("upcomming");
        mDatabaseReference.child(selected).removeValue();


    }

    private void getDetails() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("upcomming");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    getEventDetailss.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        GetEventDetails getEventDetails = dataSnapshot1.getValue(GetEventDetails.class);
                        getEventDetailss.add(getEventDetails);
                    }
//                    if (name.equals("check")) {
//                        imageupload.setVisibility(View.GONE);
//                    }
                    ListAdapter listAdapter = new ListAdaptor(upcoming.this, getEventDetailss);
                    imaguploadlist.setAdapter(listAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void storeuserinfo(String getted) {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("upcomming");
        String key = mDatabaseReference.push().getKey();
        GetEventDetails getEventDetails = new GetEventDetails(key, path, getted);
        mDatabaseReference.child(key).setValue(getEventDetails);
        reset();
    }

    private void reset() {

        image.setImageResource(R.mipmap.ic_launcher);
        text_add.setText("");
    }

    @Override
    protected void onStart() {
        super.onStart();
//        FirebaseUser id = FirebaseAuth.getInstance().getCurrentUser();
//        name = id.getUid();
//        Log.e(TAG, "onStart: "+name );
        getDetails();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
//            String name = intent.getData().getPath();
//            Log.d(TAG, "File Name : "+name);
                startActivityForResult(Intent.createChooser(intent, "Choose Profile Image"), CHOOSE_FILE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_FILE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            urimageurl = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), urimageurl);
                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
