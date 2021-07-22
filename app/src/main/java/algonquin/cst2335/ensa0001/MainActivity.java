package algonquin.cst2335.ensa0001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {

    private String stringUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button forecastBtn = findViewById(R.id.forecastButton);
        EditText cityText = findViewById(R.id.cityTextField);


        forecastBtn.setOnClickListener(clk ->{
        });


        Executor newThread = Executors.newSingleThreadExecutor();

        newThread.execute(() ->{
            try {
                String cityName = cityText.getText().toString();

                stringUrl = "https://api.openweathermap.org/data/2.5/weather?q"
                        + URLEncoder.encode(cityName,"UTF-8")
                        +"&appid=7e943c97096a9784391a981c4d878b22&Units=Metric";

                URL url = new URL(stringUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            }



            catch (IOException ioe){
                Log.e("Connection error:", ioe.getMessage());
            }
        });

    }

}