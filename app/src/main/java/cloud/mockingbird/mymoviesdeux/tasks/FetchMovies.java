package cloud.mockingbird.mymoviesdeux.tasks;

import android.os.AsyncTask;
import android.view.View;
import cloud.mockingbird.mymoviesdeux.MainActivity;
import cloud.mockingbird.mymoviesdeux.adapters.MoviePosterAdapter;
import cloud.mockingbird.mymoviesdeux.utilities.JsonUtility;
import cloud.mockingbird.mymoviesdeux.utilities.NetworkUtility;
import java.net.URL;

public class FetchMovies extends AsyncTask<String, Void, String[][]> {

  private MainActivity activity;
  private MoviePosterAdapter adapter;

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    MainActivity.loadingIndicator.setVisibility(View.VISIBLE);
  }

  @Override
  protected void onPostExecute(String[][] strings) {
    MainActivity.loadingIndicator.setVisibility(View.INVISIBLE);
    if (strings != null) {
      activity.showMovies();
      adapter.setMoviePosterData(strings);
    } else {
      activity.showErrorMessage();
    }
  }

  @Override
  protected String[][] doInBackground(String... strings) {
    if (strings.length == 0) {
      return null;
    }
    String params = strings[0];
    URL movieURL = NetworkUtility.buildMoviesUrl(MainActivity.context, params);
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
