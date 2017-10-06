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
import com.blacksmithyouthclub.model.Video_Pojo;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by pc on 7/22/2017.
 */

public class VideoAdapterRecyclerView extends RecyclerView.Adapter<VideoAdapterRecyclerView.MyViewHolder> {
    private ImageView Images_dis;
    private Context context;
    private ArrayList<Video_Pojo> Alldatas;
    private LayoutInflater inflater;

    public VideoAdapterRecyclerView(Context context, ArrayList<Video_Pojo> alldatas) {
        this.context = context;
        Alldatas = alldatas;
        inflater=LayoutInflater.from(context);
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder
    {
        final ImageView VideoImgBG;
        final TextView VideoTitle;
        public MyViewHolder(View itemView) {
            super(itemView);
            VideoImgBG=(ImageView)itemView.findViewById(R.id.imgVideobg);
            VideoTitle=(TextView)itemView.findViewById(R.id.tvvideotitle);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.row_single_video,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Video_Pojo SP=Alldatas.get(position);

        try {
            Glide.with(context).load(SP.getVideoImg()).error(R.mipmap.ic_launcher).into(holder.VideoImgBG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.VideoTitle.setText(SP.getVideoName());
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