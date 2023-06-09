package com.sisfo.practicumfinale.data.http;

import com.sisfo.practicumfinale.models.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @GET("{media}/popular")
    Call<Response<List<Movie>>> getMedia(@Path("media") String media, @Query("api_key") String apiKey, @Query("language") String language);
}
