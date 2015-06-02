package com.ltrix.jk.quiapo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jk on 5/7/15.
 */
public class RequestStatusSubAdapter extends ArrayAdapter<String >{
    private Context context;
    private List<String > objects;

    public RequestStatusSubAdapter(Context context, int resource, List<String > objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/tf_font.ttf");

        final String item = objects.get(position);
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_request_status_sub,null);
        String [] items =  item.split(":");

        TextView tvRequest = (TextView) view.findViewById(R.id.tvRequest);
        TextView tvQuantity = (TextView) view.findViewById(R.id.tvQuantity);

        tvQuantity.setTypeface(typeFace);
        tvRequest.setTypeface(typeFace);

        tvRequest.setText(items[0]);
        tvQuantity.setText(items[1]);

        return view;
    }



}
