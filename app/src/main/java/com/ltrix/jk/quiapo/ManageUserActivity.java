package com.ltrix.jk.quiapo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jk on 5/20/15.
 */
public class ManageUserActivity extends AppCompatActivity {

    List<ParseUser> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_manageuser);
        final Spinner userSpinner = (Spinner) findViewById(R.id.spinnerUser);
        final Spinner typeSpinner = (Spinner) findViewById(R.id.spinnerType);
        final EditText amount = (EditText) findViewById(R.id.etAmount);
        final Button amoutBtn = (Button) findViewById(R.id.buttonAmt);
        final TextView amountText  = (TextView) findViewById(R.id.tvAmount);
        List<String> typeArray = new ArrayList<>();
        final List<String > typeUser = new ArrayList<>();
        typeArray.add("current payment");
        typeArray.add("Total balance");

        ArrayAdapter<String> adapterType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeArray);

        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapterType);

        ParseQuery<ParseUser> queryUser = ParseUser.getQuery();
        queryUser.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    users = objects;
                    for (ParseUser parseUser : objects){
                        typeUser.add(parseUser.getUsername());
                    }
                    ArrayAdapter<String> adapterName = new ArrayAdapter<>(ManageUserActivity.this, android.R.layout.simple_spinner_item, typeUser);
                    adapterName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    userSpinner.setAdapter(adapterName);

                } else {
                    // Something went wrong.
                }
            }
        });


        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Log.v("user", userSpinner.getSelectedItem().toString());
                ParseQuery<ParseObject> query = ParseQuery.getQuery("BalanceDue");
                query.whereEqualTo("username",userSpinner.getSelectedItem().toString());
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject parseObject, ParseException e) {

                        if (e ==null) {
                            amountText.setText(parseObject.getDouble("amount") + " SAR");
                        }else {

                            amountText.setText("0 SAR");
                        }

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        amoutBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (amount.getText().toString().length() >0){
                    final String username = userSpinner.getSelectedItem().toString();
                    final Double amountNum = Double.parseDouble(amount.getText().toString());
                    amoutBtn.setEnabled(false);


                    if (typeSpinner.getSelectedItemId() == 0){

                        ParseObject parseObject = new ParseObject("UserPayment");
                        parseObject.put("username",username);
                        parseObject.put("amount",amountNum);
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                amoutBtn.setEnabled(true);
                                amount.setText("");

                            }
                        });
                        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("BalanceDue");
                        parseQuery.whereEqualTo("username", userSpinner.getSelectedItem());
                        parseQuery.getFirstInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject parseObject, ParseException e) {
                                if (e == null) {
                                    double amt = parseObject.getDouble("amount");
                                    amt -= amountNum;
                                    parseObject.put("amount", amt);
                                    parseObject.saveInBackground();

                                    ParseQuery pushQuery = ParseInstallation.getQuery();
                                    pushQuery.whereEqualTo("username", username);

                                    ParsePush push = new ParsePush();
                                    push.setQuery(pushQuery);
                                    push.setMessage("Your payment SAR " + amountNum+" has been confirmed");
                                    push.sendInBackground();
                                }
                            }
                        });
                    }else {
//
                        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("BalanceDue");
                        parseQuery.whereEqualTo("username",userSpinner.getSelectedItem());
                        parseQuery.getFirstInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject parseObject, ParseException e) {
                                if (e == null) {

                                    parseObject.put("username",username);
                                    parseObject.put("amount",amountNum);
                                    parseObject.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            amount.setText("");
                                            amoutBtn.setEnabled(true);
                                            amountText.setText(amountNum+" SAR");
                                            ParseQuery pushQuery = ParseInstallation.getQuery();
                                            pushQuery.whereEqualTo("username", username);

                                            ParsePush push = new ParsePush();
                                            push.setQuery(pushQuery);
                                            push.setMessage("Your total balance has been updated. Remaining credit is SAR " + amountNum);
                                            push.sendInBackground();

                                        }
                                    });

                                } else if (e.getCode() == 101){

                                    ParseObject parseObject1 = new ParseObject("BalanceDue");
                                    parseObject1.put("username",username);
                                    parseObject1.put("amount",amountNum);
                                    parseObject1.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            amount.setText("");
                                            amoutBtn.setEnabled(true);

                                            ParseQuery pushQuery = ParseInstallation.getQuery();
                                            pushQuery.whereEqualTo("username", username);

                                            ParsePush push = new ParsePush();
                                            push.setQuery(pushQuery);
                                            push.setMessage("Your total balance has been updated. Remaining credit is SAR " + amountNum);
                                            push.sendInBackground();
                                        }
                                    });
                                }else {

                                    amoutBtn.setEnabled(true);

                                }
                            }
                        });
                    }


                    ;
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
