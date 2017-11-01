package com.blacksmithyouthclub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blacksmithyouthclub.R;
import com.blacksmithyouthclub.model.BusinessSubCategoryData;

import java.util.List;

/**
 * Created by pc on 7/22/2017.
 */

public class BusinessSubCategoryAdapterRecyclerView extends RecyclerView.Adapter<BusinessSubCategoryAdapterRecyclerView.MyViewHolder> {
    private ImageView Images_dis;
    private Context context;
    private List<BusinessSubCategoryData.DATum> list_BusinessData;
    private LayoutInflater inflater;
    private String TAG = BusinessSubCategoryAdapterRecyclerView.class.getSimpleName();

    public BusinessSubCategoryAdapterRecyclerView(Context context, List<BusinessSubCategoryData.DATum> alldatas) {
        this.context = context;
        list_BusinessData = alldatas;
        inflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView tvCategory, tvCategoryCount;


        public MyViewHolder(View itemView) {
            super(itemView);

            tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            tvCategoryCount = (TextView) itemView.findViewById(R.id.tvCategoryCount);


        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_single_business_data, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final BusinessSubCategoryData.DATum BD = list_BusinessData.get(position);


        holder.tvCategory.setText(BD.getBusinessCategoryName());
        holder.tvCategoryCount.setText(BD.getCount().toString());


    }

    @Override
    public int getItemCount() {

        return list_BusinessData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}