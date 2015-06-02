package com.ltrix.jk.quiapo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jk on 4/7/15.
 */
public class CartActivity extends AppCompatActivity {


    List<ProviderElement> cartElements = new ArrayList<>();
    boolean isEmpty = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_cart);
        Intent intent = getIntent();
        HashMap<String,List<ProviderElement>> hashMap = (HashMap < String, List < ProviderElement >>) intent.getSerializableExtra("toCart");
        List<ProviderElement> providerElements = hashMap.get("toCart");
        for (ProviderElement providerElement : providerElements){

            if (providerElement.quantity>0){

                cartElements.add(providerElement);
            }
        }
        ListView listView = (ListView) findViewById(R.id.listView);
        CartAdapter adapter = new CartAdapter(this,R.layout.item_cart,cartElements);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            finish();
        }else if (item.getItemId() == R.id.action_cartbuy){

            for (ProviderElement providerElement : cartElements){
                if (providerElement.quantity>0){

                    isEmpty = false;
                    break;
                }
            }

            if (!isEmpty) {

                final ProgressDialog progressDialog = new ProgressDialog(CartActivity.this);
                progressDialog.setTitle("Sending Request");
                progressDialog.setMessage("Loading....");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
                progressDialog.show();

                ParseObject object = new ParseObject("Requests");
                String result = "";
                object.put("username", ParseUser.getCurrentUser().getUsername());
                for (ProviderElement providerElement : cartElements) {

                    result = result+providerElement.providerName+"("+ providerElement.costValue+")"+" : "+providerElement.quantity+",";
                }
                object.put("request",result);
                object.put("status","unread");
                object.put("amount",0);
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null){
                            progressDialog.hide();
                            Toast.makeText(CartActivity.this,"no network",Toast.LENGTH_LONG).show();
                        }else {
                            progressDialog.hide();
                            Toast.makeText(CartActivity.this,"Stay Connected. Awaiting response from QE",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent();
                            setResult(RESULT_OK,intent);
                            finish();
                        }
                    }
                });

                ParseQuery pushQuery = ParseInstallation.getQuery();
                pushQuery.whereEqualTo("username", "admin");

                ParsePush push = new ParsePush();
                push.setQuery(pushQuery);
                push.setMessage("New order from " + ParseUser.getCurrentUser().getUsername());
                push.sendInBackground();
            }else {

                Toast.makeText(this,"empty list",Toast.LENGTH_LONG).show();
            }

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cart,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
