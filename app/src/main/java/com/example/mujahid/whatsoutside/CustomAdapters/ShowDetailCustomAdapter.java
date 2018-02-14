package com.example.mujahid.whatsoutside.CustomAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mujahid.whatsoutside.Model.Wdata;
import com.example.mujahid.whatsoutside.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Mujahid on 2/14/2018.
 */

public class ShowDetailCustomAdapter extends RecyclerView.Adapter<ShowDetailCustomAdapter.MyDetailViewHolder> {
    Context context;
    List<Wdata> data;

    public ShowDetailCustomAdapter(Context context, List<Wdata> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_show_detail_recycle,parent,false);
        return new MyDetailViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(MyDetailViewHolder holder, int position) {
            holder.temp.setText(String.format("Temp %d",Math.round(data.get(position).getMain().getTemp()-273)));
            holder.pressure.setText(String.format("Pressure %.2f",data.get(position).getMain().getPressure()));
            holder.pressure.setText(String.format("Temp Max %d",Math.round(data.get(position).getMain().getTemp_max()-273)));
            holder.tm_min.setText(String.format("Temp Min %d",Math.round(data.get(position).getMain().getTemp_min()-273)));
            holder.hum.setText(String.format("Humadity %d",data.get(position).getMain().getHumidity()));
        Picasso.with(context)
                .load(String.format("http://openweathermap.org/img/w/%s.png",data.get(position).getWeather().get(0).getIcon()))
                .into(holder.imageView);
        holder.time.setText(getTime(data.get(position).getDt()));
        holder.time.setText(getDate(data.get(position).getDt()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyDetailViewHolder extends RecyclerView.ViewHolder{
        TextView temp, pressure, tm_max, tm_min, hum,time,date;
        ImageView imageView;
        public MyDetailViewHolder(View itemView) {
            super(itemView);
            temp = itemView.findViewById(R.id.temp);
            pressure = itemView.findViewById(R.id.pressure);
            tm_max = itemView.findViewById(R.id.tmp_max);
            tm_min = itemView.findViewById(R.id.tmp_min);
            hum = itemView.findViewById(R.id.humidity);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            imageView = itemView.findViewById(R.id.image);
        }
    }

    private String getTime(int unix){
        Date date = new Date(unix * 1000L);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        format.setTimeZone(TimeZone.getDefault());
        return format.format(date);
    }
    private String getDate(int unix){
        Date date = new Date(unix * 1000L);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("EEE, dd:MM");
        format.setTimeZone(TimeZone.getDefault());
        return format.format(date);
    }

}
