package com.sisfo.practicumfinale.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sisfo.practicumfinale.R;
import com.sisfo.practicumfinale.models.Genre;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {
    private List<Genre> genres;

    public GenreAdapter(List<Genre> genres) {
        this.genres = genres;
    }


    @NonNull
    @Override
    public GenreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genre, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreAdapter.ViewHolder holder, int position) {
        holder.bind(genres.get(position));
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvGenre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGenre = itemView.findViewById(R.id.tv_name);

        }

        public void bind(Genre genre) {
            tvGenre.setText(genre.getName());

        }
    }
}
