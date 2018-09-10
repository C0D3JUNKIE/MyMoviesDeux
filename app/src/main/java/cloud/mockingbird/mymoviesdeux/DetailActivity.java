package cloud.mockingbird.mymoviesdeux;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import cloud.mockingbird.mymoviesdeux.utilities.NetworkUtility;

/**
 * Activity class with super class of AppCompatActivity
 */
public class DetailActivity extends AppCompatActivity{

    //Class variable for Image URL
    private static final String IMAGE_URL = "https://image.tmdb.org/t/p/w185";
    private static final String TRAILER_BASE_URL = "http://youtube.com/watch?v=";

    private static final String PARAM_TRAILER_RESULTS = "results";
    private static final String PARAM_TRAILER_KEY = "key";
    private static final String PARAM_TRAILER_NAME = "name";

    //Local variables
    private String[] movie;
    private String[] trailerKeys;
    private String[] trailerNames;

    private ImageView movieImage;

    private TextView movieTitle;
    private TextView movieReleaseDate;
    private TextView movieRating;
    private TextView movieDescription;

    private Button trailerButton;

    private String movie_id;
    private String movie_poster;
    private String movie_title;
    private String movie_description;
    private String movie_release_date;

    private double movie_rating;

    private int movie_review_number;

    private LinearLayout trailerListLayout;

    /**
     * Simple onCreate method for binding view, adapter and xml.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Binding view with xml
        movieImage = findViewById(R.id.iv_movie_poster_image);
        movieTitle = findViewById(R.id.tv_movie_title);
        movieReleaseDate = findViewById(R.id.tv_movie_release_date);
        movieRating = findViewById(R.id.tv_movie_vote_average);
        movieDescription = findViewById(R.id.tv_movie_plot);

        //Explicit intent from startActivity method from MainActivity.onClick
        //id[0], title[1], plot[2], language[3], date[4], image[5], vote[6], rating[7]
        Intent intent = getIntent();
        Bundle movieInfo = intent.getExtras();
        if (movieInfo != null) {
            if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                movie = intent.getStringArrayExtra(Intent.EXTRA_TEXT);
                movieTitle.setText(movie[1]);
                movieReleaseDate.setText(movie[4]);
                movieRating.setText(movie[7]);
                movieDescription.setText(movie[2]);
                Picasso.get().load(IMAGE_URL + movie[5]).into(movieImage);
            }
        }

//        new FetchTrailers().execute();

    }

    public void getTrailerData(String trailerDataResponse){
      try{
        JSONObject trailerObject = new JSONObject(trailerDataResponse);
        JSONArray trailerResults = trailerObject.getJSONArray(PARAM_TRAILER_RESULTS);

        trailerKeys = new String[trailerResults.length()];
        trailerNames = new String[trailerResults.length()];

        for(int i = 0; i < trailerResults.length(); i++) {
          trailerKeys[i] = trailerResults.getJSONObject(i).optString(PARAM_TRAILER_KEY);
          trailerNames[i] = trailerResults.getJSONObject(i).optString(PARAM_TRAILER_NAME);
        }
      }catch(JSONException e){
        e.printStackTrace();
      }
    }

    private void loadTrailerData() {
      if (trailerKeys.length == 0) {
        TextView noTrailerText = new TextView(this);
        noTrailerText.setText("There a no trailers for this movie.");
        noTrailerText.setPadding(0, 0, 0, 0);
        trailerListLayout.addView(noTrailerText);
      } else {
        for (int i = 0; i < trailerKeys.length; i++) {
          Button trailerItem = new Button(this);
          trailerItem.setText(trailerNames[i]);
          trailerItem.setPadding(0, 24, 0, 24);
          trailerItem.setTextSize(18);
          final String trailerUrl = TRAILER_BASE_URL + trailerKeys[i];
          trailerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Uri videoLink = Uri.parse(trailerUrl);
              Intent videoIntent = new Intent(Intent.ACTION_VIEW, videoLink);
              if (videoIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(videoIntent);
              }
            }
          });
          trailerListLayout.addView(trailerItem);
        }
      }

    }



    //Activity life cycle support
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    //Activity life cycle support
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

    }

  public class FetchTrailers extends AsyncTask<String, Void, String> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result){
      getTrailerData(result);
      loadTrailerData();
    }

    @Override
    protected String doInBackground(String... strings){
      try{
        URL requestTrailerUrl = NetworkUtility.buildTrailerUrl(DetailActivity.this, movie_id);
        return NetworkUtility.getResponseFromHttpURL(requestTrailerUrl);
      }catch(Exception e){
        e.printStackTrace();
        return null;
      }
    }

  }


}
