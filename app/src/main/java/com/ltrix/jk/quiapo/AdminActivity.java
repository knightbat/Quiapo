package com.ltrix.jk.quiapo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by jk on 4/19/15.
 */
public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("username", ParseUser.getCurrentUser().getUsername());
        installation.saveInBackground();
        setContentView(R.layout.activity_admin);




        ParseQuery<ParseObject> query = ParseQuery.getQuery("Requests");
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {


                ListView listView = (ListView) findViewById(R.id.listViewAdmin);
                AdminAdapter adapter = new AdminAdapter(AdminActivity.this, R.layout.item_admin, objects);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(AdminActivity.this, AdminSubActivity.class);
                        intent.putExtra("request", objects.get(position).getString("request"));
                        intent.putExtra("id", objects.get(position).getObjectId());
                        intent.putExtra("status", objects.get(position).getString("status"));
                        intent.putExtra("name", objects.get(position).getString("username"));
                        ParseObject object = objects.get(position);
                        if (object.get("status").toString().equals("unread")) {
                            object.put("status", "read");
                            object.saveInBackground();
                        }
                        startActivity(intent);

                    }
                });
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            ParseUser.logOutInBackground();
            finish();
            return true;
        }else if (id == R.id.action_manage){

            Intent intent = new Intent(AdminActivity.this,ManageUserActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        ActivityCompat.finishAffinity(this);
    }
}
