package com.blacksmithyouthclub;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Toast;


import com.blacksmithyouthclub.adapter.SurnameWiseMemberDataAdapterRecyclerView;
import com.blacksmithyouthclub.api.ApiClient;
import com.blacksmithyouthclub.api.ApiInterface;
import com.blacksmithyouthclub.databinding.ContentSearchBinding;
import com.blacksmithyouthclub.databinding.RowItemBinding;

import com.blacksmithyouthclub.helper.CommonMethods;
import com.blacksmithyouthclub.model.MembersDataBySurname;
import com.blacksmithyouthclub.model.SearchData;
import com.blacksmithyouthclub.session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchActivity extends AppCompatActivity {


    //Searching Related Keys
/*    public static final String TAG_SEARCH_ARRAY = "data";
    public static final String TAG_SEARCH_FLAG = "flag";
    public static final String TAG_SEARCH_MESSAGE = "message";
    public static final String TAG_SEARCH_ID = "id";
    public static final String TAG_SEARCH_PRODUCTID = "product_id";
    public static final String TAG_SEARCH_NAME = "name";
    public static final String TAG_SEARCH_CATEGORYID = "category_id";
    public static final String TAG_SEARCH_CATEGORYNAME = "category_name";
    public static final String TAG_SEARCH_CATEGORY_TYPE = "category_type";
    public static final String TAG_SEARCH_CATEGORY_TYPE_NAME = "category_type_name";
    public static final String TAG_SEARCH_IS_ACTIVE_SELLER = "is_active_seller";
    public static final String TAG_SEARCH_IS_ADMIN_ACTIVE = "is_active_admin";
    public static final String TAG_SEARCH_SKU = "sku";
    public static final String TAG_SEARCH_PRICE = "price";
    public static final String TAG_SEARCH_IS_PARENT = "is_parent";*/


    ContentSearchBinding activityMainBinding;
    ListAdapter adapter;

    List<String> arrayList = new ArrayList<>();
    private String TAG = SearchActivity.class.getSimpleName();
    private ArrayList<SearchData> list_SearchData = new ArrayList<SearchData>();
    private Context context = this;
    private SpotsDialog spotsDialog;
    private SessionManager sessionmanager;
    private HashMap<String, String> userDetails = new HashMap<String, String>();

    private ImageView ivBack;
    private String categortype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.content_search);

        activityMainBinding.search.setActivated(true);
        activityMainBinding.search.setQueryHint(getResources().getString(R.string.search_hint));
        activityMainBinding.search.onActionViewExpanded();
        activityMainBinding.search.setIconified(false);
        activityMainBinding.search.clearFocus();


        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
        activityMainBinding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                try {
                    getProductDataFromServer(query);

                    if (adapter != null) {

                        adapter.getFilter().filter(query);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                try {
                    getProductDataFromServer(newText);

                    if(adapter!=null)
                    {

                    adapter.getFilter().filter(newText);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return false;
            }
        });


        ;

        spotsDialog = new SpotsDialog(context);
        spotsDialog.setCancelable(false);

        sessionmanager = new SessionManager(context);
        userDetails = sessionmanager.getSessionDetails();


        ivBack = (ImageView) findViewById(R.id.ivBack);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intet = new Intent(context, DashBoardActivity.class);
                startActivity(intet);
                finish();
            }
        });


        // getProductDataFromServer("");


    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        try {
            getMenuInflater().inflate(R.menu.menu_search, menu);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }*/


    public static String convertToJsonFormat(String json_data) {

        String response = "{\"Maindata\":" + json_data + "]}";
        return response;

    }


    private void getProductDataFromServer(final String str) {


        CommonMethods.showDialog(spotsDialog);


        ApiInterface apiClient = ApiClient.getClient().create(ApiInterface.class);
        Log.d(TAG, "URL getDetailsBySearch : " + CommonMethods.WEBSITE + "getDetailsBySearch?type=search&search=" + str + "");
        apiClient.getDetailsBySearch("search", str).enqueue(new Callback<MembersDataBySurname>() {
            @Override
            public void onResponse(Call<MembersDataBySurname> call, Response<MembersDataBySurname> response) {


                Log.d(TAG, "getDetailsBySearch Response Code : " + response.code());

                if (response.code() == 200) {


                    String str_error = response.body().getMESSAGE();
                    String str_error_original = response.body().getORIGINALMESSAGE();
                    boolean error_status = response.body().getERRORSTATUS();
                    boolean record_status = response.body().getRECORDS();
                    list_SearchData.clear();
                    arrayList.clear();
                    if (error_status == false) {
                        if (record_status == true) {

                            List<MembersDataBySurname.DATum> arr = response.body().getDATA();


                            for (int i = 0; i < arr.size(); i++) {

                                arrayList.add(arr.get(i).getFirstName());

                                //SearchData(String userId, String firstName, String originalSurname, String village, String mobile, String address, String avatar, String fatherName)

                                SearchData sc = new SearchData(arr.get(i).getUserId().toString(), arr.get(i).getFirstName(), arr.get(i).getOriginalSurname(), arr.get(i).getVillage(), arr.get(i).getMobile(), arr.get(i).getAddress(), arr.get(i).getAvatar(), arr.get(i).getFatherName());

                                list_SearchData.add(sc);

                            }

                            adapter = new ListAdapter(context, arrayList);
                            activityMainBinding.listView.setAdapter(adapter);


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
            public void onFailure(Call<MembersDataBySurname> call, Throwable t) {

                CommonMethods.onFailure(context, TAG, t);

                CommonMethods.hideDialog(spotsDialog);
            }
        });


    }


    public class ListAdapter extends BaseAdapter implements Filterable {

        List<String> mData;
        List<String> mStringFilterList;
        ListAdapter.ValueFilter valueFilter;
        private LayoutInflater inflater;
        private Context context;


        public ListAdapter(Context context, List<String> cancel_type) {
            mData = cancel_type;
            mStringFilterList = cancel_type;
            this.context = context;
        }


        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public String getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {

            if (inflater == null) {
                inflater = (LayoutInflater) parent.getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            RowItemBinding rowItemBinding = DataBindingUtil.inflate(inflater, R.layout.row_item, parent, false);
            rowItemBinding.stringName.setText(mData.get(position));

            rowItemBinding.stringName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  Toast.makeText(context, "Selecte : " + list_SearchData.get(position).getSearch_product_id(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Data : " + mData.get(position) + list_SearchData.get(position).getUserId());

              /*      Intent intent = new Intent(context , SingleItemActivity.class);
                    startActivity(intent);
                    finish();*/

                    try {
                        //Toast.makeText(_context, "Name : " + pd.getProductname() + " Id : " + pd.getProductid(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Clicked On Name : " + list_SearchData.get(position).getFirstName() + " Id : " + list_SearchData.get(position).getUserId());
                        Intent intent = new Intent(context,

                                SingleMemberDetailsDisplayActivity.class);



                        intent.putExtra(CommonMethods.KEY_FIRST_NAME,list_SearchData.get(position).getFirstName()+" "+list_SearchData.get(position).getOriginalSurname());
                        intent.putExtra(CommonMethods.KEY_MOBILE,list_SearchData.get(position).getMobile());
                        intent.putExtra(CommonMethods.KEY_AVATAR,list_SearchData.get(position).getAvatar());
                        intent.putExtra(CommonMethods.KEY_FATHERNAME,list_SearchData.get(position).getFatherName());
                        intent.putExtra(CommonMethods.KEY_VILLAGE , list_SearchData.get(position).getVillage());




                        sessionmanager.setSearchUserDetails(list_SearchData.get(position).getUserId(), list_SearchData.get(position).getFirstName());


                        //  Toast.makeText(context, "Company id : "+list_SearchData.get(position).getCompnayid(), Toast.LENGTH_SHORT).show();
                        //sessionmanager.setCompnayTypeAndIdDetails(list_SearchData.get(position).getCompnayid(), dbhandler.getCompanyNameByCompnayId(Integer.parseInt(list_SearchData.get(position).getCompnayid())));


                        //sessionmanager.setCategoryTypeAndIdDetails(categoryType, categoryId, categoryName);


                        //intent.putExtra("ProductId", pd.getProductid());
                        intent.putExtra("ActivityName", TAG);

                        //sessionmanager.setActivityName(TAG);

                        //sessionmanager.setActivityName("");
                        context.startActivity(intent);
                        //finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });


            return rowItemBinding.getRoot();
        }

        @Override
        public Filter getFilter() {
            if (valueFilter == null) {
                valueFilter = new ValueFilter();
            }
            return valueFilter;
        }

        private class ValueFilter extends Filter {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint != null && constraint.length() > 0) {
                    List<String> filterList = new ArrayList<>();
                    for (int i = 0; i < mStringFilterList.size(); i++) {
                        if ((mStringFilterList.get(i).toUpperCase()).contains(constraint.toString().toUpperCase())) {
                            filterList.add(mStringFilterList.get(i));
                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                } else {
                    results.count = mStringFilterList.size();
                    results.values = mStringFilterList;
                }
                return results;

            }

            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                mData = (List<String>) results.values;
                notifyDataSetChanged();
            }

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            Intent i = new Intent(context, DashBoardActivity.class);
            startActivity(i);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(context, DashBoardActivity.class);
        startActivity(i);
        finish();
    }
}
