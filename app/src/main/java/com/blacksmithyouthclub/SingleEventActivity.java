package com.blacksmithyouthclub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import dmax.dialog.SpotsDialog;

public class SingleEventActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private Context context=this;
    private SpotsDialog pDialog;
    private ImageView imgeventimg;
    private TextView tvtitle,tvdate,tvlongdesc;
    private String Event_IMG,Event_Title,Event_Date,Event_Desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinateLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initialization();
        Intent intent=getIntent();
        Event_IMG=intent.getStringExtra("Event_IMG");
        Event_Title=intent.getStringExtra("Event_Title");
        Event_Date=intent.getStringExtra("Event_Date");
        Event_Desc=intent.getStringExtra("Event_Desc");


        setTitle(""+Event_Title);
        setValue();
    }

    private void initialization()
    {
        imgeventimg=(ImageView)findViewById(R.id.imgeventimg);
        tvtitle=(TextView)findViewById(R.id.txttitle);
        tvdate=(TextView)findViewById(R.id.txtdate);
        tvlongdesc=(TextView)findViewById(R.id.txtlongdesc);
    }

    private void setValue()
    {
        try {
//            Glide.with(context).load(Event_IMG).into(imgeventimg);
            Picasso.with(context)
                    .load(Event_IMG)
                    .placeholder(R.drawable.app_logo)
                    .error(R.drawable.app_logo)
                    .into(imgeventimg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvtitle.setText(Event_Title);
        tvdate.setText(Event_Date);
        tvlongdesc.setText(Event_Desc);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            Intent intent=new Intent(context,EventsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(context,EventsActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

}