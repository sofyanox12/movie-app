package com.sisfo.practicumfinale.adapters;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sisfo.practicumfinale.R;
import com.sisfo.practicumfinale.data.local.Bookmark;
import com.sisfo.practicumfinale.databinding.ItemFavoritesBinding;
import com.sisfo.practicumfinale.utils.Media;

import java.util.Arrays;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private List<Bookmark> bookmarks;
    private ClickListener clickListener;

    public FavoriteAdapter(List<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
        notifyDataSetChanged();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void addBookmark(Bookmark bookmark) {
        this.bookmarks.add(bookmark);
        notifyItemInserted(this.bookmarks.size() - 1);
    }

    public void removeBookmark(int position) {
        this.bookmarks.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.bookmarks.size());
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFavoritesBinding binding = ItemFavoritesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        holder.onBind(bookmarks.get(position), position);
        holder.itemView.setOnClickListener(v -> {
            clickListener.onClick(bookmarks.get(position), position);
        });
    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemFavoritesBinding binding;
        public ViewHolder(@NonNull ItemFavoritesBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void onBind(Bookmark bookmark, int position) {
            String[] date = bookmark.getReleaseDate().split("-");
            String formattedDate = date[2] + " " + Media.getMonth(date[1]) + " " + date[0];
            binding.tvTitle.setText(bookmark.getTitle());
            binding.tvReleaseDate.setText(formattedDate);

            if (bookmark.getMediaType().equals("MOVIE")) {
                binding.ivType.setImageResource(R.drawable.round_movie_24);
            } else {
                binding.ivType.setImageResource(R.drawable.round_tv_24);
            }

            Glide.with(itemView.getContext())
                    .asBitmap()
                    .load(bookmark.getPosterPath())
                    .into(binding.ivPreview);
        }
    }

    public interface ClickListener {
        void onClick(Bookmark bookmark, int position);
    }
}
