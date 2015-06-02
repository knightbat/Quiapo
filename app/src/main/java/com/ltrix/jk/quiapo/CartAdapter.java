package com.ltrix.jk.quiapo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jk on 4/7/15.
 */
public class CartAdapter extends ArrayAdapter<ProviderElement> {

    private Context context;
    private List<ProviderElement> objects;
    public CartAdapter(Context context, int resource, List<ProviderElement> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        Typeface typeFace=Typeface.createFromAsset(context.getAssets(), "fonts/tf_font.ttf");

        final ProviderElement cartElement =  objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_cart,null);
        TextView tvProvider = (TextView) view.findViewById(R.id.textView2);
        TextView tvPrice = (TextView) view.findViewById(R.id.textView3);
        TextView tvQuantity = (TextView) view.findViewById(R.id.textView4);
        TextView tvX = (TextView) view.findViewById(R.id.textViewX);
        tvProvider.setTypeface(typeFace);
        tvPrice.setTypeface(typeFace);
        tvQuantity.setTypeface(typeFace);
        tvX.setTypeface(typeFace);

        ImageButton button = (ImageButton) view.findViewById(R.id.delete_button);
        tvProvider.setText(cartElement.providerName);
        tvQuantity.setText(Integer.toString(cartElement.quantity));
        tvPrice.setText(cartElement.costValue);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objects.remove(position);
                notifyDataSetChanged();

            }
        });
        return view;
    }


}
