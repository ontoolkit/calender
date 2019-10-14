package com.project.srmcalender.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.srmcalender.Pojo.Event;
import com.project.srmcalender.R;

import java.util.ArrayList;

public class ProductList extends ArrayAdapter<Event> {

    private Activity context;
    private ArrayList<Event> objects;

    public ProductList(Context context, ArrayList<Event> objects) {
        super(context, R.layout.eventitems, objects);
        this.context = (Activity) context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.eventitems, null);
        TextView mname = (TextView) view.findViewById(R.id.mname);

        Event event = objects.get(position);
        mname.setText(event.getEventdesc());

        return view;
    }
}
