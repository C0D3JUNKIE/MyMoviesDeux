package cloud.mockingbird.mymoviesdeux;

import android.os.AsyncTask;

import java.net.URL;

import cloud.mockingbird.mymoviesdeux.utilities.NetworkUtility;

public class FetchTrailers extends AsyncTask<String, Void, String> {

  @Override
  protected void onPostExecute(String result){
    extractTrailerData(result);
    loadTrailerData();
  }

  @Override
  protected String doInBackground(String... strings){
    try{
      URL requestTrailerUrl = NetworkUtility.buildTrailerUrl(id);
      return NetworkUtility.getResponseFromHttpUrl(requestTrailerUrl);
    }catch(Exception e){
      e.printStackTrace();
      return null;
    }
  }

}