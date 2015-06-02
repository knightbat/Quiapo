package com.ltrix.jk.quiapo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by jk on 4/28/15.
 */
public class RequestStatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Order Status");


        setContentView(R.layout.activity_requeststatus);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Requests");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername().toString());
        query.orderByDescending("createdAt");
        final ProgressDialog progressDialog = new ProgressDialog(RequestStatusActivity.this);
        progressDialog.setTitle("Fetching Data");
        progressDialog.setMessage("Loading....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {

                if (e == null) {
                    ListView listView = (ListView) findViewById(R.id.listView_status);
                    RequestStatusAdapter adapter = new RequestStatusAdapter(RequestStatusActivity.this, R.layout.item_request_status, objects);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent intent = new Intent(RequestStatusActivity.this, RequestStatusSubActivity.class);
                            intent.putExtra("request", objects.get(position).getString("request"));
                            intent.putExtra("amount",objects.get(position).getDouble("amount"));
                            intent.putExtra("status",objects.get(position).getString("status"));
                            startActivity(intent);

                        }

                    });

                    progressDialog.hide();

                } else {
                    progressDialog.hide();

                    Toast.makeText(RequestStatusActivity.this, "Error fetching", Toast.LENGTH_LONG).show();
                }
            }
        });

        ParseQuery<ParseObject> bgQuery = ParseQuery.getQuery("BalanceDue");
        bgQuery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername().toString());
        bgQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {

                if (e == null) {
                    setTitle("Balance Due: SAR " + parseObject.getDouble("amount"));
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
