package com.ltrix.jk.quiapo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by jk on 5/23/15.
 */
public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passsword);
        setTitle("Change Password");

        Typeface typeFace=Typeface.createFromAsset(this.getAssets(),"fonts/tf_font.ttf");

        final Intent intent = getIntent();
        if (intent.getStringExtra("backStatus").equals("yes")){

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etConfirm = (EditText) findViewById(R.id.etConfirmPassword);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword.setTypeface(typeFace);
        etConfirm.setTypeface(typeFace);
        etEmail.setTypeface(typeFace);
        ImageButton done = (ImageButton) findViewById(R.id.doneButton);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPassword.getText().length()== 0 || etConfirm.getText().length() == 0){

                    Toast.makeText(ChangePasswordActivity.this,"Empty Field",Toast.LENGTH_LONG).show();
                }else if (!etPassword.getText().toString().equals( etConfirm.getText().toString())){

                    Toast.makeText(ChangePasswordActivity.this,"Password don't match",Toast.LENGTH_LONG).show();

                }else {

                    final ProgressDialog progressDialog = new ProgressDialog(ChangePasswordActivity.this);
                    progressDialog.setTitle("Sending Request");
                    progressDialog.setMessage("Loading....");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    ParseUser parseUser = ParseUser.getCurrentUser();
                    parseUser.setPassword(etPassword.getText().toString());
                    if (etEmail.getText().length() > 0){

                        parseUser.setEmail(etEmail.getText().toString());
                    }

                    parseUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            progressDialog.hide();

                            if (e == null) {
                                Intent intent1 = new Intent();
                                setResult(RESULT_OK, intent);
                                finish();
                            } else {
                                finish();
                            }

                        }
                    });
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
