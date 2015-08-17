package com.example.ashish.imdbsample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;


public class movieDetails extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        try{
            new ImageDownloader(((ImageView) (findViewById(R.id.imageView)))).execute(SearchResults.j.getString("Poster"));
            ((TextView) findViewById(R.id.textView)).setText("Movie name : " +
                    SearchResults.j.getString("Title") + "\n" +
                    "Director : " +
                    SearchResults.j.getString("Director") +
                    "\n" + "Type : "+ SearchResults.j.getString("Type") + "("+SearchResults.j.getString("Genre")+")"
                    +"\n" + "Released : " + SearchResults.j.getString("Released")
                    + "\n" + "IMDB Rating : " + SearchResults.j.getString("imdbRating") +"(" +SearchResults.j.getString("imdbVotes") +" votes)"
                    +"\n"+"Run time : " + SearchResults.j.getString("Runtime")
                    +"\n"+"Rated : " + SearchResults.j.getString("Rated")
                    +"\n" + "Plot : " + SearchResults.j.getString("Plot")
            );
        }
        catch(Exception e){}
        //String imgUrl =
        //new ImageDownloader(imageView).execute(imgUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);
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
    class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public ImageDownloader(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
