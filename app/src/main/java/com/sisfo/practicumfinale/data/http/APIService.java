package com.sisfo.practicumfinale.data.http;

import com.sisfo.practicumfinale.models.Movie;
import com.sisfo.practicumfinale.models.TVShow;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("movie/popular")
    Call<Response<List<Movie>>> getMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("tv/popular")
    Call<Response<List<TVShow>>> getTVShows(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

}
