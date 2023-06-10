package com.sisfo.practicumfinale.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class TVShow implements Parcelable {
    // Response body
    @SerializedName("id") int apiID;
    @SerializedName("name") String name;
    @SerializedName("first_air_date") String firstAirDate; // release date
    @SerializedName("poster_path") String posterPath;
    @SerializedName("backdrop_path") String backdropPath;
    @SerializedName("overview") String overview;
    @SerializedName("vote_average") String voteAverage;
    @SerializedName("genre_ids") int[] genreIDs;
    @SerializedName("vote_count") int voteCount;

    protected TVShow(Parcel in) {
        apiID = in.readInt();
        name = in.readString();
        firstAirDate = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        overview = in.readString();
        voteAverage = in.readString();
        genreIDs = in.createIntArray();
        voteCount = in.readInt();
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel in) {
            return new TVShow(in);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };

    // Getters
    public int getApiID() {
        return apiID;
    }

    public String getName() {
        return name;
    }

    public String getFirstAirDate() {
        return firstAirDate;
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
        parcel.writeString(name);
        parcel.writeString(firstAirDate);
        parcel.writeString(posterPath);
        parcel.writeString(backdropPath);
        parcel.writeString(overview);
        parcel.writeString(voteAverage);
        parcel.writeIntArray(genreIDs);
        parcel.writeInt(voteCount);
    }
}
