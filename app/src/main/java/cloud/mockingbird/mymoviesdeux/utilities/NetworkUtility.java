package cloud.mockingbird.mymoviesdeux.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import cloud.mockingbird.mymoviesdeux.R;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Class for network communications.
 */
public class NetworkUtility {

    //Class variables for networking methods
    private static final String LOG_TAG = NetworkUtility.class.getSimpleName();
    private static final String BASE_URL = "https://api.themoviedb.org/";
    private static final String VERSION = "3";
    private static final String MOVIE_PARAM = "movie";
    private static final String TRAILER_PARAM = "videos";
    private static final String REVIEW_PARAM = "reviews";
    private static final String DEFAULT_URL = BASE_URL;
    private static final String KEY_PARAM = "api_key";
    private static final String LANG_PARAM = "en-US";
    private static final String PAGE_PARAM = "1";


    /**
     *
     * @param context
     * @param params
     * @return
     */
    public static URL buildMoviesUrl(Context context, String params) {

        Uri builtUri = Uri.parse(DEFAULT_URL)
            .buildUpon()
            .appendPath(VERSION)
            .appendPath(MOVIE_PARAM)
            .appendPath(params)
            .appendQueryParameter(KEY_PARAM, context.getString(
                R.string.movie_db_key))
            .appendQueryParameter("language", LANG_PARAM)
            .appendQueryParameter("page", PAGE_PARAM)
            .build();
        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(LOG_TAG, "MOVIE URL - Built URI: " + url);

        return url;

    }

    /**
     *
     * @param context
     * @param params
     * @return
     */
    public static URL buildReviewUrl(Context context, String params) {

        Uri builtUri = Uri.parse(DEFAULT_URL)
            .buildUpon()
            .appendPath(VERSION)
            .appendPath(MOVIE_PARAM)
            .appendPath(params)
            .appendPath(REVIEW_PARAM)
            .appendQueryParameter(KEY_PARAM, context.getString(
                R.string.movie_db_key))
            .build();
        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(LOG_TAG, "REVIEW URL - Built URI " + url);

        return url;

    }

    /**
     *
     * @param context
     * @param params
     * @return
     */
    public static URL buildTrailerUrl(Context context, String params) {

        Uri builtUri = Uri.parse(DEFAULT_URL)
            .buildUpon()
            .appendPath(VERSION)
            .appendPath(MOVIE_PARAM)
            .appendPath(params)
            .appendPath(TRAILER_PARAM)
            .appendQueryParameter(KEY_PARAM, context.getString(
                R.string.movie_db_key))
            .build();
        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(LOG_TAG, "Built URI " + url);

        return url;

    }

    /**
     * Please note these methods are cookie cutter methods from Google/Udacity's Sunshine Project.
     *
     * @param url URL to fetch response from
     * @return Contents of HTTP response
     * @throws IOException For network response
     */
    public static String getResponseFromHttpURL(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasData = scanner.hasNext();
            if (hasData) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }

}