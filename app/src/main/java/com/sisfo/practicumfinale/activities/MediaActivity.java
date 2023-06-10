package com.sisfo.practicumfinale.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.sisfo.practicumfinale.R;
import com.sisfo.practicumfinale.adapters.GenreAdapter;
import com.sisfo.practicumfinale.databinding.ActivityMediaBinding;
import com.sisfo.practicumfinale.models.Genre;
import com.sisfo.practicumfinale.models.Movie;
import com.sisfo.practicumfinale.models.TVShow;
import com.sisfo.practicumfinale.utils.Media;

import java.util.ArrayList;
import java.util.List;

public class MediaActivity extends AppCompatActivity {
    private ActivityMediaBinding binding;
    private Movie movie;
    private TVShow tvShow;
    private boolean isBookmarked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMediaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        takeMedia(this.movie, this.tvShow);

        binding.btnBack.setOnClickListener(v -> finish());
        binding.btnBookmark.setOnClickListener(v -> {
            if (isBookmarked) {
                binding.btnBookmark.setImageResource(R.drawable.round_bookmark_border_24);
                isBookmarked = false;
            } else {
                binding.lavTap.playAnimation();
                binding.btnBookmark.setImageResource(R.drawable.round_bookmark_24);
                isBookmarked = true;
            }
        });


    }

    private void setMovie(Movie movie) {
        List<Genre> genres = getGenre(getResources().getStringArray(R.array.movie), movie.getGenreIDs());
        String[] date = movie.getReleaseDate().split("-");
        String formattedDate = date[2] + " " + Media.getMonth(date[1]) + " " + date[0];

        binding.rvGenres.setAdapter(new GenreAdapter(genres));
        binding.tvTitle.setText(movie.getTitle());
        binding.tvOverview.setText(movie.getOverview());
        binding.tvRating.setText(String.valueOf(movie.getVoteAverage()));
        binding.tvDate.setText(formattedDate);
        binding.tvType.setText("Movie");
        binding.tvRatingCount.setText(String.valueOf(movie.getVoteCount()));

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .into(binding.ivPoster);

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + movie.getBackdropPath())
                .into(binding.ivBanner);

        binding.ivType.setImageResource(R.drawable.round_movie_24);



        if (movie.getOverview().isEmpty())
            noOverview();

    }


    private void setTVShow(TVShow tvShow) {
        List<Genre> genres = getGenre(getResources().getStringArray(R.array.tv), tvShow.getGenreIDs());
        String[] date = tvShow.getFirstAirDate().split("-");
        String formattedDate = date[2] + " " + Media.getMonth(date[1]) + " " + date[0];
        binding.rvGenres.setAdapter(new GenreAdapter(genres));
        binding.tvTitle.setText(tvShow.getName());
        binding.tvOverview.setText(tvShow.getOverview());
        binding.tvRating.setText(String.valueOf(tvShow.getVoteAverage()));
        binding.tvDate.setText(formattedDate);
        binding.tvType.setText("TV Show");
        binding.tvRatingCount.setText(String.valueOf(tvShow.getVoteCount()));

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + tvShow.getPosterPath())
                .into(binding.ivPoster);

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + tvShow.getBackdropPath())
                .into(binding.ivBanner);

        binding.ivType.setImageResource(R.drawable.round_tv_24);

        if (tvShow.getOverview().isEmpty())
            noOverview();
    }

    private void takeMedia(Movie movie, TVShow tvShow) {
        movie = getIntent().getParcelableExtra(Media.MOVIE);
        if (movie == null) {
            tvShow = getIntent().getParcelableExtra(Media.TV_SHOW);
            setTVShow(tvShow);
        } else {
            setMovie(movie);
        }
    }

    private List<Genre> getGenre(String[] rawGenres, int[] genresMatchID) {
        List<Genre> genres = new ArrayList<>();
        for (int j : genresMatchID) {
            for (String mediaGenre : rawGenres) {
                String[] split = mediaGenre.split(":");
                if (split[0].equals(String.valueOf(j)))
                    genres.add(new Genre(Integer.parseInt(split[0]), split[1]));
            }
        }
        return genres;
    }

    private void noOverview() {
        binding.tvLabelOverview.setText(R.string.str_no_overview);
        binding.tvOverview.setVisibility(View.GONE);
        binding.lavHand.setVisibility(View.VISIBLE);
    }



}