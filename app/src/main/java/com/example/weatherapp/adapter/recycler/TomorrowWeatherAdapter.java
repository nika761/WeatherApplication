package com.example.weatherapp.adapter.recycler;

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

public class TomorrowWeatherAdapter extends RecyclerView.Adapter<TomorrowWeatherAdapter.ForecastViewHolder> {
    private List<ListItems> listItems;

    public TomorrowWeatherAdapter(List<ListItems> listItems) {
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ForecastViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tomorrow_weather, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        holder.date.setText(listItems.get(position).getDt_txt().split(" ")[0]);
        holder.currentHour.setText(listItems.get(position).getDt_txt().split(" ")[1].substring(0, 5) + "");
        holder.temperature.setText(listItems.get(position).getMain().getTemp() + " C\u00B0");
        String weatherIconID = listItems.get(position).getWeather().get(0).getIcon();
        Glide.with(holder.temperature.getContext())
                .load("http://openweathermap.org/img/wn/" + weatherIconID + "@2x.png")
                .into(holder.weatherImage);

        Animation animation = AnimationUtils.loadAnimation(holder.temperature.getContext(), R.anim.animation_recycler_item);
        holder.itemView.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView date, temperature, currentHour;
        ImageView weatherImage;

        ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.recycler_date);
            temperature = itemView.findViewById(R.id.recycler_temperature);
            weatherImage = itemView.findViewById(R.id.recycler_weather_image);
            currentHour = itemView.findViewById(R.id.first_hour_recycler);
        }
    }

}
