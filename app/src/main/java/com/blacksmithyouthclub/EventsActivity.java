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
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


import com.blacksmithyouthclub.adapter.EventAdapterRecyclerView;
import com.blacksmithyouthclub.helper.Allkeys;
import com.blacksmithyouthclub.helper.NetConnectivity;
import com.blacksmithyouthclub.model.Event_Pojo;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import dmax.dialog.SpotsDialog;

public class EventsActivity extends AppCompatActivity {
    private CoordinatorLayout coordinatorLayout;
    private Context context=this;
    private TextView tvNoDataFound;
    private RecyclerView rvEvent;
    private SpotsDialog pDialog;

    SyncHttpClient syncHttpClient=new SyncHttpClient();	//Declaration on top of the class

    ArrayList<Event_Pojo> Event_List=new ArrayList<Event_Pojo>();

    //EventAdapterRecyclerView

    EventAdapterRecyclerView Event_Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinateLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setTitle("Events Data");

        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initialization();

        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        rvEvent.setLayoutManager(mLayoutManager);
        rvEvent.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(4), true));
        rvEvent.setItemAnimator(new DefaultItemAnimator());

        if (NetConnectivity.isOnline(context))
        {
            String EV_URL= Allkeys.WEBSITE+"getAllEvents?type=events";
            Log.d("Events Url",EV_URL);
            new GetEventFromServer().execute(EV_URL);
        }
        else
        {
            checkInternet();
        }

        rvEvent.addOnItemTouchListener(new RecyclerTouchListener(this,rvEvent, new ClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view, int position) {

                if (NetConnectivity.isOnline(context))
                {
                    String Event_IMG= Event_List.get(position).getEventImg();
                    String Event_Title=Event_List.get(position).getEventName();
                    String Event_Date=Event_List.get(position).getEventDate();
                    String Event_Desc=Event_List.get(position).getEventDesc();

                    Intent intent=new Intent(context,SingleEventActivity.class);
                    intent.putExtra("Event_IMG",Event_IMG);
                    intent.putExtra("Event_Title",Event_Title);
                    intent.putExtra("Event_Date",Event_Date);
                    intent.putExtra("Event_Desc",Event_Desc);
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
        rvEvent=(RecyclerView)findViewById(R.id.RecyclerViewEvent);
        tvNoDataFound=(TextView)findViewById(R.id.txteventerror);
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

    public class GetEventFromServer extends AsyncTask<String,Void,Void> {

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
            Event_List.clear();
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
                                        String EventId=c.getString("id");
                                        String EventName=c.getString("eventName");
                                        String EventDate=c.getString("eventDate");
                                        String EventDesc=c.getString("description");
                                        String EventImg=c.getString("eventImg");
                                        //String EventTime=c.getString("eventTime");
                                        Event_Pojo VCP=new Event_Pojo(EventId,EventName,EventDate,EventDesc,EventImg);
                                        Event_List.add(VCP);
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
            if (Event_List.size()>0)
            {
                rvEvent.setVisibility(View.VISIBLE);
                tvNoDataFound.setVisibility(View.GONE);
                Event_Adapter = new EventAdapterRecyclerView(context, Event_List);
                rvEvent.setAdapter(Event_Adapter);
            }
            else
            {
                rvEvent.setVisibility(View.GONE);
                tvNoDataFound.setVisibility(View.VISIBLE);
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
                            Intent intent = new Intent(context, EventsActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                        } else {
                            tvNoDataFound.setVisibility(View.VISIBLE);
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

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private EventsActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final EventsActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
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