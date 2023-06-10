package com.sisfo.practicumfinale.utils;

public class Media {
    public static String MOVIE = "MOVIE";
    public static String TV_SHOW = "TV_SHOW";

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
}
