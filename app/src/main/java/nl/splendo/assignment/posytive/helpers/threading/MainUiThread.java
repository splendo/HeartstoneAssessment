package nl.splendo.assignment.posytive.helpers.threading;

import android.os.Handler;
import android.os.Looper;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.LazyInitializer;

/**
 * A singleton wrapper around {@link Handler} that helps running jobs on the main UI thread.
 */
public class MainUiThread {

    private Handler handler;

    private MainUiThread() {
        handler = new Handler(Looper.getMainLooper());
    }

    public static synchronized MainUiThread getInstance() {

        LazyInitializer<MainUiThread> lazyInitializer = new LazyInitializer<MainUiThread> () {

            @Override
            protected MainUiThread initialize() throws ConcurrentException {
                return new MainUiThread();
            }
        };
        try {
            return lazyInitializer.get();
        } catch (ConcurrentException ce) {
            return new MainUiThread();
        }
    }

    public void post(Runnable runnable) {
        handler.post(runnable);
    }

}
