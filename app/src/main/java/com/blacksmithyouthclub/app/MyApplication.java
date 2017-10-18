package com.blacksmithyouthclub.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.LruCache;


import com.crashlytics.android.Crashlytics;
import com.firebase.client.Firebase;


import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by SATHISH on 28-Jun-17.
 */

public class MyApplication extends Application {


    public static final String TAG = MyApplication.class
            .getSimpleName();


    private static MyApplication mInstance;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //Allowing Strict mode policy for Nougat support
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        mInstance = this;

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        //Initializing firebase
        try {
            Firebase.setAndroidContext(getApplicationContext());


            Fabric.with(this, new Crashlytics());

            final Fabric fabric = new Fabric.Builder(this)
                    .kits(new Crashlytics())
                    .debuggable(true)
                    .build();
            Fabric.with(fabric);


            Fabric.with(getApplicationContext(), new Crashlytics());


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
