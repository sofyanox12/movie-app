package com.sisfo.practicumfinale.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.sisfo.practicumfinale.R;
import com.sisfo.practicumfinale.activities.MediaActivity;
import com.sisfo.practicumfinale.adapters.MovieAdapter;
import com.sisfo.practicumfinale.data.http.APIClient;
import com.sisfo.practicumfinale.data.http.Response;
import com.sisfo.practicumfinale.databinding.FragmentMoviesBinding;
import com.sisfo.practicumfinale.models.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MoviesFragment extends Fragment {
    private FragmentMoviesBinding binding;
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

        // fetch data from API and set it to recyclerview
        fetchData();

    }

    private void fetchData() {
        Call<Response<List<Movie>>> client = APIClient.service().getMedia("movie", getString(R.string.api_key), "en-US");

        client.enqueue(new Callback<Response<List<Movie>>>() {
            @Override
            public void onResponse(Call<Response<List<Movie>>> call, retrofit2.Response<Response<List<Movie>>> response) {
                if (response.isSuccessful()) {
                    List<Movie> movies = response.body().getData();
                    MovieAdapter adapter = new MovieAdapter(movies);
                    adapter.setClickListener(movie -> {
                        Intent toDetail = new Intent(getActivity(), MediaActivity.class);
                        toDetail.putExtra("MEDIA", movie);
                        startActivity(toDetail);
                    });
                    binding.rvMovies.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Response<List<Movie>>> call, Throwable t) {

            }
        });
    }
}