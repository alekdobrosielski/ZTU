package Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import AutoLogIn.AutoLogInPreferences;

public class BaseActivity extends AppCompatActivity {
    private static android.os.Handler handler = new android.os.Handler();
    private static Runnable runnable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        if (handler == null) {
            handler = new Handler();
        } else {
            handler.removeCallbacks(runnable);
        }
        if (runnable == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            Context context = this;
            runnable = new Runnable() {
                @Override
                public void run() {

                    stop();
                    startActivity(intent);
                    ((Activity) context).finish();
                    Toast.makeText(context, "Auto log out due to inactivity!", Toast.LENGTH_LONG).show();
                    AutoLogInPreferences.clearPrefs(context);
                    runnable = null;
                    handler = new Handler();

                }

            };
        }
        start();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        stop();
        start();

    }

    void start() {
        handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(3000));
    }

    void stop() {
        handler.removeCallbacks(runnable);
    }

    public void Reset()
    {
        stop();
        runnable = null;
        handler = new Handler();
    }

}