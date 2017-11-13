package org.arnoid.heartstone;

import android.app.Application;
import android.support.annotation.NonNull;

import org.arnoid.heartstone.controller.DatabaseController;
import org.arnoid.heartstone.controller.NetworkController;

public class HeartstoneApplication extends Application {

    private static NetworkController networkController;
    private static DatabaseController databaseController;

    @Override
    public void onCreate() {
        super.onCreate();

        networkController = produceNetworkController();
        databaseController = produceDatabaseController();
    }

    /**
     * Factory method to override in subclasses.
     * @return databaseController
     */
    @NonNull
    protected DatabaseController produceDatabaseController() {
        return new DatabaseController(this);
    }

    /**
     * Factory method to override in subclasses.
     * @return NetworkController
     */
    @NonNull
    protected NetworkController produceNetworkController() {
        return new NetworkController();
    }

    public static NetworkController network() {
        return networkController;
    }

    public static DatabaseController database() {
        return databaseController;
    }

}
