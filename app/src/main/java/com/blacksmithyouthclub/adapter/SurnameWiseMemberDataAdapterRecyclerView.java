package com.blacksmithyouthclub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blacksmithyouthclub.R;
import com.blacksmithyouthclub.model.MembersDataBySurname;
import com.blacksmithyouthclub.model.Video_Pojo;
import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.BubbleImageView;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 7/22/2017.
 */

public class SurnameWiseMemberDataAdapterRecyclerView extends RecyclerView.Adapter<SurnameWiseMemberDataAdapterRecyclerView.MyViewHolder> {
    private ImageView Images_dis;
    private Context context;
    private List<MembersDataBySurname.DATum> list_AllMembersData;
    private LayoutInflater inflater;
    private String TAG =SurnameWiseMemberDataAdapterRecyclerView.class.getSimpleName() ;

    public SurnameWiseMemberDataAdapterRecyclerView(Context context, List<MembersDataBySurname.DATum> alldatas) {
        this.context = context;
        list_AllMembersData = alldatas;
        inflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView tvMemberName, tvMemberMobile, tvVillage, tvAddress;

        private final CircularImageView ivProfilePic;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvMemberName = (TextView) itemView.findViewById(R.id.tvMemberName);
            tvMemberMobile = (TextView) itemView.findViewById(R.id.tvMemberMobile);
            tvVillage = (TextView) itemView.findViewById(R.id.tvVillage);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            ivProfilePic = (CircularImageView) itemView.findViewById(R.id.ivProfilePic);



        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_single_member_by_surname, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final MembersDataBySurname.DATum MD = list_AllMembersData.get(position);

        try {
            Log.d(TAG, "IMAGE URL  : " +MD.getAvatar() );
           // Glide.with(context).load(MD.getAvatar()).error(R.mipmap.ic_launcher).into(holder.ivProfilePic);
            Picasso.with(context)
                    .load(MD.getAvatar())
                    .placeholder(R.drawable.app_logo)
                    .error(R.drawable.app_logo)
                    .into(holder.ivProfilePic);
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.tvMemberName.setText(MD.getFirstName());
        holder.tvAddress.setText(MD.getAddress());
        holder.tvMemberMobile.setText(MD.getMobile());
        holder.tvVillage.setText(MD.getVillage());


    }

    @Override
    public int getItemCount() {
        return list_AllMembersData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}