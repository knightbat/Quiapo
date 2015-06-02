package com.ltrix.jk.quiapo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by jk on 5/23/15.
 */
public class PaymentHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymenthistory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ListView listView = (ListView) findViewById(R.id.paymentList);

        final ProgressDialog progressDialog = new ProgressDialog(PaymentHistoryActivity.this);
        progressDialog.setTitle("Fetching Data");
        progressDialog.setMessage("Loading....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("UserPayment");
        parseQuery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername().toString());
        parseQuery.orderByDescending("createdAt");
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                progressDialog.hide();
                if (e == null){
                    Log.v("list",list.toString());
                    PaymentHistoryAdapter adapter = new PaymentHistoryAdapter(PaymentHistoryActivity.this, R.layout.item_history, list);
                    listView.setAdapter(adapter);
                }else {

                    Toast.makeText(PaymentHistoryActivity.this,"Error Fetching",Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
