package com.ltrix.jk.quiapo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.parse.ParseInstallation;
import com.parse.ParseUser;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Welcome");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("username", ParseUser.getCurrentUser().getUsername());
        installation.saveInBackground();

        setContentView(R.layout.activity_main);
        ImageButton listButton = (ImageButton) findViewById(R.id.listBtn) ;
        ImageButton statusButton = (ImageButton) findViewById(R.id.statusBtn) ;
        ImageButton paymentButton = (ImageButton) findViewById(R.id.paymentBtn) ;



        listButton.setOnClickListener(this);
        statusButton.setOnClickListener(this);
        paymentButton.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
            installation.put("username", "");
            installation.saveInBackground();
            ParseUser.logOutInBackground();
            finish();
            return true;
        }else if(id == R.id.action_change_pass){

            Intent intent = new Intent(MainActivity.this,ChangePasswordActivity.class);
            intent.putExtra("backStatus","yes");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.listBtn){

            Intent intent = new Intent(MainActivity.this,ProviderListActivity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.paymentBtn){

            Intent intent = new Intent(MainActivity.this,PaymentHistoryActivity.class);
            startActivity(intent);
        }else {

            Intent intent = new Intent(MainActivity.this,RequestStatusActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onBackPressed() {

        Log.v("test", "test");
        ActivityCompat.finishAffinity(this);
    }


}
