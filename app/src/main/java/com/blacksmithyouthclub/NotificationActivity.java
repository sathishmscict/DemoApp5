package com.blacksmithyouthclub;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.blacksmithyouthclub.adapter.NotificaitonAdapterRecyclerView;
import com.blacksmithyouthclub.helper.CommonMethods;
import com.blacksmithyouthclub.realm.model.NotificationMaster;
import com.blacksmithyouthclub.session.SessionManager;
import com.squareup.picasso.Picasso;

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
    private RealmResults<NotificationMaster> notificationsData;
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

//                    Toast.makeText(context, "Position  : "+notificationsData.get(position).getId(), Toast.LENGTH_SHORT).show();

                    notificationsData.get(position).setReaded(true);


                    realm.commitTransaction();

                    FillDataOnRecylerView();


                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_notification_data);
                    dialog.setCancelable(true);

                    try {
                        LinearLayout llmain = (LinearLayout) dialog.findViewById(R.id.llmain);
                        llmain.setVisibility(LinearLayout.VISIBLE);
                        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fadein);
                        animation.setDuration(1000);
                        llmain.setAnimation(animation);
                        llmain.animate();
                        animation.start();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }


                    TextView tvDate = (TextView) dialog.findViewById(R.id.tvDate);
                    EditText edtTitle = (EditText) dialog.findViewById(R.id.edtTitle);
                    EditText edtDescr = (EditText) dialog.findViewById(R.id.edtDescr);

                    ImageView ivIcon = (ImageView) dialog.findViewById(R.id.ivIcon);

                    try {
                        Log.d(TAG , "Image Data : "+notificationsData.get(position).getImageURL());
                        //Glide.with(context).load(userData.getAvatar()).error(R.mipmap.ic_launcher).into(imgProfilePic);
                        Picasso.with(context)
                                .load(notificationsData.get(position).getImageURL())
                                .placeholder(R.drawable.app_logo)
                                .error(R.drawable.app_logo)
                                .into(ivIcon);

                        //sessionManager.setEncodedImage(CommonMethods.getStringImage(myBitmapAgain));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    final Button btnOk = (Button) dialog.findViewById(R.id.btnok);

                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.cancel();
                            dialog.dismiss();
                        }
                    });


                    tvDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            btnOk.performClick();
                        }
                    });

                    edtTitle.setText(notificationsData.get(position).getTitle());
                    edtDescr.setText(notificationsData.get(position).getDescr());
                    tvDate.setText(" Date :"+notificationsData.get(position).getDate());





                    dialog.getWindow().setLayout(Toolbar.LayoutParams.FILL_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);

                    dialog.show();





                            /*InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromInputMethod(edttarget.getWindowToken(),0);*/

                    Log.d("getCurrentFocus : ", String.valueOf(getCurrentFocus().toString()));
                            /*if(getCurrentFocus()!=null) {
                                *//*InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);*//*
                                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                            }

                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);*/

                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


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

        notificationsData = realm.where(NotificationMaster.class).findAllSorted("id", Sort.DESCENDING);
        //personsData.deleteAllFromRealm();
        realm.commitTransaction();


        Log.d(TAG, "Total " + notificationsData.size() + " Records Found in NotificationMaster master");


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
