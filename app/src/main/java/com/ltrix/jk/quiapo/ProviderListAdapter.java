package com.ltrix.jk.quiapo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Created by jk on 4/3/15.
 */
public class ProviderListAdapter extends ArrayAdapter<ProviderElement>{

    private Context context;
    private List<ProviderElement> objects;

    public ProviderListAdapter(Context context, int resource, List<ProviderElement> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;

    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/tf_font.ttf");

        final ProviderElement providerElement = objects.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_buy,null);
        final RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.main);
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.radioButton);
        final EditText editText = (EditText) view.findViewById(R.id.editText);
        editText.setTypeface(typeFace);
        if (providerElement.costValue.toString().equals("main")){

            int resId = context.getResources().getIdentifier(providerElement.providerName, "drawable", context.getPackageName());
            layout.setBackgroundResource(resId);
            editText.setVisibility(View.INVISIBLE);
            checkBox.setVisibility(view.INVISIBLE);

        }else {
//        final ImageView imageView = (ImageView) view.findViewById(R.id.image);

            String string = Integer.toString(providerElement.quantity);
            if (Integer.parseInt(string) > 0) {
                editText.setText(string);
            }
            if (providerElement.quantity > 0) {
                checkBox.setChecked(true);
            }

            int resId = context.getResources().getIdentifier(providerElement.providerName + providerElement.costValue, "drawable", context.getPackageName());
            layout.setBackgroundResource(resId);

            editText.setSelection(editText.getText().length());
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText et = (EditText) v;

                    if (et.getText().length() > 0) {

                        if (Integer.parseInt(et.getText().toString()) > 0) {
                            checkBox.setChecked(true);
                            providerElement.quantity = Integer.parseInt(et.getText().toString());
                        } else {

//                        providerElement.quantity = 0;
                            checkBox.setChecked(false);
                        }
                    } else {
//                    providerElement.quantity= 0;
                        checkBox.setChecked(false);

                    }

                }
            });
        }
        return view;
    }





}
