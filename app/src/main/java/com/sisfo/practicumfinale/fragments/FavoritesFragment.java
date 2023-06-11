package com.sisfo.practicumfinale.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sisfo.practicumfinale.R;
import com.sisfo.practicumfinale.activities.MainActivity;
import com.sisfo.practicumfinale.activities.MediaActivity;
import com.sisfo.practicumfinale.adapters.FavoriteAdapter;
import com.sisfo.practicumfinale.data.local.Bookmark;
import com.sisfo.practicumfinale.data.local.DatabaseHelper;
import com.sisfo.practicumfinale.databinding.FragmentFavoritesBinding;
import com.sisfo.practicumfinale.models.Movie;
import com.sisfo.practicumfinale.models.TVShow;
import com.sisfo.practicumfinale.utils.Media;

import java.util.List;

public class FavoritesFragment extends Fragment {
    private FragmentFavoritesBinding binding;
    private MainActivity parent;
    private DatabaseHelper dbHelper;
    private FavoriteAdapter adapter;
    private ActivityResultLauncher<Intent> launcher;
    public FavoritesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFavoritesBinding binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentFavoritesBinding.bind(view);
        parent = (MainActivity) getActivity();
        launcher = getLauncherReference();
        dbHelper = DatabaseHelper.getInstance(getContext());
        parent.setTitle(getString(R.string.favorites));
        parent.startLoading();
        loadFavorites();
    }

    private void loadFavorites() {
        List<Bookmark> bookmarks = dbHelper.roomDao().getAll();
        adapter = new FavoriteAdapter(bookmarks);
        adapter.setClickListener(this::toMedia);
        binding.rvFavorites.setAdapter(adapter);
        parent.stopLoading();
        checkAdapter();
    }

    private void toMedia(Bookmark bookmark, int position) {
        Intent intent = new Intent(getContext(), MediaActivity.class);
        intent.putExtra(Media.BOOKMARKED, true);
        if (bookmark.getMediaType().equals(Media.MOVIE)) {
            Movie movie = new Movie(
                    bookmark.getApiID(),
                    bookmark.getTitle(),
                    bookmark.getReleaseDate(),
                    null,
                    null,
                    bookmark.getOverview(),
                    bookmark.getVoteAverage(),
                    Media.getReverseFormattedGenres(bookmark.getGenreIDs()),
                    bookmark.getVoteCount()
            );
            intent.putExtra(Media.MOVIE, movie);

        } else {
            TVShow tvShow = new TVShow(
                    bookmark.getApiID(),
                    bookmark.getTitle(),
                    bookmark.getReleaseDate(),
                    null,
                    null,
                    bookmark.getOverview(),
                    bookmark.getVoteAverage(),
                    Media.getReverseFormattedGenres(bookmark.getGenreIDs()),
                    bookmark.getVoteCount()
            );
            intent.putExtra(Media.TV_SHOW, tvShow);
        }

        intent.putExtra(Media.BOOKMARK_ID, position);

        launcher.launch(intent);
    }

    private ActivityResultLauncher<Intent> getLauncherReference() {
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getData() != null) {
                if (result.getResultCode() == Media.RESULT_DELETE) {
                    adapter.removeBookmark(result.getData().getIntExtra(Media.BOOKMARK_ID, 0));
                } else
                if (result.getResultCode() == Media.RESULT_ADD) {
                    adapter.addBookmark(result.getData().getParcelableExtra(Media.BOOKMARK));
                }

                checkAdapter();
            }

        });
    }

    private void checkAdapter() {
        if (adapter.getItemCount() < 1) {
            binding.tvEmpty.setVisibility(View.VISIBLE);
        } else {
            binding.tvEmpty.setVisibility(View.GONE);
        }
    }
    
}