package com.sisfo.practicumfinale.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.sisfo.practicumfinale.R;
import com.sisfo.practicumfinale.databinding.ActivityMainBinding;
import com.sisfo.practicumfinale.fragments.FavoritesFragment;
import com.sisfo.practicumfinale.fragments.MoviesFragment;
import com.sisfo.practicumfinale.fragments.TVShowsFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        binding.btnReload.setOnClickListener(v -> {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_main);
            if (fragment instanceof MoviesFragment)
                startFragment(new MoviesFragment());
            else
                startFragment(new TVShowsFragment());

            hideError();
        });

        setContentView(binding.getRoot());
        startFragment(new MoviesFragment());
        initiateBottomNavbar();
    }

    public void showError() {
        binding.llError.setVisibility(View.VISIBLE);
    }

    public void hideError() {
        binding.llError.setVisibility(View.GONE);
    }

    public void startLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    public void stopLoading() {
        binding.progressBar.setVisibility(View.GONE);
    }

    public void setTitle(String title) {
        binding.tvTitle.setText(title);
    }

    private void startFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_main, fragment)
                .commit();
    }

    private void initiateBottomNavbar() {
        binding.bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_movies)
                startFragment(new MoviesFragment());
            else if (item.getItemId() == R.id.nav_tv_shows)
                startFragment(new TVShowsFragment());
            else if (item.getItemId() == R.id.nav_favorites)
                startFragment(new FavoritesFragment());

            return true;
        });
    }
}