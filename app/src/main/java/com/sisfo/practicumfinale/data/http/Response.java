package com.sisfo.practicumfinale.data.http;

import com.google.gson.annotations.SerializedName;

public class Response<T> {
    @SerializedName("results")
    private T data;

    public T getData() {
        return data;
    }
}
