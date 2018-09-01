package com.example.neustein.weatherapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Weather extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        showWeather();
    }

    public void showWeather() {
        Intent intent = getIntent();
        String jsonString = intent.getStringExtra("WeatherData");


        try {
            JSONObject jsonObj = new JSONObject(jsonString);
            jsonObj = jsonObj.getJSONObject("query").getJSONObject("results").getJSONObject("channel");

            final TextView cityView = findViewById(R.id.cityText);
            final TextView windView = findViewById(R.id.windText);
            final TextView textView = findViewById(R.id.textText);
            final TextView humidView = findViewById(R.id.humidText);
            final TextView tempView = findViewById(R.id.curTempText);

            JSONObject location = jsonObj.getJSONObject("location");
            JSONObject condition = jsonObj.getJSONObject("item").getJSONObject("condition");
            JSONObject atmosphere = jsonObj.getJSONObject("atmosphere");
            JSONObject wind = jsonObj.getJSONObject("wind");
            JSONArray forecast = jsonObj.getJSONObject("item").getJSONArray("forecast");

            String city = location.getString("city") + "," + location.getString("region");
            String curTemp = condition.getString("temp") + (char)0x00B0;
            String curText = condition.getString("text");
            String humid = atmosphere.getString("humidity") + "%";
            String windSpeed = wind.getString("speed") + "mph";
            Integer len = jsonObj.getJSONObject("item").getJSONArray("forecast").length();

            LinearLayout layout = findViewById(R.id.linearLayout);

            for (int i = 0; i < len;i++){
                LinearLayout horLayout = new LinearLayout(this);
                horLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                horLayout.setOrientation(LinearLayout.HORIZONTAL);
                TextView dayView =  new TextView(this);
                TextView textsView = new TextView(this);
                TextView lowView = new TextView(this);
                TextView highView = new TextView(this);
                View view = new View(this);
                view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
                view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                String lowTemp = "low: " + forecast.getJSONObject(i).getString("low");
                String highTemp = "high: " + forecast.getJSONObject(i).getString("high");
                String date = forecast.getJSONObject(i).getString("date");
                String dayi = forecast.getJSONObject(i).getString("day") + ", " + date.split(" ")[0] + " " + date.split(" ")[1];
                String texts = forecast.getJSONObject(i).getString("text");

                dayView.setTextColor(Color.parseColor("#FFFFFF"));
                textsView.setTextColor(Color.parseColor("#FFFFFF"));
                lowView.setTextColor(Color.parseColor("#FFFFFF"));
                highView.setTextColor(Color.parseColor("#FFFFFF"));
                dayView.setTextSize(18);
                textsView.setTextSize(18);
                lowView.setTextSize(18);
                highView.setTextSize(18);
                dayView.setGravity(Gravity.CENTER);
                textsView.setGravity(Gravity.CENTER);
                horLayout.setGravity(Gravity.CENTER);

                lowView.setPadding(0,0,100,0);
                highView.setPadding(100,0,0,0);
                horLayout.setPadding(0,0,0,50);
                dayView.setText(dayi);
                textsView.setText(texts);
                lowView.setText(lowTemp);
                highView.setText(highTemp);
                layout.addView(dayView);
                layout.addView(textsView);
                horLayout.addView(lowView);
                horLayout.addView(highView);
                layout.addView(horLayout);
                layout.addView(view);
            }

            tempView.setText(curTemp);
            windView.setText(windSpeed);
            cityView.setText(city);
            humidView.setText(humid);
            textView.setText(curText);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
        }
    }
}
