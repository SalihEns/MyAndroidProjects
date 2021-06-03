package algonquin.cst2335.ensa0001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    final private static String TAG = "MainActivity";

    @Override
    protected void onStart() {
        super.onStart();
        Log.w (TAG, "In onStart() - The application is now visible on screen" );
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w(TAG, "In onCreate() - Loading Widgets" );
        Button loginButton = findViewById(R.id.nextPageButton);
        loginButton.setOnClickListener(  clk -> {
            Intent nextPage = new Intent( MainActivity.this, SecondActivity.class);
            startActivity(nextPage);

        } );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w (TAG, "The application is now responding to user input");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w (TAG, "In onPause() " );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w (TAG, "In onStop()" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w (TAG, "In onDestroy()" );
    }
}