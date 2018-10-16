package cloud.mockingbird.mymoviesdeux;

import static cloud.mockingbird.mymoviesdeux.data.MovieProvider.COLUMN_MOVIE_ID;
import static cloud.mockingbird.mymoviesdeux.data.MovieProvider.COLUMN_VOTE_AVERAGE;
import static cloud.mockingbird.mymoviesdeux.data.MovieProvider.COLUMN_TITLE;
import static cloud.mockingbird.mymoviesdeux.data.MovieProvider.COLUMN_POSTER;
import static cloud.mockingbird.mymoviesdeux.data.MovieProvider.COLUMN_DESCRIPTION;
import static cloud.mockingbird.mymoviesdeux.data.MovieProvider.COLUMN_RELEASE_DATE;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;

import cloud.mockingbird.mymoviesdeux.adapters.ReviewAdapter;
import cloud.mockingbird.mymoviesdeux.adapters.TrailerAdapter;
import cloud.mockingbird.mymoviesdeux.data.FavoriteProvider;
import cloud.mockingbird.mymoviesdeux.data.MovieProvider;
import cloud.mockingbird.mymoviesdeux.databinding.ActivityDetailBinding;
import cloud.mockingbird.mymoviesdeux.model.MoviePoster;
import cloud.mockingbird.mymoviesdeux.model.MovieReview;

import cloud.mockingbird.mymoviesdeux.utilities.JsonUtility;
import cloud.mockingbird.mymoviesdeux.utilities.NetworkUtility;
import com.squareup.picasso.Picasso;

import java.net.URL;

import cloud.mockingbird.mymoviesdeux.data.MovieDbHelper;


/**
 * Activity class with super class of AppCompatActivity
 */
public class DetailActivity extends AppCompatActivity{

//public class DetailActivity extends AppCompatActivity implements
//    TrailerAdapter.TrailerAdapterOnClickHandler, ReviewAdapter.ReviewAdapterOnClickHandler {

  //Class variable for Image URL
  private static final String IMAGE_URL = "https://image.tmdb.org/t/p/w185";
  private static final String TRAILER_BASE_URL = "http://youtube.com/watch?v=";
  private static final int TRAILER_KEY_INDEX = 1;


  //Local variables
  private ActivityDetailBinding bindingDetails;
  private Context context;
  private Parcelable layoutManagerTrailer;
  private Parcelable layoutManagerReviews;
  private String[] movie;
  private String[][] trailerMovieData;
  private String[][] reviewMovieData;
  private ImageView movieImage;
  private TextView movieTitle;
  private TextView movieReleaseDate;
  private TextView movieRating;
  private TextView movieDescription;

  private double movie_rating;
  private int movie_review_number;
  private MoviePoster moviePoster;
  private TrailerAdapter trailerAdapter;
  private ReviewAdapter reviewAdapter;

  private FloatingActionButton fabFavorite;

  private String movieId;
  private String rating;
  private String title;
  private String posterPath;
  private String description;
  private String releaseDate;


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
    fabFavorite = findViewById(R.id.fab_favorite);

//    bindingDetails = DataBindingUtil.setContentView(this, R.layout.activity_detail);
//    context = getApplicationContext();

//    ActionBar actionBar = getSupportActionBar();
//    if (actionBar != null) {
//      actionBar.setDisplayHomeAsUpEnabled(true);
//    }
//
//    Bundle data = getIntent().getExtras();
//    //Binding view with xml
//    movieId = moviePoster.getMovieId();
//    bindingDetails.tvMovieTitle.setText(moviePoster.getMovieTitle());
//    bindingDetails.tvMovieReleaseDate.setText(moviePoster.getMovieReleaseDate());
//    bindingDetails.tvMovieVoteAverage.setText(moviePoster.getMovieRating());
//    bindingDetails.tvMoviePlot.setText(moviePoster.getMovieDescription());
//    String posterUrl = moviePoster.getMovieImagePath();
//    Picasso.get().load(IMAGE_URL + posterUrl)
//        .into(bindingDetails.ivMoviePosterImage);

//    bindingDetails.rvTrailerView.setLayoutManager(new LinearLayoutManager(this));
//    trailerAdapter = new TrailerAdapter(this);
//    bindingDetails.rvTrailerView.setAdapter(trailerAdapter);
//    bindingDetails.rvTrailerView.setHasFixedSize(true);
//
//    bindingDetails.rvReviewView.setLayoutManager(new LinearLayoutManager(this));
//    reviewAdapter = new ReviewAdapter(this);
//    bindingDetails.rvReviewView.setAdapter(reviewAdapter);
//    bindingDetails.rvReviewView.setHasFixedSize(true);

//    Bundle trailerBundle = new Bundle();
//    trailerBundle.putString("movieId", movieId);


