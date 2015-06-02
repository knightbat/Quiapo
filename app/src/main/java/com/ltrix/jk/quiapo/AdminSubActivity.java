package com.ltrix.jk.quiapo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jk on 4/23/15.
 */
public class AdminSubActivity extends AppCompatActivity implements View.OnClickListener {

    Button confirmBtn;
    Button invoicedBtn;
    Button readyBtn;
    String objId;
    double amt;
    String name;

    ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminsub);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String listData= intent.getStringExtra("request");
        String [] listArray = singleChars(listData);
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.addAll(Arrays.asList(listArray));

        ListView listView = (ListView) findViewById(R.id.listViewAdminSub);
        listAdapter = new ArrayAdapter<String>(AdminSubActivity.this,android.R.layout.simple_list_item_1,arrayList);

        confirmBtn = (Button) findViewById(R.id.btnConfirm);
        invoicedBtn = (Button) findViewById(R.id.btnInvoiced);
        readyBtn = (Button) findViewById(R.id.btnReady);
        confirmBtn.setOnClickListener(this);
        invoicedBtn.setOnClickListener(this);
        readyBtn.setOnClickListener(this);
        String status = intent.getStringExtra("status");
        if (status.equals("read") || status.equals("unread")){
            confirmBtn.setEnabled(true);
            invoicedBtn.setEnabled(false);
            readyBtn.setEnabled(false);
        }else if (status.equals("confirmed")){

            confirmBtn.setEnabled(false);
            invoicedBtn.setEnabled(true);
            readyBtn.setEnabled(false);
        }else if(status.equals("invoiced")){
            confirmBtn.setEnabled(false);
            invoicedBtn.setEnabled(false);
            readyBtn.setEnabled(true);
        }else{

            confirmBtn.setEnabled(false);
            invoicedBtn.setEnabled(false);
            readyBtn.setEnabled(false);
        }
        listView.setAdapter(listAdapter);
    }

    public static String[] singleChars(String s) {

        return s.split(",");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View v) {

        Intent intent = getIntent();
        objId = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        switch (v.getId()){

            case R.id.btnConfirm:

                parseOperation("confirmed");
                break;
            case R.id.btnInvoiced:

                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("Enter Amount");

                final EditText editText= new EditText(this);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                alert.setView(editText);
                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        amt = Double.parseDouble(editText.getText().toString());
                        Toast.makeText(AdminSubActivity.this, amt + "", Toast.LENGTH_LONG).show();
                        parseOperation("invoiced");
                    }
                });

                alert.show();
                break;

            case R.id.btnReady:
                parseOperation("ready");
                break;
            default:
                parseOperation("invoiced");

        }


    }

    public void parseOperation(final String statusStr) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Requests");

        query.getInBackground(objId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject parseObject, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to the Parse Cloud. playerName hasn't changed.
                    parseObject.put("status", statusStr);
                    parseObject.put("amount",amt);

                    if (statusStr.equals("invoiced")) {

                        final ParseQuery<ParseObject> query = ParseQuery.getQuery("BalanceDue");
                        query.whereEqualTo("username", name);
                        query.getFirstInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject parseObject, ParseException e) {

                                if (e == null){

                                    double currAmt = parseObject.getDouble("amount");
                                    currAmt+=amt;
                                    Log.v("amt",currAmt+"");
                                    parseObject.put("amount", currAmt);
                                    parseObject.saveInBackground();
                                }else if (e.getCode() == 101){

                                    ParseObject object = new ParseObject("BalanceDue");
                                    object.put("username",name);
                                    object.put("amount",amt);
                                    object.saveInBackground();
                                }
                            }
                        });

                    }

                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            String message;

                            if (statusStr.equals("confirmed")) {

                                confirmBtn.setEnabled(false);
                                invoicedBtn.setEnabled(true);
                                readyBtn.setEnabled(false);
                                message = "Your order is Confirmed";
                            } else if (statusStr.equals("invoiced")) {

                                confirmBtn.setEnabled(false);
                                invoicedBtn.setEnabled(false);
                                readyBtn.setEnabled(true);
                                message = "Your order is invoiced. Current bill amount is SAR "+amt;
                            } else {

                                confirmBtn.setEnabled(false);
                                invoicedBtn.setEnabled(false);
                                readyBtn.setEnabled(false);
                                message = "Your order is ready to be delivered";
                            }

                            ParseQuery pushQuery = ParseInstallation.getQuery();
                            pushQuery.whereEqualTo("username", name);

                            ParsePush push = new ParsePush();
                            push.setQuery(pushQuery);
                            push.setMessage(message);
                            push.sendInBackground();
                        }
                    });
                }
            }
        });

    }
}
