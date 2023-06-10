package com.sisfo.practicumfinale.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sisfo.practicumfinale.R;
import com.sisfo.practicumfinale.activities.MainActivity;
import com.sisfo.practicumfinale.activities.MediaActivity;
import com.sisfo.practicumfinale.adapters.TVShowAdapter;
import com.sisfo.practicumfinale.data.http.APIClient;
import com.sisfo.practicumfinale.data.http.Response;
import com.sisfo.practicumfinale.databinding.FragmentTVShowsBinding;
import com.sisfo.practicumfinale.models.TVShow;
import com.sisfo.practicumfinale.utils.Media;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class TVShowsFragment extends Fragment {
    private FragmentTVShowsBinding binding;
    private MainActivity parent;
    public TVShowsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTVShowsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentTVShowsBinding.bind(view);
        parent = (MainActivity) getActivity();
        parent.startLoading();
        fetchData(binding.rvTvShows);
    }

    private void fetchData(RecyclerView rvTVShow) {
        Call<Response<List<TVShow>>> client = APIClient.service().getTVShows(getString(R.string.api_key), "en-US");

        client.enqueue(new Callback<Response<List<TVShow>>>() {
            @Override
            public void onResponse(Call<Response<List<TVShow>>> call, retrofit2.Response<Response<List<TVShow>>> response) {
                if (response.isSuccessful()) {
                    TVShowAdapter adapter = new TVShowAdapter(response.body().getData());
                    adapter.setClickListener(tvShow -> toMedia(tvShow));
                    rvTVShow.setAdapter(adapter);
                    parent.stopLoading();
                }
            }

            @Override
            public void onFailure(Call<Response<List<TVShow>>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    private void toMedia(TVShow tvShow) {
        Intent intent = new Intent(getActivity(), MediaActivity.class);
        intent.putExtra(Media.TV_SHOW, tvShow);
        startActivity(intent);
    }
}