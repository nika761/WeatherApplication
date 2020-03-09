package com.example.weatherapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.weatherapp.R;
import com.example.weatherapp.model.ListItems;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    private List<ListItems> listItemsList;
    private Context contextActivity;

    public ForecastAdapter(Context context) {
        contextActivity = context;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.forecast_item, parent, false);
        return new ForecastViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        holder.date.setText(listItemsList.get(position).getDt_txt().split(" ")[0]);
        holder.currentHour.setText(listItemsList.get(position).getDt_txt().split(" ")[1].substring(0, 5) + "");
        holder.temperature.setText(listItemsList.get(position).getMain().getTemp() + " C\u00B0");
        holder.precipitation.setText(listItemsList.get(position).getMain().getHumidity() + "%");
        String weatherIconID = listItemsList.get(position).getWeather().get(0).getIcon();
        Glide.with(contextActivity)
                .load("http://openweathermap.org/img/wn/" + weatherIconID + "@2x.png")
                .into(holder.weatherImage);

        Animation animation = AnimationUtils.loadAnimation(contextActivity, R.anim.animation_recycler_item);
        holder.itemView.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return listItemsList.size();
    }

    public void setListItems(List<ListItems> getForecastListItems) {
        listItemsList = getForecastListItems;
        notifyDataSetChanged();
    }

    class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView date, temperature, precipitation, currentHour;
        ImageView weatherImage;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.recycler_date);
            temperature = itemView.findViewById(R.id.recycler_temperature);
            precipitation = itemView.findViewById(R.id.recycler_precipitation_percent);
            weatherImage = itemView.findViewById(R.id.recycler_weather_image);
            currentHour = itemView.findViewById(R.id.first_hour_recycler);
        }
    }

}
