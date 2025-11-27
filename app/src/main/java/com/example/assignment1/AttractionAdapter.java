package com.example.assignment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.ViewHolder> {

    private List<Attraction> attractions;
    private Context context;
    private OnAttractionClickListener listener;

    public interface OnAttractionClickListener {
        void onAttractionClick(Attraction attraction);
    }

    public AttractionAdapter(List<Attraction> attractions, Context context, OnAttractionClickListener listener) {
        this.attractions = attractions;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_attraction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Attraction attraction = attractions.get(position);

        // Set basic information only
        holder.nameTV.setText(attraction.getName());
        holder.locationTV.setText(attraction.getLocation());
        holder.typeTV.setText(attraction.getType());
        holder.dateTV.setText(attraction.getVisitDate());
        holder.ratingTV.setText(getStars(attraction.getRating()));

        // Show/hide favorite indicator
        if (attraction.isFavorite()) {
            holder.favoriteIndicator.setVisibility(View.VISIBLE);
        } else {
            holder.favoriteIndicator.setVisibility(View.GONE);
        }

        // Make the entire item clickable
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAttractionClick(attraction);
            }
        });
    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

    public void updateList(List<Attraction> newList) {
        this.attractions = newList;
        notifyDataSetChanged();
    }

    private String getStars(int rating) {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < rating; i++) {
            stars.append("⭐");
        }
        return stars.toString();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV, locationTV, typeTV, dateTV, ratingTV, favoriteIndicator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.attraction_name);
            locationTV = itemView.findViewById(R.id.attraction_location);
            typeTV = itemView.findViewById(R.id.attraction_type);
            dateTV = itemView.findViewById(R.id.attraction_date);
            ratingTV = itemView.findViewById(R.id.attraction_rating);
            favoriteIndicator = itemView.findViewById(R.id.tv_favorite_indicator);
        }
    }
}