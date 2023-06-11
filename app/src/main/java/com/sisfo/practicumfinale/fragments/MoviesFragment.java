package com.sisfo.practicumfinale.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sisfo.practicumfinale.R;
import com.sisfo.practicumfinale.activities.MainActivity;
import com.sisfo.practicumfinale.activities.MediaActivity;
import com.sisfo.practicumfinale.adapters.MovieAdapter;
import com.sisfo.practicumfinale.data.http.APIClient;
import com.sisfo.practicumfinale.data.http.Response;
import com.sisfo.practicumfinale.databinding.FragmentMoviesBinding;
import com.sisfo.practicumfinale.models.Movie;
import com.sisfo.practicumfinale.utils.Media;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MoviesFragment extends Fragment {
    private FragmentMoviesBinding binding;
    private MovieAdapter adapter;
    private MainActivity parent;
    private int page = 1;
    private boolean isLoading = false;
    public MoviesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMoviesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMoviesBinding.bind(view);

        parent = (MainActivity) getActivity();
        parent.setTitle(getString(R.string.movies));
        parent.startLoading();

        adapter = new MovieAdapter();
        adapter.setClickListener(this::toMedia);
        binding.rvMovies.setAdapter(adapter);

        initInfiniteScrolling();
        fetchData(page);
    }

    private void fetchData(int page) {
        isLoading = true;
        Call<Response<List<Movie>>> client = APIClient.service().getMovies( getString(R.string.api_key), "en_US", page);
        client.enqueue(new Callback<Response<List<Movie>>>() {
            @Override
            public void onResponse(Call<Response<List<Movie>>> call, retrofit2.Response<Response<List<Movie>>> response) {
                if (response.isSuccessful()) {
                    adapter.addAll(response.body().getData());
                    adapter.notifyItemRangeInserted(adapter.getItemCount() - 2, response.body().getData().size());
                    parent.stopLoading();
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<Response<List<Movie>>> call, Throwable t) {
                    parent.showError();
                    parent.stopLoading();
                    isLoading = false;
            }
        });
    }

    private void initInfiniteScrolling() {
        binding.rvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rvMovies, int dx, int dy) {
                super.onScrolled(rvMovies, dx, dy);
                GridLayoutManager layoutManager = (GridLayoutManager) rvMovies.getLayoutManager();
                if (!isLoading) {
                    if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == adapter.getItemCount() - 1) {
                        page++;
                        fetchData(page);
                    }
                }
            }

        });
    }

    private void toMedia(Movie movie) {
        Intent toDetail = new Intent(getActivity(), MediaActivity.class);
        toDetail.putExtra(Media.MOVIE, movie);
        startActivity(toDetail);
    }
}