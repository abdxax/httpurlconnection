package com.example.abdulrahman.weatherapi;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
EditText city;
TextView h1,h2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city=(EditText) findViewById(R.id.editText);
        h1=(TextView) findViewById(R.id.textView);
        h2=(TextView) findViewById(R.id.textView2);
    }

    public void butsearch(View view) {
        String url="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22=+"+city.getText().toString()+"+%2C%20ar%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        new Connec().execute(url);

    }

    private class Connec extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String jso=values[0];
            try {
                JSONObject jsonObject=new JSONObject(jso);
                JSONObject qur=jsonObject.getJSONObject("query");
                String createt=qur.getString("created");
                h2.setText(createt);
                int h=jsonObject.getInt("ttl");
                h1.setText(h+"f");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url=new URL(strings[0]);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                String NewData = "";
                String lineReader="";
                InputStream inputStream=new BufferedInputStream(httpURLConnection.getInputStream());
                BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                while ((lineReader=reader.readLine())!=null){
                    NewData=lineReader;
                }
                publishProgress(NewData);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
