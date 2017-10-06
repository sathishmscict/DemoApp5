package com.blacksmithyouthclub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.blacksmithyouthclub.adapter.AdvertiesmentAdapterRecyclerView;
import com.blacksmithyouthclub.helper.Allkeys;
import com.blacksmithyouthclub.helper.CommonMethods;
import com.blacksmithyouthclub.helper.NetConnectivity;
import com.blacksmithyouthclub.model.Advertiesment_Pojo;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import dmax.dialog.SpotsDialog;

public class AdvertisementActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private Context context=this;
    private SpotsDialog pDialog;
    SyncHttpClient syncHttpClient=new SyncHttpClient();	//Declaration on top of the class
    private RecyclerView rvAdver;
    private TextView tvNoDataFoundAdver;
    AdvertiesmentAdapterRecyclerView Advertiesment_Adapter;
    //Advertiesment_Pojo
    ArrayList<Advertiesment_Pojo> Adver_List=new ArrayList<Advertiesment_Pojo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
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

        setTitle(getString(R.string.activity_advertisements));

        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        rvAdver.setLayoutManager(mLayoutManager);
        rvAdver.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(4), true));
        rvAdver.setItemAnimator(new DefaultItemAnimator());

        if (NetConnectivity.isOnline(context))
        {
            String ADVER_URL= Allkeys.WEBSITE+"getAllAdvertisement?type=advertisement";
            Log.d("Event Url",ADVER_URL);
            new GetAdvertisementFromServer().execute(ADVER_URL);
        }
        else
        {
            checkInternet();
        }
        rvAdver.addOnItemTouchListener(new EventsActivity.RecyclerTouchListener(this,rvAdver, new EventsActivity.ClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view, int position) {
                if (NetConnectivity.isOnline(context))
                {
                    String Adver_IMG=Adver_List.get(position).getAdverImg();
                    String Adver_Name=Adver_List.get(position).getAdverName();
                    String Adver_Desc=Adver_List.get(position).getAdverDesc();

                    Intent intent=new Intent(context,SingleAdvertisementActivity.class);
                    intent.putExtra("Adver_IMG",Adver_IMG);
                    intent.putExtra("Adver_Name",Adver_Name);
                    intent.putExtra("Adver_Desc",Adver_Desc);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    checkInternet();
                }
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }
        ));

    }

    private void initialization()
    {
        rvAdver=(RecyclerView)findViewById(R.id.RecyclerViewAdver);
        tvNoDataFoundAdver=(TextView)findViewById(R.id.txtadvererror);
    }

    private void ShowDialog()
    {
        try {
            pDialog = new SpotsDialog(context);
            pDialog.setCancelable(false);
            pDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DismissDialog()
    {
        try {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public class GetAdvertisementFromServer extends AsyncTask<String,Void,Void> {

        private String msg;
        private boolean Error_Status,Records;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ShowDialog();
        }

        @Override
        protected Void doInBackground(String... strings) {
            syncHttpClient.setResponseTimeout(30000);
            Adver_List.clear();
            syncHttpClient.get(strings[0],new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);

                    try {
                        if(response.getString("MESSAGE").equals("Success"))
                        {
                            JSONObject jonobj=new JSONObject(String.valueOf(response));
                            msg=jonobj.getString(Allkeys.TAG_MESSAGE);
                            String ORIGINAL_ERROR=jonobj.getString(Allkeys.TAG_ERROR_ORIGINAL);
                            Error_Status=jonobj.getBoolean(Allkeys.TAG_ERROR_STATUS);
                            Records=jonobj.getBoolean(Allkeys.TAG_IS_RECORDS);
                            if (Error_Status==false)
                            {
                                if (Records==true)
                                {
                                    JSONArray array=jonobj.getJSONArray("DATA");
                                    for (int i=0;i<array.length(); i++)
                                    {
                                        JSONObject c=array.getJSONObject(i);
                                        String AdverId=c.getString("advertisementId");
                                        String AdverName=c.getString("advertisementName");
                                        //String AdverDate=c.getString("advertisementCreatedDate");
                                        String AdverDesc=c.getString("advertisementDescription");
                                        String AdverImg=c.getString("advertisementImg");
                                        Advertiesment_Pojo ADP=new Advertiesment_Pojo(AdverId,AdverName,AdverDesc,AdverImg);
                                        Adver_List.add(ADP);
                                    }
                                }
                            }
                            Log.e("90check", "onSuccess: "+jonobj);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (Adver_List.size()>0)
            {
                rvAdver.setVisibility(View.VISIBLE);
                tvNoDataFoundAdver.setVisibility(View.GONE);
                Advertiesment_Adapter = new AdvertiesmentAdapterRecyclerView(context, Adver_List);
                rvAdver.setAdapter(Advertiesment_Adapter);
            }
            else
            {
                rvAdver.setVisibility(View.GONE);
                tvNoDataFoundAdver.setVisibility(View.VISIBLE);
            }
            DismissDialog();
        }
    }

    public void checkInternet() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "" + getString(R.string.no_network2), Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (NetConnectivity.isOnline(context)) {
                            Intent intent = new Intent(context, AdvertisementActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                        } else {
                            tvNoDataFoundAdver.setVisibility(View.VISIBLE);
                            Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "" + getString(R.string.no_network2), Snackbar.LENGTH_SHORT);
                            snackbar1.show();
                        }
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.YELLOW);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);

        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }


    //recycalview code
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
    //Complet Getting Category Details From Server



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