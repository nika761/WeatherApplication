package com.example.weatherapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.model.ListItems;

import java.util.List;

public class WeeklyForecastAdapter extends RecyclerView.Adapter<WeeklyForecastAdapter.WeeklyViewHolder> {
    private List<ListItems> listItems;
    private Context contextWeekly;

    public WeeklyForecastAdapter(Context context) {
        contextWeekly = context;
    }

    @NonNull
    @Override
    public WeeklyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.forecast_item, parent, false);
        return new WeeklyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull WeeklyViewHolder holder, int position) {
        holder.date.setText(listItems.get(position).getDt_txt().split(" ")[0]);
        holder.currentHour.setText(listItems.get(position).getDt_txt().split(" ")[1].substring(0, 5) + "");
        holder.temperature.setText(listItems.get(position).getMain().getTemp() + " C\u00B0");
        holder.precipitation.setText(listItems.get(position).getMain().getHumidity() + "%");
        String weatherIconID = listItems.get(position).getWeather().get(0).getIcon();
        Glide.with(contextWeekly)
                .load("http://openweathermap.org/img/wn/" + weatherIconID + "@2x.png")
                .into(holder.weatherImage);
        Animation animation = AnimationUtils.loadAnimation(contextWeekly, R.anim.animation_item);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public void setListItems(List<ListItems> getForecastListItems) {
        listItems = getForecastListItems;
        notifyDataSetChanged();
    }

    class WeeklyViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView temperature;
        TextView precipitation;
        ImageView weatherImage;
        TextView currentHour;

        public WeeklyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.recycler_date);
            temperature = itemView.findViewById(R.id.recycler_temperature);
            precipitation = itemView.findViewById(R.id.recycler_precipitation_percent);
            weatherImage = itemView.findViewById(R.id.recycler_weather_image);
            currentHour = itemView.findViewById(R.id.first_hour_recycler);
        }
    }
}
