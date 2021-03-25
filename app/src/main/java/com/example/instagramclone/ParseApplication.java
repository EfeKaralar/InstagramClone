package com.example.instagramclone;

import com.example.instagramclone.models.Post;
import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Application;

public class ParseApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("GAWeEfnfpdEsdyJ5zwFORDU955Kp2lIAhGnuwEw9")
                .clientKey("tfNsipkWFpKhYdcRqw17OIrjwf07GVFU9GJR4KG5")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
