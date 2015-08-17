package com.example.ashish.imdbsample;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    JSONObject j;

    static String[] values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((ProgressBar)(findViewById(R.id.progressBar))).setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    JSONArray s;
    public void onClick(View v) {
        ((ProgressBar)(findViewById(R.id.progressBar))).setVisibility(View.VISIBLE);
        String movieName = ((EditText) findViewById(R.id.editText)).getText().toString().replace(" ", "+");
        //new readJson().execute("http://www.omdbapi.com/?t="+movieName+"&y=&plot=short&r=json");
        new readJson().execute("http://www.omdbapi.com/?s="+movieName+"&r=json");
    }
    private class readJson extends AsyncTask<String,Void,JSONObject>{
        protected JSONObject doInBackground(String... surl) {
            j = new JSONObject();
            InputStream in = null;
            String result = "";
            JSONObject jsonObject = null;


            URL url;
            HttpURLConnection urlConnection;
            try {
                url = new URL(surl[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                return null;
            }

            // Read response to string
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                in.close();
                result = sb.toString();
            } catch (Exception e) {
                return null;
            }

            // Convert string to object
            try {
                jsonObject = new JSONObject(result);
            } catch (JSONException e) {
                return null;
            }
            j= jsonObject;
            return jsonObject;

        }
        protected void onPostExecute(JSONObject page)
        {
            ((ProgressBar)(findViewById(R.id.progressBar))).setVisibility(View.INVISIBLE);
            ((EditText)(findViewById(R.id.editText))).setText("");
            String x = "";
            try
            {
                s = j.getJSONArray("Search");
                for (int i = 0; i < s.length(); i++)
                {
                    x += s.getJSONObject(i).getString("Title")+"("+s.getJSONObject(i).getString("Year")+")";
                    if(i!=s.length()) x+= "\n";
                }
                values = x.split("\n");



                //((TextView) findViewById(R.id.textView)).setText(x);
                //((TextView) findViewById(R.id.textView)).setText("Movie name : " + j.getString("Title") + "\n" + "Director : " + j.getString("Director") + "\n" + "Plot : " + j.getString("Plot") + "\n" + "IMDB Rating : " + j.getString("imdbRating"));
                //Toast.makeText(getApplicationContext(), "name : " + j.getString("Director"), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SearchResults.class);
                startActivity(intent);

            } catch (Exception e) {
                //Toast.makeText(getApplicationContext(), "some error occured : " + e.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    public void click(View v)
    {
        Intent intent = new Intent(MainActivity.this, Credits.class);
        startActivity(intent);
    }

}
  /*  public ArrayList<String> getJson(String url) {
        ArrayList<String> listItems = new ArrayList<String>();

        try {
            URL twitter = new URL(url);
            URLConnection tc = twitter.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(tc.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                JSONArray ja = new JSONArray(line);

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                    listItems.add(jo.getString("Director"));
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "some error here : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return listItems;
    }
}*/
