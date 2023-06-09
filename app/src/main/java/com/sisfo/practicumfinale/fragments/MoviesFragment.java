package com.sisfo.practicumfinale.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sisfo.practicumfinale.R;
import com.sisfo.practicumfinale.activities.MainActivity;
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
    private MainActivity parent;
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
        parent.startLoading();
        fetchData(binding.rvMovies);

    }

    private void fetchData(RecyclerView rvMovies) {
        Call<Response<List<Movie>>> client = APIClient.service().getMovies( getString(R.string.api_key), "en-US");

        client.enqueue(new Callback<Response<List<Movie>>>() {
            @Override
            public void onResponse(Call<Response<List<Movie>>> call, retrofit2.Response<Response<List<Movie>>> response) {
                if (response.isSuccessful()) {
                    MovieAdapter adapter = new MovieAdapter(response.body().getData());
                    adapter.setClickListener(movie -> toMedia(movie));
                    rvMovies.setAdapter(adapter);
                    System.out.println(response.body().getData().get(0).getVoteAverage());
                    parent.stopLoading();
                }
            }

            @Override
            public void onFailure(Call<Response<List<Movie>>> call, Throwable t) {

            }
        });
    }

    private void toMedia(Movie movie) {
        Intent toDetail = new Intent(getActivity(), MediaActivity.class);
        toDetail.putExtra("MEDIA", movie);
        startActivity(toDetail);
    }
}