package com.zaferayan.newsportal.backgroundService;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import com.zaferayan.newsportal.config.Constants;
import com.zaferayan.newsportal.di.DependencyInjectorImpl;
import com.zaferayan.newsportal.util.NetworkUtil;

import java.util.Timer;
import java.util.TimerTask;

import static com.zaferayan.newsportal.config.Constants.INTERVAL_SERVICE;
import static com.zaferayan.newsportal.config.Constants.INTERVAL_SERVICE_DELAY;
import static com.zaferayan.newsportal.util.ToastHelper.showError;
import static com.zaferayan.newsportal.util.ToastHelper.showToast;

public class UpdaterService extends Service {

    private DependencyInjectorImpl injector;

    private Timer mTimer;
    private Handler handler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        this.injector = new DependencyInjectorImpl(getApplication());
        if (mTimer != null)
            mTimer = null;

        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new ArticleGetterTask(), INTERVAL_SERVICE_DELAY, INTERVAL_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class ArticleGetterTask extends TimerTask {

        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String sourceId = getApplicationContext()
                            .getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE)
                            .getString(Constants.PREF_SOURCE_ID, null);
                    if (sourceId == null) {
                        showError(injector.application, "Source Id could not get from preferences.");
                        return;
                    }
                    showToast(injector.application, "Getting news...");
                    NetworkUtil.getArticles(injector, sourceId, null, null);
                }
            });
        }
    }
}
