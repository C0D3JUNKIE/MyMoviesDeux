package cloud.mockingbird.mymoviesdeux;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import cloud.mockingbird.mymoviesdeux.adapters.MoviePosterAdapter;
import cloud.mockingbird.mymoviesdeux.data.MoviePreferences;
import cloud.mockingbird.mymoviesdeux.data.MovieProvider;
import cloud.mockingbird.mymoviesdeux.tasks.FetchFavorites;
import cloud.mockingbird.mymoviesdeux.utilities.JsonUtility;
import cloud.mockingbird.mymoviesdeux.utilities.NetworkUtility;


import java.net.URL;

/**
 * MainActivity class with super class of AppCompatActivity and implementing AdapterOnClickHandler
 */
public class MainActivity extends AppCompatActivity implements
    MoviePosterAdapter.MoviePosterAdapterOnClickHandler {

  //Class variables
  private static final String TAG = MainActivity.class.getSimpleName();
  public static final int TEXT_INDEX_ID = 1;
  public static final int IMAGE_INDEX_ID = 5;

  public static TextView errorMessageDisplay;
  public static ProgressBar loadingIndicator;

  //Local Variables
  private MoviePosterAdapter moviePosterAdapter;
  private RecyclerView movieRecyclerView;
  private GridLayoutManager layoutManager;
  private Parcelable moviePostersState;

  private String[] ids;
  private String[] votes;
  private String[] titles;
  private String[] posters;
  private String[] dates;
  private String[] descriptions;


  public static Context context;

  /**
   * Simple onCreate method for binding view, adapter and xml.
   * Call to loadMovies
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //Tie recyclerView, errorText, and progressBar to the xml entity.
    movieRecyclerView = findViewById(R.id.rv_movie_posters);
    errorMessageDisplay = findViewById(R.id.tv_error_message_display);
    loadingIndicator = findViewById(R.id.pb_loading_indicator);

    //Setting up layoutManager params.
    int recyclerViewOrientation = GridLayoutManager.VERTICAL;
    boolean shouldReversLayout = false;

    //Initialize layoutManager.
    layoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.span),
        recyclerViewOrientation,
        shouldReversLayout);
    movieRecyclerView.setLayoutManager(layoutManager);
    movieRecyclerView.setHasFixedSize(true);

    //Tie the adapter to the views
    moviePosterAdapter = new MoviePosterAdapter(this);
    movieRecyclerView.setAdapter(moviePosterAdapter);

    loadingIndicator = findViewById(R.id.pb_loading_indicator);
    loadMovies();
  }

  //Lifecycle support methods
  @Override
  protected void onStart() {
    super.onStart();
  }

  //Lifecycle support methods
  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  //Lifecycle support methods
  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable("movieList", layoutManager.onSaveInstanceState());
  }

  //Lifecycle support methods
  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    moviePostersState = savedInstanceState.getParcelable("movieList");
  }

  //onClick implementation for AdapterOnClickHandler
  @Override
  public void onClick(String[] moviePosterSelected) {
    Context context = this;
    Class destinationClass = DetailActivity.class;
    Intent intentToStartDetailActivity = new Intent(context, destinationClass);
    intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, moviePosterSelected);
    startActivity(intentToStartDetailActivity);
  }

  /**
   * Displays movie data in recycler view.
   */
  public void showMovies() {
    errorMessageDisplay.setVisibility(View.INVISIBLE);
    movieRecyclerView.setVisibility(View.VISIBLE);
  }

  /**
   * Calls showMovies method and gets specified sort and creates nested class object.
   */
  public void loadMovies() {
    showMovies();
    String selectedSort = MoviePreferences.getSortPreferred();
    new FetchMovies().execute(selectedSort);
  }

  /**
   * Displays error message
   */
  public void showErrorMessage() {
    movieRecyclerView.setVisibility(View.INVISIBLE);
    errorMessageDisplay.setVisibility(View.VISIBLE);
  }

  /**
   * Nested class to fetch movie data asynchronously
   * Modeled on Udacity's Sunshine App's FetchWeatherTask
   */
  public class FetchMovies extends AsyncTask<String, Void, String[][]> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      loadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String[][] strings) {
      loadingIndicator.setVisibility(View.INVISIBLE);
      if (strings != null) {
        showMovies();
        moviePosterAdapter.setMoviePosterData(strings);
      } else {
        showErrorMessage();
      }

    }

    @Override
    protected String[][] doInBackground(String... strings) {
      if (strings.length == 0) {
        return null;
      }
      String params = strings[0];
      URL movieURL = NetworkUtility.buildMoviesUrl(MainActivity.this, params);
      try {
        String jsonResponse = NetworkUtility.getResponseFromHttpURL(movieURL);
        String[][] jsonMovieData = JsonUtility.getMoviePosterValuesFromJson(jsonResponse);
        return jsonMovieData;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

  }

  //Boiler plate code from Udacity's Sunshine App
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  //Menu action overflow items switch utilizes menu.xml values for cases
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_by_rating:
        new FetchMovies().execute(MoviePreferences.PREF_SORT_RATING);
        return true;
      case R.id.action_by_popularity:
        new FetchMovies().execute(MoviePreferences.PREF_SORT_POPULARITY);
        return true;
      case R.id.action_favorite:
        showFavorites();
        return true;
      case R.id.action_refresh:
        moviePosterAdapter.setMoviePosterData(null);
        loadMovies();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public boolean onlineCheck(Context context){
    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    if(networkInfo == null){
      return false;
    }else{
      return networkInfo.isConnected();
    }
  }

  private void showFavorites(){
    Uri uri = MovieProvider.CONTENT_URI;
    Cursor cursor = getContentResolver().query(uri, null, null, null, null);

    if(cursor != null && cursor.moveToFirst()){
      int i = 0;
      int length = cursor.getCount();
      //initialize local arrays
      ids = new String[length];
      votes = new String[length];
      titles = new String[length];
      posters = new String[length];
      dates = new String[length];
      descriptions = new String[length];
      //simple loop for assigning db values
      while(!cursor.isAfterLast()){
        ids[i] = cursor.getString(cursor.getColumnIndex(MovieProvider.COLUMN_MOVIE_ID));
        votes[i] = cursor.getString(cursor.getColumnIndex(MovieProvider.COLUMN_VOTE_AVERAGE));
        titles[i] = cursor.getString(cursor.getColumnIndex(MovieProvider.COLUMN_TITLE));
        posters[i] = cursor.getString(cursor.getColumnIndex(MovieProvider.COLUMN_POSTER));
        dates[i] = cursor.getString(cursor.getColumnIndex(MovieProvider.COLUMN_RELEASE_DATE));
        descriptions[i] = cursor.getString(cursor.getColumnIndex(MovieProvider.COLUMN_DESCRIPTION));
        //increment counter
        i++;
        cursor.moveToNext();
      }
      cursor.close();
    }else{
      TextView emptyFavorites = new TextView(this);
      emptyFavorites.setText(getString(R.string.empty_favorites));
      emptyFavorites.setTextSize(18);
      emptyFavorites.setPadding(30,30,0,0);
      movieRecyclerView.removeAllViews();
      movieRecyclerView.addView(emptyFavorites);
    }


  }

}
