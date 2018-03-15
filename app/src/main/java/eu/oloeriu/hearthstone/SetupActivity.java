package eu.oloeriu.hearthstone;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import eu.oloeriu.hearthstone.tools.Constants;
import eu.oloeriu.hearthstone.tools.MyIntentService;

public class SetupActivity extends AppCompatActivity {
    private String logTag = Constants.LOG_TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);


        (new InitiateSetup()).execute();
    }

    private class InitiateSetup extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            MyIntentService.startActionInitialSetup(getApplicationContext(),"AAA","BBB");
            return null;
        }
    }
}
