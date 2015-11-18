package com.ltrix.jk.quiapo;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by jk on 4/6/15.
 */
public class ParseInitialization extends Application {

    @Override
    public void onCreate() {
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "baLV0ZB1rdYRIKcYYToLRTi7PC1fFqOTJgIgVU6j", "62yLgmACiCYIGCoYqvnh4P3oVTittwOLdWgoEGq7");


        ParsePush.subscribeInBackground("common", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }

            }
        })
;
    }
}
