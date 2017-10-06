package com.blacksmithyouthclub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.blacksmithyouthclub.helper.CommonMethods;
import com.blacksmithyouthclub.session.SessionManager;
import com.crashlytics.android.answers.LoginEvent;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectCasteActivity extends AppCompatActivity {


    @BindView(R.id.btnLuhar)
    Button btnLuhar;

    @BindView(R.id.btnSuthar)
    Button btnSuthar;
    private Context context =this;
    private SessionManager sessionManager;
    private HashMap<String, String> userDetails= new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_caste);

        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
        }


        setTitle(getString(R.string.activity_select_caste));



        sessionManager = new SessionManager(context);
        userDetails = sessionManager.getSessionDetails();




        btnLuhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sessionManager.setCaste(CommonMethods.KEY_LUHAR);
                Intent intent = new Intent(context, SignInActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });


        btnSuthar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionManager.setCaste(CommonMethods.KEY_SUTHAR);

                Intent intent = new Intent(context, SignInActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            Intent intent=new Intent(context,SelectLanguageActivity.class);
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
        Intent intent=new Intent(context,SelectLanguageActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }



}
