package com.blacksmithyouthclub;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.blacksmithyouthclub.helper.CommonMethods;
import com.blacksmithyouthclub.helper.Config;
import com.blacksmithyouthclub.helper.NotificationUtils;
import com.blacksmithyouthclub.realm.model.NotificationMaster;
import com.blacksmithyouthclub.session.SessionManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import io.realm.Realm;


/**
 * Created by Satish Gadde on 02-09-2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    private Intent resultIntent;
    private Realm realm;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());


        if (Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

        try {
            Log.d(TAG, "NotificationMaster Message Body: " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "From: " + remoteMessage.getFrom());
            Log.d(TAG, "NotificationMaster Message Body: " + remoteMessage.getData().get("notification"));
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Error FCM : " + e.getMessage());
        }
        Log.d(TAG, "Received message");








        //intenttype = activation/general


        String message = remoteMessage.getData().get("message");
        String title = remoteMessage.getData().get("title");
        String notification_id = remoteMessage.getData().get("notificationid");
        String img_url = remoteMessage.getData().get("imageurl");
        String type = remoteMessage.getData().get("type");
        String intenttype = remoteMessage.getData().get("intenttype");


        if(img_url == null)
        {
            img_url ="";

        }


        try {
            //get realm instance
            this.realm = Realm.getDefaultInstance();

            realm.beginTransaction();

            NotificationMaster notificationMaster = new NotificationMaster();
            notificationMaster.setId(realm.where(NotificationMaster.class).findAll().size() + 1);
            notificationMaster.setDate(CommonMethods.getDateCurrentDate());
            notificationMaster.setDescr(message);
            notificationMaster.setTitle(title);
            notificationMaster.setImageURL(img_url);
            notificationMaster.setType(type);
            notificationMaster.setIntenttype(intenttype);

            notificationMaster.setNotificationid(Integer.parseInt(notification_id));
            notificationMaster.setReaded(false);

            realm.copyToRealm(notificationMaster);
            realm.commitTransaction();
            Log.d(TAG, "NotificationMaster has been added in database");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        System.out.println("MESSAGE : " + message + " TITLE :" + title + " NotificationID : " + notification_id + " img_url :" + img_url + "type: " + type + " intenttype : " + intenttype);


        handleDataMessage(message, title, Integer.parseInt(notification_id), img_url, type,intenttype);


       /* if (remoteMessage == null)
                return;

            // Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                Log.e(TAG, "NotificationMaster Body: " + remoteMessage.getNotification().getBody());
                handleNotification(remoteMessage.getNotification().getBody());
            }

            // Check if message contains a data payload.
            if (remoteMessage.getData().size() > 0) {
                Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

                try {
                    JSONObject json = new JSONObject(remoteMessage.getData().toString());
                    handleDataMessage(json);
                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e.getMessage());
                }
            }
        }*/

   /* private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }else{
            // If the app is in background, firebase itself handles the notification
        }
    }*/

    }

    private void handleDataMessage(String message, String title, int notificationId, String imageURL, String type,String intenttype) {


        SessionManager sessionManager = new SessionManager(this);

        if(intenttype.equals("activation"))
        {
            sessionManager.setUserActivationStatus(true);
        }


        try {



              /*  sessionManager.setNotificationStatus("true",productid,categoryid);
                sessionManager.setCategoryTypeAndIdDetails("category", categoryid, "");*/


            Log.d(TAG, "message : " + message);
            Log.d(TAG, "title : " + title);
            Log.d(TAG, "notificationId : " + notificationId);
            Log.d(TAG, "imageUrl : " + imageURL);
            Log.d(TAG, "type : " + type);
            Log.d(TAG, "intenttype : " + intenttype);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext()))
            {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();

                // app is in background, show the notification in notification tray
                //Intent resultIntent = new Intent(getApplicationContext(), NewDashBoardActivity.class);

                if (intenttype.equals("activation") || intenttype.isEmpty() || intenttype == null) {

                    resultIntent = new Intent(getApplicationContext(), DashBoardActivity.class);

                } else

                {

                    //sessionManager.setOfferDetails(offerid, "");
                    resultIntent = new Intent(getApplicationContext(), NotificationActivity.class);


                }


                // check for image attachment
                if (TextUtils.isEmpty(imageURL)) {
                    showNotificationMessage(getApplicationContext(), title, message, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, resultIntent, imageURL);
                }


            } else {
                // app is in background, show the notification in notification tray

                if (intenttype.equals("activation") || intenttype.isEmpty() || intenttype == null) {

                    resultIntent = new Intent(getApplicationContext(), DashBoardActivity.class);

                } else

                {

                    //sessionManager.setOfferDetails(offerid, "");
                    resultIntent = new Intent(getApplicationContext(), NotificationActivity.class);


                }


                //Intent resultIntent = new Intent(getApplicationContext(), DashBoardActivity.class);

                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageURL)) {
                    showNotificationMessage(getApplicationContext(), title, message, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, resultIntent, imageURL);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, intent, imageUrl);
    }
}