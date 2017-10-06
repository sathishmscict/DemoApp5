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

public class SingleAdvertisementActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private Context context=this;
    private ImageView imgadverimg;
    private TextView tvtitle,tvlongdesc;
    private String Adver_IMG,Adver_Title,Adver_Desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_advertisement);
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
        Adver_IMG=intent.getStringExtra("Adver_IMG");
        Adver_Title=intent.getStringExtra("Adver_Name");
        Adver_Desc=intent.getStringExtra("Adver_Desc");

        setTitle(""+Adver_Title);

        setValue();
    }

    private void initialization()
    {
        imgadverimg=(ImageView)findViewById(R.id.imgadverimg);
        tvtitle=(TextView)findViewById(R.id.tvtitle);
        tvlongdesc=(TextView)findViewById(R.id.tvlongdesc);
    }
    private void setValue()
    {
        try {
            Glide.with(context).load(Adver_IMG).into(imgadverimg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvtitle.setText(Adver_Title);
        tvlongdesc.setText(Adver_Desc);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            Intent intent=new Intent(context,AdvertisementActivity.class);
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
        Intent intent=new Intent(context,AdvertisementActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}