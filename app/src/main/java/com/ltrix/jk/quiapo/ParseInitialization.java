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
        Parse.initialize(this, "aSGl0iNrVj9DVQRpU8dd3KUAtD0Ucrd8Ru1IgRa5", "faDg9p901a0TLsVbuTauMUCwvQUBHmtuhJWLk61S");


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
