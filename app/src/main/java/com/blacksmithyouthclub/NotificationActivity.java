package com.blacksmithyouthclub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.blacksmithyouthclub.adapter.NotificaitonAdapterRecyclerView;
import com.blacksmithyouthclub.helper.CommonMethods;
import com.blacksmithyouthclub.realm.model.Notification;
import com.blacksmithyouthclub.session.SessionManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.rvNotificationList)
    RecyclerView rvNotificationList;

    @BindView(R.id.tvNodata)
    TextView tvNodata;
    private Realm realm;
    private RealmResults<Notification> notificationsData;
    private String TAG = NotificationActivity.class.getSimpleName();
    private Context context = this;
    private SessionManager sessionManager;
    private HashMap<String, String> userDetails = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setTitle("Notifications");

        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setHomeAsUpIndicator(AllKeys.back_button);
            //  getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //get realm instance
        this.realm = Realm.getDefaultInstance();

        LinearLayoutManager lManager = new LinearLayoutManager(context);
        rvNotificationList.setLayoutManager(lManager);

        sessionManager = new SessionManager(context);
        userDetails = sessionManager.getSessionDetails();


        FillDataOnRecylerView();

        rvNotificationList.addOnItemTouchListener(new CommonMethods.RecyclerTouchListener(context, rvNotificationList, new CommonMethods.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                try {



                    realm.beginTransaction();

                    Toast.makeText(context, "Position  : "+notificationsData.get(position).getId(), Toast.LENGTH_SHORT).show();
                    notificationsData.get(position).setReaded(true);



                    realm.commitTransaction();

                    FillDataOnRecylerView();



                    // sessionManager.setOfferDetails(String.valueOf(notificationsData.get(position).getOfferid()), notificationsData.get(position).getTitle());


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    private void FillDataOnRecylerView() {

        realm.refresh();

        realm.beginTransaction();

        notificationsData = realm.where(Notification.class).findAllSorted("id", Sort.DESCENDING);
        //personsData.deleteAllFromRealm();
        realm.commitTransaction();


        Log.d(TAG, "Total " + notificationsData.size() + " Records Found in Notification master");


        if (notificationsData.size() > 0) {
            NotificaitonAdapterRecyclerView adapter = new NotificaitonAdapterRecyclerView(context, notificationsData);
            rvNotificationList.setAdapter(adapter);
            rvNotificationList.setVisibility(View.VISIBLE);
            tvNodata.setVisibility(View.GONE);
        } else {
            rvNotificationList.setVisibility(View.GONE);
            tvNodata.setVisibility(View.VISIBLE);
            tvNodata.setText(getString(R.string.str_no_notifications));


        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            Intent intent = new Intent(context, DashBoardActivity.class);

            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, DashBoardActivity.class);

        startActivity(intent);
        finish();

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onBackPressed();

    }

}