    //OLD CODE PRIOR TO DATA BINDING
    //Explicit intent from startActivity method from MainActivity.onClick
    //id[0], title[1], plot[2], language[3], date[4], image[5], vote[6], rating[7]
    Intent intent = getIntent();

    if (intent != null) {
      if (intent.hasExtra(Intent.EXTRA_TEXT)) {
        movie = intent.getStringArrayExtra(Intent.EXTRA_TEXT);
        movieTitle.setText(movie[1]);
        movieReleaseDate.setText(movie[4]);
        movieRating.setText(movie[7]);
        movieDescription.setText(movie[2]);
        Picasso.get().load(IMAGE_URL + movie[5]).into(movieImage);
      }
    }

    if(movieFavorite(movieId)){
      fabFavorite.setImageResource(R.drawable.ic_heart);
    }
    fabFavorite.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if(movieFavorite(movieId)){
          String locale = COLUMN_MOVIE_ID + " = " + movieId;
          getContentResolver().delete(MovieProvider.CONTENT_URI, locale, null);
          fabFavorite.setImageResource(R.drawable.ic_heart_outline);
          Snackbar.make(v, "The movie was removed to your favorites.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
         }else{
          ContentValues contentValues = new ContentValues();
          contentValues.put(COLUMN_MOVIE_ID, movieId);
          contentValues.put(COLUMN_VOTE_AVERAGE, rating);
          contentValues.put(COLUMN_TITLE, title);
          contentValues.put(COLUMN_POSTER, posterPath);
          contentValues.put(COLUMN_DESCRIPTION, description);
          contentValues.put(COLUMN_RELEASE_DATE, releaseDate);
          getContentResolver().insert(MovieProvider.CONTENT_URI, contentValues);
          fabFavorite.setImageResource(R.drawable.ic_heart);
          Snackbar.make(v, "The movie was added to your favorites.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
      }
    });



  }

  //Activity life cycle support
  @Override
  protected void onSaveInstanceState(Bundle outState) {
//    layoutManagerTrailer = bindingDetails.rvTrailerView.getLayoutManager().onSaveInstanceState();
//    layoutManagerReviews = bindingDetails.rvReviewView.getLayoutManager().onSaveInstanceState();
//    outState.putParcelable("trailers", layoutManagerTrailer);
//    outState.putParcelable("reviews", layoutManagerReviews);
    super.onSaveInstanceState(outState);
  }

  //Activity life cycle support
  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
//    if(savedInstanceState !=null){
//      layoutManagerTrailer = (savedInstanceState).getParcelable("trailers");
//      layoutManagerReviews = (savedInstanceState).getParcelable("reviews");
//    }
    super.onRestoreInstanceState(savedInstanceState);
  }

//  @Override
//  public void onClick(String[] trailer){
//    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(TRAILER_BASE_URL + trailer[TRAILER_KEY_INDEX]));
//    if(intent.resolveActivity(getPackageManager()) != null){
//      startActivity(intent);
//    }else{
//      Toast.makeText(getBaseContext(), "Unable to open trailer url.", Toast.LENGTH_SHORT).show();
//    }
//  }

//  public void onClick(MovieReview review) {
//    Log.d("DetailActivity: REVIEW"," - MOVIE REVIEW CLICKED!!!");
//  }

//  public class FetchTrailers extends AsyncTask<String, Void, String[][]> {
//
//    @Override
//    protected void onPreExecute() {
//      super.onPreExecute();
//    }
//
//    @Override
//    protected void onPostExecute(String[][] result) {
//      if(result != null){
//        trailerAdapter.updateTrailers(result);
//      }else{
//
//      }
//    }
//
//    @Override
//    protected String[][] doInBackground(String... strings) {
//      if (strings.length == 0) {
//        return null;
//      }
//      String params = strings[0];
//      URL requestTrailerUrl = NetworkUtility.buildTrailerUrl(DetailActivity.this, params);
//      try {
//        String trailerResponse = NetworkUtility.getResponseFromHttpURL(requestTrailerUrl);
//        trailerMovieData = JsonUtility.getTrailerData(trailerResponse);
//        return trailerMovieData;
//      } catch (Exception e) {
//        e.printStackTrace();
//        return null;
//      }
//    }
//  }

  public boolean movieFavorite(String id){
    Uri uri = MovieProvider.CONTENT_URI;
    Cursor cursor = getContentResolver().query(uri, null, null, null, null);
    if(cursor != null && cursor.moveToFirst()){
      //move to last
      while(!cursor.isAfterLast()){
        String movieId = cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_ID));
        if(id.equals(movieId)){
          return true;
        }
        cursor.moveToNext();
      }
      cursor.close();
    }
    return false;
  }



}
