package nl.splendo.assignment.posytive;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * AndreaNigido Application.
 * In charge of:
 * - initializing Database (with DbFlow)
 * - initializing custom fonts library (with Calligraphy)
 */
public class ANApplication extends Application {

    /** Used for logging */
    public static final String TAG = "ANApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        // setup db via FlowManager
        FlowManager.init(new FlowConfig.Builder(this).build());

        // setup Calligraphy lib to use custom fonts
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
