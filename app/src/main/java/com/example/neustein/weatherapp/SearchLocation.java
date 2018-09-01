package com.example.neustein.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class SearchLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
    }

    public void searchWeather(View view){
        final EditText mEditText = findViewById(R.id.postalText);
        final TextView mTextView = findViewById(R.id.textView3);
        final Intent weatherIntent = new Intent(this,Weather.class);

        String newPlace = mEditText.getText().toString();
        String url = "https://query.yahooapis.com/v1/public/yql?q=select * from weather.forecast where woeid in (select woeid from geo.places(1) where text='"+newPlace+"')&format=json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//               mTextView.setText(response.toString());
                weatherIntent.putExtra("WeatherData",response.toString());
                startActivity(weatherIntent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("Failed");
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }
}

