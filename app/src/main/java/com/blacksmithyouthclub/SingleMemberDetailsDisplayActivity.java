package com.blacksmithyouthclub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.blacksmithyouthclub.helper.CommonMethods;
import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingleMemberDetailsDisplayActivity extends AppCompatActivity {


    @BindView(R.id.ivProfilePic)
    CircularImageView ivProfilePic;

    @BindView(R.id.edtFullName)
    EditText edtFullName;


    @BindView(R.id.edtFatherName)
    EditText edtFatherName;

    @BindView(R.id.edtMobile)
    EditText edtMobile;

    @BindView(R.id.edtAddress)
    EditText edtAddress;

    @BindView(R.id.edtVillage)
    EditText edtVillage;
    private String TAG = SingleMemberDetailsDisplayActivity.class.getSimpleName();
    private Context context =this;


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



        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent=getIntent();

    /*    try {
            Log.d(TAG, "IMAGE URL  : " +intent.getStringExtra(CommonMethods.KEY_AVATAR) );
            Glide.with(context).load(intent.getStringExtra(CommonMethods.KEY_AVATAR)).error(R.mipmap.ic_launcher).into(ivProfilePic);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/

        try {
            Log.d(TAG, "IMAGE URL  : " +intent.getStringExtra(CommonMethods.KEY_AVATAR) );
            // Glide.with(context).load(MD.getAvatar()).error(R.mipmap.ic_launcher).into(holder.ivProfilePic);
            Picasso.with(context)
                    .load(intent.getStringExtra(CommonMethods.KEY_AVATAR))
                    .placeholder(R.drawable.app_logo)
                    .error(R.drawable.app_logo)
                    .into(ivProfilePic);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            edtFullName.setText(intent.getStringExtra(CommonMethods.KEY_FIRST_NAME));
        } catch (Exception e) {
            edtFullName.setText("");
            e.printStackTrace();
        }
        try {
            edtMobile.setText(intent.getStringExtra(CommonMethods.KEY_MOBILE));
        } catch (Exception e) {
            e.printStackTrace();
            edtMobile.setText("");
        }

        //intent.getStringExtra(CommonMethods.KEY_AVATAR);


        try {
            edtFatherName.setText(intent.getStringExtra(CommonMethods.KEY_FATHERNAME));
        } catch (Exception e) {
            e.printStackTrace();
            edtFatherName.setText("");
        }


        try {
            edtVillage.setText(intent.getStringExtra(CommonMethods.KEY_VILLAGE));
        } catch (Exception e) {
            e.printStackTrace();
            edtVillage.setText("");

        }


        /*intent.putExtra(CommonMethods.KEY_FIRST_NAME,list_SearchData.get(position).getFirstName()+" "+list_SearchData.get(position).getOriginalSurname());
        intent.putExtra(CommonMethods.KEY_MOBILE,list_SearchData.get(position).getMobile());
        intent.putExtra(CommonMethods.KEY_AVATAR,list_SearchData.get(position).getAvatar());
        intent.putExtra(CommonMethods.KEY_FATHERNAME,list_SearchData.get(position).getFatherName());
        intent.putExtra(CommonMethods.KEY_VILLAGE , list_SearchData.get(position).getVillage());*/










    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            Intent intent=new Intent(context,DashBoardActivity.class);
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
        Intent intent=new Intent(context,DashBoardActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }


}
