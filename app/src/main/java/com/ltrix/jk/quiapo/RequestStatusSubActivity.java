package com.ltrix.jk.quiapo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jk on 5/7/15.
 */
public class RequestStatusSubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_requeststatussub);
        Intent intent = getIntent();
        String listData= intent.getStringExtra("request");

        Log.v("status",intent.getStringExtra("status"));
        if (intent.getStringExtra("status").equals("confirmed")){
            setTitle("Order is Confirmed");
        }else if(intent.getStringExtra("status").equals("read") || intent.getStringExtra("status").equals("unread")) {
            setTitle("Stay Connected");
        }else{
            setTitle("SAR "+intent.getDoubleExtra("amount",0));
        }

        String [] listArray = singleChars(listData);
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.addAll(Arrays.asList(listArray));

        ListView listView = (ListView) findViewById(R.id.request_sub_listView);
         RequestStatusSubAdapter listAdapter = new RequestStatusSubAdapter(RequestStatusSubActivity.this,R.layout.item_request_status_sub,arrayList);

        listView.setAdapter(listAdapter);
    }

    public static String[] singleChars(String s) {

        return s.split(",");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
