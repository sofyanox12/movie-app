package com.sisfo.practicumfinale.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.sisfo.practicumfinale.R;
import com.sisfo.practicumfinale.databinding.ActivityMainBinding;
import com.sisfo.practicumfinale.fragments.MoviesFragment;
import com.sisfo.practicumfinale.fragments.TVShowsFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        startFragment(new MoviesFragment());
        initiateBottomNavbar();



    }

    public void startLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    public void stopLoading() {
        binding.progressBar.setVisibility(View.GONE);
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
//            else if (item.getItemId() == R.id.nav_favorites)
//                startFragment(new MoviesFragment());
//
            return true;
        });
    }

}