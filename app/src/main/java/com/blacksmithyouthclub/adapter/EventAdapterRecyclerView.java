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
import com.blacksmithyouthclub.model.Event_Pojo;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by pc on 7/22/2017.
 */

public class EventAdapterRecyclerView extends RecyclerView.Adapter<EventAdapterRecyclerView.MyViewHolder> {
    private ImageView Images_dis;
    private Context context;
    private ArrayList<Event_Pojo> Alldatas;
    private LayoutInflater inflater;
    private String TAG = EventAdapterRecyclerView.class.getSimpleName();

    public EventAdapterRecyclerView(Context context, ArrayList<Event_Pojo> alldatas) {
        this.context = context;
        Alldatas = alldatas;
        inflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        final ImageView ImgBG;
        final TextView Title, Event_Date, Event_Desc;

        public MyViewHolder(View itemView) {
            super(itemView);
            ImgBG = (ImageView) itemView.findViewById(R.id.imgeventbg);
            Title = (TextView) itemView.findViewById(R.id.tveventtitle);
            Event_Date = (TextView) itemView.findViewById(R.id.tveventdate);
            Event_Desc = (TextView) itemView.findViewById(R.id.tveventdesc);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_single_event, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Event_Pojo SP = Alldatas.get(position);

        try {
            Log.d(TAG , "Event ImageURL : "+SP.getEventImg());
            if (!SP.getEventImg().isEmpty()) {

                holder.ImgBG.setVisibility(View.VISIBLE);
                //Glide.with(context).load(SP.getEventImg()).error(R.mipmap.ic_launcher).into(holder.ImgBG);
                Picasso.with(context)
                        .load(SP.getEventImg())
                        .placeholder(R.drawable.app_logo)
                        .error(R.drawable.app_logo)
                        .into(holder.ImgBG);
                holder.Event_Desc.setVisibility(View.GONE);
            } else {
                holder.ImgBG.setVisibility(View.GONE);
                holder.Event_Desc.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.Title.setText(SP.getEventName());
        holder.Event_Date.setText(SP.getEventDate());
        holder.Event_Desc.setText(SP.getEventDesc());



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