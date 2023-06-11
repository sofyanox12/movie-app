package com.sisfo.practicumfinale.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.lang.ref.WeakReference;
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
        callback.get().onPreExecute();

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            callback.get().onLoad();
            handler.post(() -> {
                callback.get().onFinish();
            });
        });
    }

}
