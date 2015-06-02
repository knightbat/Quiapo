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
 * Created by jk on 5/24/15.
 */
public class PaymentHistoryAdapter extends ArrayAdapter<ParseObject> {

    private Context context;
    private List<ParseObject> objects;

    public PaymentHistoryAdapter(Context context, int resource, List<ParseObject> objects) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/tf_font.ttf");

        final ParseObject parseObject = objects.get(position);
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_history,null);


        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
        TextView tvAmount = (TextView) view.findViewById(R.id.tvAmount);

        tvDate.setTypeface(typeFace);
        tvAmount.setTypeface(typeFace);

        DateFormat df = new SimpleDateFormat("dd-MM-yy");
        tvDate.setText(df.format(parseObject.getCreatedAt()));

        tvAmount.setText("SAR  "+parseObject.getDouble("amount"));

        return view;

    }
}
