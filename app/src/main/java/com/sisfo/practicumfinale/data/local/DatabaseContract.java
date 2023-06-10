package com.sisfo.practicumfinale.data.local;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_NAME = "favorites";
    public static final class BookmarkColumns implements BaseColumns {
        public static String ID = "id";
        public static String API_ID = "api_id";
        public static String TITLE = "title";
        public static String POSTER = "poster";
        public static String BACKDROP = "backdrop";
        public static String DATE = "date";
        public static String GENRES = "genres";
        public static String OVERVIEW = "overview";
        public static String VOTE_AVG = "vote_avg";
        public static String VOTE_COUNT = "vote_count";
        public static String TYPE = "type";
    }
}
