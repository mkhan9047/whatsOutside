package com.example.mujahid.whatsoutside.CustomAdapters;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mujahid.whatsoutside.MulitForcastModel.DataList;
import com.example.mujahid.whatsoutside.MulitForcastModel.GetWeather;
import com.example.mujahid.whatsoutside.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Callback;

/**
 * Created by Mujahid on 2/9/2018.
 */

public class MultiForcastRecycle extends RecyclerView.Adapter<MultiForcastRecycle.MyViewHolder> {

    private List<DataList> list;
    private Context context;
    String dayKeep;

    public MultiForcastRecycle(List<DataList> list, Context con){
        context = con;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycler,parent,false);
        return new MyViewHolder(view);
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.temp.setText(String.format("%s℃", String.format("%d", Math.round(list.get(position).getDay().getDay()-273.15))));
        holder.hum.setText(String.format("H %d",list.get(position).getHumidity()));
        holder.cloud.setText(String.format("C %d",list.get(position).getClouds()));
        Picasso.with(context)
                .load(String.format("http://openweathermap.org/img/w/%s.png",list.get(position).getWeather().get(0).getIcon()))
                .into(holder.imageView);
        Date date = new Date(list.get(position).getDt()*1000L);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("EEEE");
        holder.day.setText(format.format(date));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imageView;
        TextView day, temp, hum, cloud;

        public MyViewHolder(View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.icon);
            day = itemView.findViewById(R.id.day);
            temp = itemView.findViewById(R.id.temp);
            hum = itemView.findViewById(R.id.hum);
            cloud = itemView.findViewById(R.id.cloud);
        }
    }
}
