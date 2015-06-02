package com.ltrix.jk.quiapo;

import android.app.Activity;
import android.content.Context;
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
 * Created by jk on 4/30/15.
 */
public class RequestStatusAdapter extends ArrayAdapter<ParseObject> {

    private  Context context;
    private  List<ParseObject> objects;

    public RequestStatusAdapter(Context context, int resource, List<ParseObject> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/tf_font.ttf");

        final ParseObject parseObject = objects.get(position);
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_request_status,null);
        TextView date = (TextView) view.findViewById(R.id.status_date);
        final TextView time = (TextView) view.findViewById(R.id.status_time);
        TextView status = (TextView) view.findViewById(R.id.status);
//        ImageButton btn = (ImageButton) view.findViewById(R.id.closeButton);
//        if (parseObject.getString("status").equals("read") || parseObject.getString("status").equals("unread")){
//            view.setBackgroundColor(Color.GRAY);
//        }else if (parseObject.getString("status").equals("confirm")){
//
//            view.setBackgroundColor(Color.GREEN);
//        }else if (parseObject.getString("status").equals("invoiced")){
//
//            view.setBackgroundColor(Color.RED);
//
//        }else {
//
//            view.setBackgroundColor(Color.BLUE);
//
//        }
        date.setTypeface(typeFace);
        time.setTypeface(typeFace);
        status.setTypeface(typeFace);
        DateFormat df = new SimpleDateFormat("dd-MM-yy");
        date.setText(df.format(parseObject.getCreatedAt()));
        df = new SimpleDateFormat(" hh:mm aa");
        time.setText(df.format(parseObject.getCreatedAt()));
        if (parseObject.get("status").toString().equals("read") || parseObject.get("status").toString().equals("unread")){

            status.setText("waiting");

        }else {

            status.setText(parseObject.get("status").toString());

        }
        return view;

    }



}
