package com.sisfo.practicumfinale.data.local;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmark")
public class Bookmark implements Parcelable {
    @PrimaryKey (autoGenerate = true)
    private int id;

    @ColumnInfo(name = "api_id")
    private int apiID;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "poster_path")
    private byte[] posterPath;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "backdrop_path")
    private byte[] backdropPath;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "vote_average")
    private String voteAverage;

    @ColumnInfo(name = "genre_ids")
    private String genreIDs;

    @ColumnInfo(name = "vote_count")
    private int voteCount;

    @ColumnInfo(name = "media_type")
    private String mediaType;

    public Bookmark(int id, int apiID, String title, String releaseDate, byte[] posterPath, byte[] backdropPath, String overview, String voteAverage, String genreIDs, int voteCount, String mediaType) {
        this.id = id;
        this.apiID = apiID;
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.genreIDs = genreIDs;
        this.voteCount = voteCount;
        this.mediaType = mediaType;
    }

    @Ignore
    public Bookmark(int apiID, String title, String releaseDate, byte[] posterPath, byte[] backdropPath, String overview, String voteAverage, String genreIDs, int voteCount, String mediaType) {
        this.apiID = apiID;
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.genreIDs = genreIDs;
        this.voteCount = voteCount;
        this.mediaType = mediaType;
    }

    protected Bookmark(Parcel in) {
        id = in.readInt();
        apiID = in.readInt();
        title = in.readString();
        releaseDate = in.readString();
        overview = in.readString();
        voteAverage = in.readString();
        genreIDs = in.readString();
        voteCount = in.readInt();
        mediaType = in.readString();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            posterPath = in.readBlob();
            backdropPath = in.readBlob();
        }
    }

    public static final Creator<Bookmark> CREATOR = new Creator<Bookmark>() {
        @Override
        public Bookmark createFromParcel(Parcel in) {
            return new Bookmark(in);
        }

        @Override
        public Bookmark[] newArray(int size) {
            return new Bookmark[size];
        }
    };

    public int getId() {
        return id;
    }

    public int getApiID() {
        return apiID;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public byte[] getPosterPath() {
        return posterPath;
    }

    public byte[] getBackdropPath() {
        return backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getGenreIDs() {
        return genreIDs;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getMediaType() {
        return mediaType;
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
        parcel.writeByteArray(posterPath);
        parcel.writeByteArray(backdropPath);
        parcel.writeString(overview);
        parcel.writeString(voteAverage);
        parcel.writeString(genreIDs);
        parcel.writeInt(voteCount);
        parcel.writeString(mediaType);
    }
}
