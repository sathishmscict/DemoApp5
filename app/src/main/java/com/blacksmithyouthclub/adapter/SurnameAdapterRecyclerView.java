package com.blacksmithyouthclub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blacksmithyouthclub.R;
import com.blacksmithyouthclub.model.Advertiesment_Pojo;
import com.blacksmithyouthclub.model.SurnamesData;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 7/22/2017.
 */

public class SurnameAdapterRecyclerView extends RecyclerView.Adapter<SurnameAdapterRecyclerView.MyViewHolder> {
    private ImageView Images_dis;
    private Context context;
    private List<SurnamesData.DATum> list_surnameData;
    private LayoutInflater inflater;

    public SurnameAdapterRecyclerView(Context context, List<SurnamesData.DATum> alldatas) {
        this.context = context;
        list_surnameData = alldatas;
        inflater=LayoutInflater.from(context);
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder
    {

        final TextView tvSurname ;
        private final CardView cvSurname;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvSurname=(TextView) itemView.findViewById(R.id.tvSurname);
            cvSurname = (CardView)itemView.findViewById(R.id.cvSurname);


        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.row_single_surname,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SurnamesData.DATum SD = list_surnameData.get(position);


        holder.tvSurname.setText(SD.getSurname());

        if(position%2 == 0)
        {
            holder.cvSurname.setCardBackgroundColor(Color.parseColor("#3F51B5"));


        }
        else
        {
            holder.cvSurname.setCardBackgroundColor(Color.parseColor("#3F51B5"));

        }




    }

    @Override
    public int getItemCount() {
        return list_surnameData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}