package com.ltrix.jk.quiapo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


/**
 * Created by jk on 4/6/15.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        setContentView(R.layout.activity_login);
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/tf_font.ttf");
        final ParseUser parseUser = ParseUser.getCurrentUser();
        if (parseUser != null) {

           afterLogin("no password");
        }

        ImageButton loginButton = (ImageButton) findViewById(R.id.button);
        final EditText username = (EditText) findViewById(R.id.etUsername);
        final EditText password = (EditText) findViewById(R.id.etPassword);
        username.setTypeface(typeFace);
        password.setTypeface(typeFace);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (username.getText().toString().matches("") || password.getText().toString().matches("")){

                    Toast.makeText(LoginActivity.this,"Empty field",Toast.LENGTH_LONG).show();
                }else {
                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("Logging in");
                    progressDialog.setMessage("Loading....");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {

                        @Override
                        public void done(ParseUser parseUser, ParseException e) {


                            if (parseUser != null) {





                                progressDialog.hide();
                                if (username.getText().toString().equals(password.getText().toString())){
                                    afterLogin("password");

                                }else {
                                    afterLogin("none");
                                }
                                username.setText("");
                                password.setText("");
                            } else {
                                // Signup failed. Look at the ParseException to see what happened.
                                progressDialog.hide();
                                if (e.getCode() == 101){

                                    Toast.makeText(LoginActivity.this, "Invalid username/password", Toast.LENGTH_LONG).show();

                                }else {

                                     Toast.makeText(LoginActivity.this, "Cannot connect to QE. Please check your internet connection", Toast.LENGTH_LONG).show();

                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private void afterLogin(String password) {

        Log.v("pass",password);
        Intent intent;
        if (ParseUser.getCurrentUser().getUsername().equals("admin")){

            intent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(intent);

        }else if (password.equals("password")){

            intent = new Intent(LoginActivity.this, ChangePasswordActivity.class);
            intent.putExtra("backStatus","yes");
            startActivityForResult(intent,100);

        }
        else {
            intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==100 && resultCode == RESULT_OK){

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }else {

            Toast.makeText(LoginActivity.this, "failed to update password ", Toast.LENGTH_LONG).show();

        }
    }
}
