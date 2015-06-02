package com.ltrix.jk.quiapo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by jk on 4/19/15.
 */
public class AdminAdapter extends ArrayAdapter<ParseObject>{
    private  Context context;
    private  List<ParseObject> objects;


    public AdminAdapter(Context context, int resource, List<ParseObject> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/tf_font.ttf");

        ParseObject parseObject = objects.get(position);
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_admin,null);
        TextView name = (TextView) view.findViewById(R.id.adminName);
        TextView date = (TextView) view.findViewById(R.id.adminDate);
        if (parseObject.getString("status").equals("unread")){

            name.setTextColor(Color.GRAY);
            date.setTextColor(Color.GRAY);

        }
        name.setText(parseObject.getString("username"));
        DateFormat df = new SimpleDateFormat("dd - MMMM - yyyy hh:mm:ss aa");
        date.setText(df.format(parseObject.getCreatedAt()));
        return view;

    }
}
