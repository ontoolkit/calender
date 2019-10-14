package com.project.srmcalender.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.project.srmcalender.Pojo.GetEventDetails;
import com.project.srmcalender.R;

import java.util.ArrayList;

public class ListAdaptor extends ArrayAdapter<GetEventDetails> {

    private Activity context;
    ArrayList<GetEventDetails> objects;

    public ListAdaptor(@NonNull Context context, ArrayList<GetEventDetails> objects) {
        super(context, R.layout.userview, objects);
        this.context = (Activity) context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.userview, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView textView = (TextView) view.findViewById(R.id.mytext);
        GetEventDetails getEventDetails = objects.get(position);
        Glide.with(context).load(getEventDetails.getImageurl()).into(imageView);
        textView.setText(getEventDetails.getContent());

        return view;
    }
}
