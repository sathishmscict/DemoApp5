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
import com.blacksmithyouthclub.model.Advertiesment_Pojo;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by pc on 7/22/2017.
 */

public class AdvertiesmentAdapterRecyclerView extends RecyclerView.Adapter<AdvertiesmentAdapterRecyclerView.MyViewHolder> {
    private ImageView Images_dis;
    private Context context;
    private ArrayList<Advertiesment_Pojo> Alldatas;
    private LayoutInflater inflater;

    public AdvertiesmentAdapterRecyclerView(Context context, ArrayList<Advertiesment_Pojo> alldatas) {
        this.context = context;
        Alldatas = alldatas;
        inflater=LayoutInflater.from(context);
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder
    {
        final ImageView AdverImgBG;
        final TextView AdverTitle,AdverDesc;
        public MyViewHolder(View itemView) {
            super(itemView);
            AdverImgBG=(ImageView)itemView.findViewById(R.id.imgadverbg);
            AdverTitle=(TextView)itemView.findViewById(R.id.tvadvertitle);
            AdverDesc=(TextView)itemView.findViewById(R.id.tvadverdesc);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.row_single_advertiesment,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Advertiesment_Pojo SP=Alldatas.get(position);

        try {
            Glide.with(context).load(SP.getAdverImg()).error(R.mipmap.ic_launcher).into(holder.AdverImgBG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.AdverTitle.setText(SP.getAdverName());
        holder.AdverDesc.setText(SP.getAdverDesc());
        holder.AdverDesc.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return Alldatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}