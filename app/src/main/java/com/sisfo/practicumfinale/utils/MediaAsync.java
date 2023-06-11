package com.sisfo.practicumfinale.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.lang.ref.WeakReference;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MediaAsync {
    private final WeakReference<MediaCallback> callback;
    private final WeakReference<Context> context;

    public MediaAsync(Context context, MediaCallback callback) {
        this.context = new WeakReference<>(context);
        this.callback = new WeakReference<>(callback);
    }

    public void execute() {
        callback.get().onStart();

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());
        executor.execute(() -> {
            callback.get().onLoading();
            handler.post(() -> {
                callback.get().onSuccess();
            });
        });
//        callback.get().onStart();
//
//        CompletableFuture.runAsync(() -> {
//            callback.get().onLoading();
//            // Perform asynchronous operations here
//
//        }).thenAcceptAsync((Void) -> {
//            callback.get().onSuccess();
//
//        });
    }

}
