package com.sisfo.practicumfinale.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.sisfo.practicumfinale.R;
import com.sisfo.practicumfinale.adapters.GenreAdapter;
import com.sisfo.practicumfinale.data.local.Bookmark;
import com.sisfo.practicumfinale.data.local.DatabaseHelper;
import com.sisfo.practicumfinale.databinding.ActivityMediaBinding;
import com.sisfo.practicumfinale.models.Genre;
import com.sisfo.practicumfinale.models.Movie;
import com.sisfo.practicumfinale.models.TVShow;
import com.sisfo.practicumfinale.utils.Media;
import com.sisfo.practicumfinale.utils.MediaAsync;
import com.sisfo.practicumfinale.utils.MediaCallback;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MediaActivity extends AppCompatActivity {
    private ActivityMediaBinding binding;
    private DatabaseHelper dbHelper;
    private Movie movie;
    private TVShow tvShow;
    private Bookmark bookmark;
    private boolean isBookmarked;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMediaBinding.inflate(getLayoutInflater());

        dbHelper = DatabaseHelper.getInstance(this);
        intent = new Intent();

        binding.btnBack.setOnClickListener(v -> finish());
        binding.btnBookmark.setOnClickListener(v -> toggleBookmark());

        setContentView(binding.getRoot());
        loadData();
    }

    private void loadData() {

        new MediaAsync(this, new MediaCallback() {
            @Override
            public void onStart() {
                preCheck(getIntent().getParcelableExtra(Media.MOVIE), getIntent().getParcelableExtra(Media.TV_SHOW));
                binding.loadingOverlay.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoading() {
                if (!isBookmarked)
                    bookmark = getBookmarkModel();

            }

            @Override
            public void onSuccess() {
                binding.loadingOverlay.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);

                if (!isBookmarked) {
                    if (movie != null) setMovie(movie, false);
                    else setTVShow(tvShow, false);
                    return;
                }

                binding.btnBookmark.setImageResource(R.drawable.round_bookmark_24);
                if (movie != null) {
                    bookmark = dbHelper.roomDao().getByApiID(movie.getApiID());
                    setMovie(movie, isBookmarked);
                    getBookmarkImages(bookmark);
                } else {
                    bookmark = dbHelper.roomDao().getByApiID(tvShow.getApiID());
                    setTVShow(tvShow, isBookmarked);
                    getBookmarkImages(bookmark);
                }
            }

        }).execute();
    }

    private void preCheck(Movie movie, TVShow tvShow) {

        if (getIntent().getBooleanExtra(Media.BOOKMARKED, false)) {
            this.isBookmarked = true;
            this.movie = movie;
            this.tvShow = tvShow;
            return;
        }

        if (movie != null) {
            this.movie = movie;
            this.isBookmarked = dbHelper.roomDao().getByApiID(movie.getApiID()) != null;
        } else {
            this.tvShow = tvShow;
            this.isBookmarked = dbHelper.roomDao().getByApiID(tvShow.getApiID()) != null;
        }
    }

    private void getBookmarkImages(Bookmark bookmark) {
        Glide.with(this)
                .asBitmap()
                .load(Media.getBitmap(bookmark.getPosterPath()))
                .into(binding.ivPoster);

        Glide.with(this)
                .asBitmap()
                .load(Media.getBitmap(bookmark.getBackdropPath()))
                .into(binding.ivBanner);
    }

    private void setMovie(Movie movie, boolean fromBookmark) {
        List<Genre> genres = Media.getGenre(getResources().getStringArray(R.array.movie), movie.getGenreIDs());
        String[] date = movie.getReleaseDate().split("-");
        String formattedDate = date[2] + " " + Media.getMonth(date[1]) + " " + date[0];

        binding.rvGenres.setAdapter(new GenreAdapter(genres));
        binding.tvTitle.setText(movie.getTitle());
        binding.tvOverview.setText(movie.getOverview());
        binding.tvRating.setText(String.valueOf(movie.getVoteAverage()));
        binding.tvDate.setText(formattedDate);
        binding.tvType.setText("Movie");
        binding.tvRatingCount.setText(String.valueOf(movie.getVoteCount()));
        binding.ivType.setImageResource(R.drawable.round_movie_24);

        if (!fromBookmark) {
            Media.placeImage(this, movie.getPosterPath(), binding.ivPoster);
            Media.placeImage(this, movie.getBackdropPath(), binding.ivBanner);
        }

        if (movie.getOverview().isEmpty())
            noOverview();
    }

    private void setTVShow(TVShow tvShow, boolean fromBookmark) {
        List<Genre> genres = Media.getGenre(getResources().getStringArray(R.array.tv), tvShow.getGenreIDs());
        String[] date = tvShow.getFirstAirDate().split("-");
        String formattedDate = date[2] + " " + Media.getMonth(date[1]) + " " + date[0];
        binding.rvGenres.setAdapter(new GenreAdapter(genres));
        binding.tvTitle.setText(tvShow.getName());
        binding.tvOverview.setText(tvShow.getOverview());
        binding.tvRating.setText(String.valueOf(tvShow.getVoteAverage()));
        binding.tvDate.setText(formattedDate);
        binding.tvType.setText("TV Show");
        binding.tvRatingCount.setText(String.valueOf(tvShow.getVoteCount()));
        binding.ivType.setImageResource(R.drawable.round_tv_24);

        if (!fromBookmark) {
            Media.placeImage(this, tvShow.getPosterPath(), binding.ivPoster);
            Media.placeImage(this, tvShow.getBackdropPath(), binding.ivBanner);
        }

        if (tvShow.getOverview().isEmpty())
            noOverview();
    }

    private void toggleBookmark() {
        if (binding.lavTap.isAnimating()) {
            setResult(Media.RESULT_DELETE, null);
            setResult(Media.RESULT_ADD, null);
            return;
        }

        if (isBookmarked) {
            isBookmarked = false;
            binding.btnBookmark.setImageResource(R.drawable.round_bookmark_border_24);
            dbHelper.roomDao().delete(bookmark);
            intent.putExtra(Media.BOOKMARK_ID, getIntent().getIntExtra(Media.BOOKMARK_ID, 0));
            intent.removeExtra(Media.BOOKMARK);
            setResult(Media.RESULT_DELETE, intent);
            return;
        }

        isBookmarked = true;
        binding.btnBookmark.setImageResource(R.drawable.round_bookmark_24);
        binding.lavTap.playAnimation();

        if (dbHelper.roomDao().getByApiID(bookmark.getApiID()) == null) {
            dbHelper.roomDao().insert(bookmark);
            intent.removeExtra(Media.BOOKMARK_ID);
            intent.putExtra(Media.BOOKMARK, bookmark);
            setResult(Media.RESULT_ADD, intent);
        }

    }

    private Bookmark getBookmarkModel() {
        if (this.movie != null) {
            return new Bookmark(
                    this.movie.getApiID(),
                    this.movie.getTitle(),
                    this.movie.getReleaseDate(),
                    this.glideToByte(this.movie.getPosterPath()),
                    this.glideToByte(this.movie.getBackdropPath()),
                    this.movie.getOverview(),
                    this.movie.getVoteAverage(),
                    Media.getFormattedGenres(this.movie.getGenreIDs()),
                    this.movie.getVoteCount(),
                    Media.MOVIE
            );
        } else {
            return new Bookmark(
                    this.tvShow.getApiID(),
                    this.tvShow.getName(),
                    this.tvShow.getFirstAirDate(),
                    this.glideToByte(this.tvShow.getPosterPath()),
                    this.glideToByte(this.tvShow.getBackdropPath()),
                    this.tvShow.getOverview(),
                    this.tvShow.getVoteAverage(),
                    Media.getFormattedGenres(this.tvShow.getGenreIDs()),
                    this.tvShow.getVoteCount(),
                    Media.TV_SHOW
            );
        }
    }

    private void noOverview() {
        binding.tvLabelOverview.setText(R.string.str_no_overview);
        binding.tvOverview.setVisibility(View.GONE);
        binding.lavHand.setVisibility(View.VISIBLE);
    }

    private byte[] glideToByte(String url) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Bitmap> future = executorService.submit(() -> {
            try {
                return Glide.with(this)
                        .asBitmap()
                        .load("https://image.tmdb.org/t/p/w500" + url)
                        .submit()
                        .get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        });

        try {
            return Media.getBytes(future.get());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
