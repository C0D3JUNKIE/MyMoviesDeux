package cloud.mockingbird.mymoviesdeux;

import android.os.AsyncTask;
import android.view.View;

import java.net.URL;

import cloud.mockingbird.mymoviesdeux.utilities.JsonUtility;
import cloud.mockingbird.mymoviesdeux.utilities.NetworkUtility;

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
    URL movieURL = NetworkUtility.buildUrl(MainActivity.this, params);
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