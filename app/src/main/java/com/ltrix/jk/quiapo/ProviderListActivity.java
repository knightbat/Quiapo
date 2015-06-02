package com.ltrix.jk.quiapo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jk on 4/2/15.
 */
public class ProviderListActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 100;

    List<ProviderElement> mapProviders = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapProviders = new providerData().getProviders();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_providerlist);
        setTitle("Request");

        ListView listView = (ListView) findViewById(R.id.listView);

        Log.v("list",mapProviders.toString());

        ProviderListAdapter adapter = new ProviderListAdapter(ProviderListActivity.this,R.layout.item_buy,mapProviders);
        listView.setAdapter(adapter);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_second, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){

            finish();
        }
        if (item.getItemId() == R.id.action_addtocart)
        {
            HashMap<String,List<ProviderElement>> hashMap = new HashMap<>();
            hashMap.put("toCart", mapProviders);
            Intent intent = new Intent(ProviderListActivity.this,CartActivity.class);
            intent.putExtra("toCart", hashMap);
            startActivityForResult(intent, REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){

            finish();
        }
    }
}
