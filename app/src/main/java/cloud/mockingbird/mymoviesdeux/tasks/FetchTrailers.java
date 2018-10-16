package cloud.mockingbird.mymoviesdeux.tasks;

import static cloud.mockingbird.mymoviesdeux.DetailActivity.*;

import android.os.AsyncTask;
import cloud.mockingbird.mymoviesdeux.DetailActivity;
import cloud.mockingbird.mymoviesdeux.utilities.JsonUtility;
import cloud.mockingbird.mymoviesdeux.utilities.NetworkUtility;
import java.net.URL;

//public class FetchTrailers extends AsyncTask<String, Void, String> {
//
//  @Override
//  protected void onPreExecute() {
//    super.onPreExecute();
//  }
//
//  @Override
//  protected void onPostExecute(String s) {
//    super.onPostExecute(s);
//  }
//
////  @Override
////  protected String doInBackground(String... strings) {
//////    if (strings.length == 0) {
//////      return null;
//////    }
//////    String params = strings[0];
//////    URL requestTrailerUrl = NetworkUtility.buildTrailerUrl(DetailActivity.context, params);
//////    try {
//////      String trailerResponse = NetworkUtility.getResponseFromHttpURL(requestTrailerUrl);
//////      trailerMovieData = JsonUtility.getTrailerData(trailerResponse);
//////      return trailerMovieData;
//////    } catch (Exception e) {
//////      e.printStackTrace();
//////      return null;
//////    }
////  }
//
//
//}
