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
import com.blacksmithyouthclub.model.BussinessCategoryData;

import java.util.List;

/**
 * Created by pc on 7/22/2017.
 */

public class BusinessCategoryAdapterRecyclerView extends RecyclerView.Adapter<BusinessCategoryAdapterRecyclerView.MyViewHolder> {
    private ImageView Images_dis;
    private Context context;
    private List<BussinessCategoryData.DATum> list_BusinessData;
    private LayoutInflater inflater;
    private String TAG = BusinessCategoryAdapterRecyclerView.class.getSimpleName();

    public BusinessCategoryAdapterRecyclerView(Context context, List<BussinessCategoryData.DATum> alldatas) {
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

        final BussinessCategoryData.DATum BD = list_BusinessData.get(position);


        holder.tvCategory.setText(BD.getBusinessName());
        holder.tvCategoryCount.setText(BD.getSubcategorycount().toString());
        //holder.tvCategoryCount.setVisibility(View.GONE);


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