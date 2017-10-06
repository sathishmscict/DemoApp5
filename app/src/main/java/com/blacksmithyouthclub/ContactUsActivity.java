package com.blacksmithyouthclub;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.blacksmithyouthclub.helper.Allkeys;
import com.blacksmithyouthclub.helper.NetConnectivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import dmax.dialog.SpotsDialog;

public class ContactUsActivity extends AppCompatActivity implements OnMapReadyCallback {
    /*@BindView(R.id.toolbar)
    Toolbar toolbar;*/
    private static final int MY_PERMISSIONS_REQUEST_MAP = 121;

    private CoordinatorLayout coordinatorLayout;
    private Context context=this;
    private SpotsDialog pDialog;
    SyncHttpClient syncHttpClient=new SyncHttpClient();	//Declaration on top of the class
    private EditText edtaddress,edtcontactno,edtemail;
    private double lat,lon;
    private GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        //ButterKnife.bind(this);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinateLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pDialog = new SpotsDialog(context);
        pDialog.setCancelable(false);

        setTitle("Contact Us");

        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initialization();

        if (NetConnectivity.isOnline(context))
        {
            String ADVER_URL= Allkeys.WEBSITE+"getContactUsDetails?type=contactus";
            Log.d("ContactUS Url",ADVER_URL);
            new GetContactusFromServer().execute(ADVER_URL);
        }
        else
        {
            checkInternet();
        }
    }

    private void initialization()
    {
        edtaddress=(EditText)findViewById(R.id.edtaddress);
        edtcontactno=(EditText)findViewById(R.id.edtcontactno);
        edtemail=(EditText)findViewById(R.id.edtemail);

    }

    private void ShowDialog()
    {
        try {

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // create marker
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(lat, lon)).title(
                getString(R.string.app_name));

        // Changing marker icon
        MarkerOptions icon = marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        // adding marker

        LatLng latLng = new LatLng(lat, lon);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                latLng, 15);
        googleMap.animateCamera(cameraUpdate);
        // locationManager.removeUpdates(this);

        googleMap.addMarker(marker);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(ContactUsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_MAP);
        } else {
            googleMap.setMyLocationEnabled(true);
        }
    }

    public class GetContactusFromServer extends AsyncTask<String,Void,Void> {

        private String msg;
        private boolean Error_Status,Records;
        private String Usermobile="",UserName="",UserAddress="",UserEmail="",lat1="",lon1="";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ShowDialog();
        }

        @Override
        protected Void doInBackground(String... strings) {
            syncHttpClient.setResponseTimeout(30000);
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
                                        UserName=c.getString("contactPerson");
                                        UserAddress=c.getString("address");
                                        UserEmail=c.getString("email");
                                        lat1 = c.getString("latitude");
                                        lon1 = c.getString("longitude");
                                        Usermobile = c.getString("mobile");

                                        /*sessionManager.createContactUsDetails(
                                                "" + c.getString("Latitude"),
                                                "" + c.getString("Longitude"),
                                                "" + c.getString("Address")
                                                        + mobile
                                                        + phone
                                                        + ",,Email : "
                                                        + c.getString("Email"));*/
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
            DismissDialog();
            try {
                if (!UserAddress.equals(""))
                {
                    edtaddress.setText(UserAddress);
                }
                else
                {
                    edtaddress.setVisibility(View.GONE);
                }
                if (!UserEmail.equals(""))
                {
                    edtemail.setText(UserEmail);
                }
                else
                {
                    edtemail.setVisibility(View.GONE);
                }
                if (!Usermobile.equals(""))
                {
                    edtcontactno.setText(Usermobile);
                }
                else
                {
                    edtcontactno.setVisibility(View.GONE);
                }
                lat=Double.parseDouble(lat1);
                lon=Double.parseDouble(lon1);

                setmap();
                initilizeMap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void setmap()
    {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initilizeMap() {
        try {


            // Changing marker icon
            // marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.my_marker_icon)));

            // adding marker
            if (googleMap == null) {
            /*    googleMap = ((MapFragment)getFragmentManager()
                        .findFragmentById(R.id.map));*/

                //latitude =21.231212;
                //longitude=72.324231;
                // create marker
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(lat, lon)).title(
                        getString(R.string.app_name));

                // Changing marker icon
                MarkerOptions icon = marker.icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                // adding marker

                LatLng latLng = new LatLng(lat, lon);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        latLng, 15);
                googleMap.animateCamera(cameraUpdate);
                // locationManager.removeUpdates(this);

                googleMap.addMarker(marker);
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(ContactUsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_MAP);


                } else {


                    googleMap.setMyLocationEnabled(true);


                }




                // check if map is created successfully or not
                if (googleMap == null) {
                    Toast.makeText(context,
                            "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        } catch (Exception e) {
            System.out.print("Error :" + e.getMessage());
        }

    }
    public void checkInternet()
    {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "" + getString(R.string.no_network2), Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            Intent intent=new Intent(context,DashBoardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(context,DashBoardActivity.class);
        startActivity(intent);
        finish();
    }
}