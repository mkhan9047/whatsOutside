package com.example.mujahid.whatsoutside.CustomAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mujahid.whatsoutside.MulitForcastModel.DataList;
import com.example.mujahid.whatsoutside.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mujahid on 2/11/2018.
 */

public class Horizantal_recycle  extends RecyclerView.Adapter<Horizantal_recycle.MyViewHolder> {

    private List<DataList> list;
    private Context context;
    String dayKeep;
    public Horizantal_recycle(List<DataList> list, Context con){
        context = con;
        this.list = list;
    }
    @Override
    public Horizantal_recycle.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizantal_recycle,parent,false);
        return new Horizantal_recycle.MyViewHolder(view);
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(Horizantal_recycle.MyViewHolder holder, int position) {
        holder.temp.setText(String.format("%s\u00B0", String.format("%d", Math.round(list.get(position).getDay().getDay()-273.15))));

        Picasso.with(context)
                .load(String.format("http://openweathermap.org/img/w/%s.png",list.get(position).getWeather().get(0).getIcon()))
                .into(holder.imageView);
        Date date = new Date(list.get(position).getDt()*1000L);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("EEE");

        holder.day.setText(format.format(date));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView day, temp, hum, cloud;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            day = itemView.findViewById(R.id.day);
            temp = itemView.findViewById(R.id.temp);

        }
    }
}
