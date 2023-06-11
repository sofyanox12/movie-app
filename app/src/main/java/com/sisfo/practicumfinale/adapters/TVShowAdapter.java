package com.sisfo.practicumfinale.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sisfo.practicumfinale.databinding.ItemMediaBinding;
import com.sisfo.practicumfinale.models.TVShow;

import java.util.ArrayList;
import java.util.List;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.ViewHolder>{
    private List<TVShow> tvShows;
    private ClickListener listener;
    public TVShowAdapter() {
        this.tvShows = new ArrayList<>();
    }
    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }

    public void addAll(List<TVShow> data) {
        tvShows.addAll(data);
    }

    @NonNull
    @Override
    public TVShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMediaBinding binding = ItemMediaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowAdapter.ViewHolder holder, int position) {
        holder.onBind(tvShows.get(position));
        holder.itemView.setOnClickListener(v -> listener.onClick(tvShows.get(position)));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMediaBinding binding;
        public ViewHolder(@NonNull ItemMediaBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void onBind(TVShow tvShow) {
            binding.tvTitle.setText(tvShow.getName());
            if (tvShow.getFirstAirDate() != null)
                if (tvShow.getFirstAirDate().length() > 4)
                    binding.tvReleaseDate.setText(tvShow.getFirstAirDate().substring(0, 4));

            if (tvShow.getPosterPath() != null)
                Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500" + tvShow.getPosterPath())
                    .into(binding.ivPreview);
        }
    }

    public interface ClickListener {
        void onClick(TVShow tvShow);
    }
}
