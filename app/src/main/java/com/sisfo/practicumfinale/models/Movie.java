package com.sisfo.practicumfinale.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

    // Response body
    @SerializedName("id") int apiID;
    @SerializedName("title") String title;
    @SerializedName("release_date") String releaseDate;
    @SerializedName("poster_path") String posterPath; // poster
    @SerializedName("backdrop_path") String backdropPath; // banner
    @SerializedName("overview") String overview;
    @SerializedName("vote_average") String voteAverage;
    @SerializedName("genre_ids") int[] genreIDs;
    @SerializedName("vote_count") int voteCount;

    public Movie(int apiID, String title, String releaseDate, String posterPath, String backdropPath, String overview, String voteAverage, int[] genreIDs, int voteCount) {
        this.apiID = apiID;
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.genreIDs = genreIDs;
        this.voteCount = voteCount;
    }

    protected Movie(Parcel in) {
        apiID = in.readInt();
        title = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        overview = in.readString();
        voteAverage = in.readString();
        genreIDs = in.createIntArray();
        voteCount = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    // Getters
    public int getApiID() {
        return apiID;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public int[] getGenreIDs() {
        return genreIDs;
    }

    public int getVoteCount() {
        return voteCount;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(apiID);
        parcel.writeString(title);
        parcel.writeString(releaseDate);
        parcel.writeString(posterPath);
        parcel.writeString(backdropPath);
        parcel.writeString(overview);
        parcel.writeString(voteAverage);
        parcel.writeIntArray(genreIDs);
        parcel.writeInt(voteCount);

    }
}
