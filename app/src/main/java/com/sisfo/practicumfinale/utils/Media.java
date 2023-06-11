package com.sisfo.practicumfinale.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sisfo.practicumfinale.models.Genre;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Media {
    public static final String BOOKMARK_ID = "BOOKMARK_ID";
    public static final String BOOKMARKED = "BOOKMARKED";
    public static String MOVIE = "MOVIE";
    public static String TV_SHOW = "TV_SHOW";
    public static String BOOKMARK = "BOOKMARK";
    public static final int RESULT_ADD = 101;
    public static final int RESULT_DELETE = 201;

    public static String getMonth(String str) {
        String month = "";
        switch (str) {
            case "01":
                month = "January";
                break;
            case "02":
                month = "February";
                break;
            case "03":
                month = "Maret";
                break;
            case "04":
                month = "April";
                break;
            case "05":
                month = "Mei";
                break;
            case "06":
                month = "June";
                break;
            case "07":
                month = "Juli";
                break;
            case "08":
                month = "August";
                break;
            case "09":
                month = "September";
                break;
            case "10":
                month = "October";
                break;
            case "11":
                month = "November";
                break;
            case "12":
                month = "December";
                break;
        }
        return month;
    }

    public static List<Genre> getGenre(String[] rawGenres, int[] genresMatchID) {
        List<Genre> genres = new ArrayList<>();
        for (int j : genresMatchID) {
            for (String mediaGenre : rawGenres) {
                String[] split = mediaGenre.split(":");
                if (split[0].equals(String.valueOf(j)))
                    genres.add(new Genre(Integer.parseInt(split[0]), split[1]));
            }
        }
        return genres;
    }

    public static String getFormattedGenres(int[] rawGenres) {
        String[] genres = new String[rawGenres.length];
        for (int i = 0; i < rawGenres.length; i++) {
            genres[i] = String.valueOf(rawGenres[i]);
        }

        return Arrays.toString(genres).replace("[", "").replace("]", "");
    }

    public static int[] getReverseFormattedGenres(String rawGenres) {
        String[] genres = rawGenres.split(",");

        int[] formattedGenres = new int[genres.length];
        for (int i = 0; i < genres.length; i++) {
            formattedGenres[i] = Integer.parseInt(genres[i].trim());
        }
        return formattedGenres;
    }

    public static byte[] getBytes(Bitmap resource) {
        if (resource == null) return null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        resource.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap getBitmap(byte[] image) {
        if (image == null) return null;
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static void placeImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + path)
                .into(imageView);
    }
}

