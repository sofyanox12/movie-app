package com.sisfo.practicumfinale.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sisfo.practicumfinale.databinding.ItemMediaBinding;
import com.sisfo.practicumfinale.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<Movie> movies;
    private ClickListener listener;

    public MovieAdapter() {
        this.movies = new ArrayList<>();
    }

    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }
    public void addAll(List<Movie> data) {
        movies.addAll(data);
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMediaBinding binding = ItemMediaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        holder.onBind(movies.get(position));
        holder.itemView.setOnClickListener(v -> listener.onClick(movies.get(position)));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMediaBinding binding;
        public ViewHolder(@NonNull ItemMediaBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void onBind(Movie movie) {
            binding.tvTitle.setText(movie.getTitle());
            if (movie.getReleaseDate() != null)
                if (movie.getReleaseDate().length() > 4)
                    binding.tvReleaseDate.setText(movie.getReleaseDate().substring(0, 4));

            if (movie.getPosterPath() != null)
                Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                    .into(binding.ivPreview);
        }
    }

    public interface ClickListener {
        void onClick(Movie movie);
    }
}
