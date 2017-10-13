package com.blacksmithyouthclub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blacksmithyouthclub.helper.CommonMethods;
import com.blacksmithyouthclub.model.SearchData;
import com.blacksmithyouthclub.parcel.UserData;
import com.blacksmithyouthclub.session.SessionManager;
import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

public class SingleMemberDetailsDisplayActivity extends AppCompatActivity {


    @BindView(R.id.ivProfilePic)
    CircularImageView ivProfilePic;

    @BindView(R.id.edtFullName)
    EditText edtFullName;


    @BindView(R.id.edtBusinessSubcategory)
    EditText edtBusinessSubcategory;

    @BindView(R.id.edtBusinesscategory)
    EditText edtBusinesscategory;


    @BindView(R.id.edtMobile)
    EditText edtMobile;

    @BindView(R.id.edtAddress)
    EditText edtAddress;

    @BindView(R.id.edtVillage)
    EditText edtVillage;
    private String TAG = SingleMemberDetailsDisplayActivity.class.getSimpleName();
    private Context context = this;
    private UserData selectedUserData;
    private SessionManager sessionManager;
    private HashMap<String, String> userDetails = new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_member_details_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);


        setTitle("Member Data");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        sessionManager = new SessionManager(context);
        userDetails = sessionManager.getSessionDetails();


        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = getIntent();

    /*    try {
            Log.d(TAG, "IMAGE URL  : " +intent.getStringExtra(CommonMethods.KEY_AVATAR) );
            Glide.with(context).load(intent.getStringExtra(CommonMethods.KEY_AVATAR)).error(R.mipmap.ic_launcher).into(ivProfilePic);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/


        try {
            // To retrieve object in second Activity
            selectedUserData = (UserData) getIntent().getParcelableExtra(CommonMethods.MEMBER_DATA);


            // edtFullName.setText(intent.getStringExtra(CommonMethods.KEY_FIRST_NAME));
            edtFullName.setText(selectedUserData.getFirstName() + " " + selectedUserData.getFatherName());
        } catch (Exception e) {
            edtFullName.setText("");
            e.printStackTrace();
        }

        try {
            Log.d(TAG, "IMAGE URL  : " + intent.getStringExtra(CommonMethods.KEY_AVATAR));
            // Glide.with(context).load(MD.getAvatar()).error(R.mipmap.ic_launcher).into(holder.ivProfilePic);
            Picasso.with(context)
                    .load(selectedUserData.getAvatar())
                    .placeholder(R.drawable.app_logo)
                    .error(R.drawable.app_logo)
                    .into(ivProfilePic);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            //edtMobile.setText(intent.getStringExtra(CommonMethods.KEY_MOBILE));
            edtMobile.setText(selectedUserData.getMobile());

        } catch (Exception e) {
            e.printStackTrace();
            edtMobile.setText("");
        }

        //intent.getStringExtra(CommonMethods.KEY_AVATAR);


        try {
            //edtFatherName.setText(intent.getStringExtra(CommonMethods.KEY_FATHERNAME));
            edtBusinessSubcategory.setText(selectedUserData.getBusinesssubcategoryname());
        } catch (Exception e) {
            e.printStackTrace();
            edtBusinessSubcategory.setText("");
        }


        try {
            //edtFatherName.setText(intent.getStringExtra(CommonMethods.KEY_FATHERNAME));
            edtAddress.setText(selectedUserData.getAddress());
        } catch (Exception e) {
            e.printStackTrace();
            edtAddress.setText("");
        }


        try {
            //edtVillage.setText(intent.getStringExtra(CommonMethods.KEY_VILLAGE));
            edtVillage.setText(selectedUserData.getVillage());
        } catch (Exception e) {
            e.printStackTrace();
            edtVillage.setText("");

        }


        /*intent.putExtra(CommonMethods.KEY_FIRST_NAME,list_SearchData.get(position).getFirstName()+" "+list_SearchData.get(position).getOriginalSurname());
        intent.putExtra(CommonMethods.KEY_MOBILE,list_SearchData.get(position).getMobile());
        intent.putExtra(CommonMethods.KEY_AVATAR,list_SearchData.get(position).getAvatar());
        intent.putExtra(CommonMethods.KEY_FATHERNAME,list_SearchData.get(position).getFatherName());
        intent.putExtra(CommonMethods.KEY_VILLAGE , list_SearchData.get(position).getVillage());*/


        try {
            //edtMobile.setText(intent.getStringExtra(CommonMethods.KEY_MOBILE));
            edtBusinesscategory.setText(selectedUserData.getBusinesscategorytitle());

        } catch (Exception e) {
            e.printStackTrace();
            edtBusinesscategory.setText("");
        }


        //Toast.makeText(context, "Actname : "+getIntent().getStringExtra(CommonMethods.ACTIVITY_NAME).contains("BusinessSubCategoryActivity"), Toast.LENGTH_SHORT).show();
        if (getIntent().getStringExtra(CommonMethods.ACTIVITY_NAME).contains("MembersDataBySurnameActivity") || userDetails.get(SessionManager.KEY_SEARH_TYPE).toLowerCase().contains(CommonMethods.SEARCH_BUSINESS_SURNAME)) {
            edtBusinessSubcategory.setVisibility(View.GONE);
            edtBusinesscategory.setVisibility(View.GONE);
        } else if (userDetails.get(SessionManager.KEY_SEARH_TYPE).toLowerCase().contains("category")) {
            edtBusinessSubcategory.setVisibility(View.VISIBLE);
            edtBusinesscategory.setVisibility(View.VISIBLE);

        } else {
            edtBusinessSubcategory.setVisibility(View.VISIBLE);
            edtBusinesscategory.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            try {
                Log.d(TAG, context.getPackageName() + "." + getIntent().getStringExtra(CommonMethods.ACTIVITY_NAME));
                //Log.d(TAG, context.getPackageName() + "." + userDetails.get(SessionManager.KEY_ACTIVITY_NAME));
                Intent i = null;
                try {
                    i = new Intent(context, Class.forName(context.getPackageName() + "." + getIntent().getStringExtra(CommonMethods.ACTIVITY_NAME)));
                    i.putExtra("ActivityName", getIntent().getStringExtra(CommonMethods.ACTIVITY_NAME));
                } catch (ClassNotFoundException e) {
                    i = new Intent(context, DashBoardActivity.class);
                    e.printStackTrace();
                }
                //sessionmanager.setActivityName(TAG);
                startActivity(i);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        try {
            Log.d(TAG, context.getPackageName() + "." + getIntent().getStringExtra(CommonMethods.ACTIVITY_NAME));
            //Log.d(TAG, context.getPackageName() + "." + userDetails.get(SessionManager.KEY_ACTIVITY_NAME));
            Intent i = null;
            try {
                i = new Intent(context, Class.forName(context.getPackageName() + "." + getIntent().getStringExtra(CommonMethods.ACTIVITY_NAME)));
                //i.putExtra(CommonMethods.ACTIVITY_NAME, TAG);
            } catch (ClassNotFoundException e) {
                i = new Intent(context, DashBoardActivity.class);
                e.printStackTrace();
            }
            //sessionmanager.setActivityName(TAG);
            startActivity(i);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
