package com.blacksmithyouthclub;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.blacksmithyouthclub.adapter.BusinessSubCategoryAdapterRecyclerView;
import com.blacksmithyouthclub.api.ApiClient;
import com.blacksmithyouthclub.api.ApiInterface;
import com.blacksmithyouthclub.helper.CommonMethods;
import com.blacksmithyouthclub.model.BusinessSubCategoryData;
import com.blacksmithyouthclub.session.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessSubCategoryActivity extends AppCompatActivity {


    @BindView(R.id.rvBusiness)
    RecyclerView rvBusiness;

    private Context context = this;
    private SessionManager sessionManager;
    private HashMap<String, String> userDetails = new HashMap<String, String>();
    private SpotsDialog spotsDialog;
    private String TAG = BusinessSubCategoryActivity.class.getSimpleName();
    private List<BusinessSubCategoryData.DATum> list_BusinessSubCategory = new ArrayList<BusinessSubCategoryData.DATum>();
    private BusinessSubCategoryAdapterRecyclerView adapter;
    private MenuItem cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_sub_category);


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

        sessionManager = new SessionManager(context);
        userDetails = sessionManager.getSessionDetails();

        sessionManager.setSearchType(CommonMethods.SEARCH_BUSINESS_SUBCATEGORY);

        setTitle(userDetails.get(SessionManager.KEY_SELECTED_BUSINESS_CATEGORY));
        spotsDialog = new SpotsDialog(context);
        spotsDialog.setCancelable(false);


        LinearLayoutManager lManager = new LinearLayoutManager(context);
        rvBusiness.setLayoutManager(lManager);


        rvBusiness.addOnItemTouchListener(new CommonMethods.RecyclerTouchListener(this, rvBusiness, new CommonMethods.ClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view, int position) {


                sessionManager.setSelectedBusinessSubCategoryDetails(String.valueOf(list_BusinessSubCategory.get(position).getId()), list_BusinessSubCategory.get(position).getBusinessCategoryName());

                if (list_BusinessSubCategory.get(position).getCount() == 0) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Data information");


                    builder.setMessage("Sorry, we have not found any members in  \"" + list_BusinessSubCategory.get(position).getBusinessCategoryName() + "\" category");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.cancel();
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();


                } else {
                    Intent intent = new Intent(context, MembersDataByBusinessActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                }


            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }
        ));

        getBusinessSubCategoryDataWithCountFromServer();


    }

    private void getBusinessSubCategoryDataWithCountFromServer() {

        CommonMethods.showDialog(spotsDialog);


        ApiInterface apiClient = ApiClient.getClient().create(ApiInterface.class);
        Log.d(TAG, "URL getAllBusinessSubCategory : " + CommonMethods.WEBSITE + "getAllBusinessSubCategory?type=businesscategory&businessid="+ Integer.parseInt(userDetails.get(SessionManager.KEY_SELECTED_BUSINESS_CATEGORY_ID)) +"");
        apiClient.getAllBusinessSubCategory("businesscategory", Integer.parseInt(userDetails.get(SessionManager.KEY_SELECTED_BUSINESS_CATEGORY_ID))).enqueue(new Callback<BusinessSubCategoryData>() {
            @Override
            public void onResponse(Call<BusinessSubCategoryData> call, Response<BusinessSubCategoryData> response) {


                Log.d(TAG, "getAllBusinessSubCategory Response Code : " + response.code());

                if (response.code() == 200) {


                    String str_error = response.body().getMESSAGE();
                    String str_error_original = response.body().getORIGINALMESSAGE();
                    boolean error_status = response.body().getERRORSTATUS();
                    boolean record_status = response.body().getRECORDS();

                    if (error_status == false) {
                        if (record_status == true) {

                            list_BusinessSubCategory = response.body().getDATA();


                            adapter = new BusinessSubCategoryAdapterRecyclerView(context, list_BusinessSubCategory);
                            rvBusiness.setAdapter(adapter);


                            CommonMethods.hideDialog(spotsDialog);


                        }
                    } else {

                        CommonMethods.hideDialog(spotsDialog);
                        Toast.makeText(context, "" + str_error, Toast.LENGTH_SHORT).show();
                    }


                } else {
                    CommonMethods.showErrorMessageWhenStatusNot200(context, response.code());
                }


                CommonMethods.hideDialog(spotsDialog);

            }

            @Override
            public void onFailure(Call<BusinessSubCategoryData> call, Throwable t) {

                CommonMethods.onFailure(context, TAG, t);

                CommonMethods.hideDialog(spotsDialog);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        try {
            getMenuInflater().inflate(R.menu.dash_board, menu);
            cart = (MenuItem) menu.findItem(R.id.menu_notification);
            cart.setVisible(false);

            MenuItem search = (MenuItem) menu.findItem(R.id.menu_search);


//int orderid=db.getMaxOrderId();


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(context, BusinessDirectoryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        } else if (item.getItemId() == R.id.menu_search) {


            sessionManager.setSearchType(CommonMethods.SEARCH_BUSINESS_SUBCATEGORY);
            Intent intent = new Intent(context, SearchActivity.class);
            intent.putExtra(CommonMethods.ACTIVITY_NAME , TAG);
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
        Intent intent = new Intent(context, BusinessDirectoryActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

}
