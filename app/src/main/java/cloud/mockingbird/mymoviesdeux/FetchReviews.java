package cloud.mockingbird.mymoviesdeux;


import android.os.AsyncTask;
import java.net.URL;
import cloud.mockingbird.mymoviesdeux.utilities.NetworkUtility;

public class FetchReviews extends AsyncTask<String, Void, String> {

  @Override
  protected void onPostExecute(String result){
    extractReviewData(result);
    loadReviewData();
  }

  @Override
  protected String doInBackground(String... strings){
    try{
      URL requestReviewUrl = NetworkUtility.buildReviewUrl(id);
      return NetworkUtility.getResponseFromHttpUrl(requestTrailerUrl);
    }catch(Exception e){
      e.printStackTrace();
      return null;
    }
  }

}
