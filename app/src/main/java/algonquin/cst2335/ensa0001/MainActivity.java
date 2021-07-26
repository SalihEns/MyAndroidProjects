package algonquin.cst2335.ensa0001;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class MainActivity extends AppCompatActivity {

     String stringUrl;
     Bitmap image = null;
     EditText cityText;
     Button forecastBtn;

    String description = null;
    String iconName = null;
    String current = null;
    String min = null;
    String max = null;
    String humidity = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        forecastBtn = findViewById(R.id.forecastButton);
        cityText = findViewById(R.id.cityTextField);

        forecastBtn.setOnClickListener(clk ->{

            String cityName = cityText.getText().toString();

            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Getting Forecast")
                    .setMessage("We're calling people in " +cityName+" to look outside their windows and tell us what's the weather like over there. :)")
                    .setView(new ProgressBar(MainActivity.this))
                    .show();

            Executor newThread = Executors.newSingleThreadExecutor();
            newThread.execute(() ->{
                try {
                    stringUrl = "https://api.openweathermap.org/data/2.5/weather?q="
                            + URLEncoder.encode(cityName,"UTF-8")
                            +"&appid=7e943c97096a9784391a981c4d878b22&units=metric&mode=xml";

                    URL url = new URL(stringUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(false);
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(in,"UTF-8");


                        while (xpp.next() != XmlPullParser.END_DOCUMENT)
                        {
                            switch (xpp.getEventType())
                            {
                                case XmlPullParser.START_TAG:
                                    if (xpp.getName().equals("temperature"))
                                    {
                                        current = xpp.getAttributeValue(null, "value");  //this gets the current temperature

                                        min = xpp.getAttributeValue(null, "min"); //this gets the min temperature

                                        max = xpp.getAttributeValue(null, "max"); //this gets the max temperature

                                    }
                                    else  if(xpp.getName().equals("weather"))
                                    {
                                        description = xpp.getAttributeValue(null, "value"); //this gets the min temperature

                                        iconName = xpp.getAttributeValue(null, "icon"); //this gets the max temperature
                                    }
                                    else  if(xpp.getName().equals("humidity"))
                                    {
                                       humidity  = xpp.getAttributeValue(null,"value");

                                    }
                                    break;
                                case XmlPullParser.END_TAG:

                                    break;
                                case XmlPullParser.TEXT:

                                    break;

                            }
                        }
//                    String text = (new BufferedReader(
//                            new InputStreamReader(in, StandardCharsets.UTF_8)))
//                            .lines()
//                            .collect(Collectors.joining("\n"));
//                    JSONObject theDocument = new JSONObject(text);
//
//                    JSONArray weatherArray = theDocument.getJSONArray("weather");
//                    JSONObject position0 = weatherArray.getJSONObject(0);

//                    String description = position0.getString("description");
//                    String iconName = position0.getString("icon");
//
//                    JSONObject mainObject = theDocument.getJSONObject( "main" );
//                    double current = mainObject.getDouble("temp");
//                    double min = mainObject.getDouble("temp_min");
//                    double max = mainObject.getDouble("temp_max");
//                    int humidity = mainObject.getInt("humidity");

                    File file = new File(getFilesDir()+ "/"+iconName+".png");
                        if (file.exists()){
                            image = BitmapFactory.decodeFile(getFilesDir()+"/"+iconName+".png");
                        }
                        else{
                            URL imgUrl = new URL("https://openweathermap.org/img/w/" + iconName + ".png");
                            HttpURLConnection connection = (HttpURLConnection) imgUrl.openConnection();
                            connection.connect();
                            int responseCode = connection.getResponseCode();
                            if (responseCode == 200) {
                                image = BitmapFactory.decodeStream(connection.getInputStream());
                                image.compress(Bitmap.CompressFormat.PNG,100,openFileOutput(iconName+" png", Activity.MODE_PRIVATE));
                            }
                        }

                    runOnUiThread(( ) ->{
                        TextView tv = findViewById(R.id.temp);
                        tv.setText("the current temperature is " + current);
                        tv.setVisibility(View.VISIBLE);

                        TextView tvmin = findViewById(R.id.min);
                        tvmin.setText("the  min temperature is " + min);
                        tvmin.setVisibility(View.VISIBLE);

                        TextView tvmax = findViewById(R.id.max);
                        tvmax.setText("the  max temperature is " + max);
                        tvmax.setVisibility(View.VISIBLE);

                        TextView hum = findViewById(R.id.humidity);
                        hum.setText("the  humidity  is " + humidity + "%");
                        hum.setVisibility(View.VISIBLE);

                        TextView descr = findViewById(R.id.description);
                        descr.setText(description);
                        descr.setVisibility(View.VISIBLE);

                        ImageView iv = findViewById(R.id.icon);
                        iv.setImageBitmap(image);
                        iv.setVisibility(View.VISIBLE);

                        dialog.hide();
                    });

                }
                catch (IOException |  XmlPullParserException ioe){
                    Log.e("Connection error:", ioe.getMessage());
                }
            });
        });
    }
}