package cloud.mockingbird.mymoviesdeux.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MovieProvider extends ContentProvider {

  public static final String PREFIX = "content://";
  public static final String AUTHORITY = "cloud.mockingbird.mymoviesdeux";

  public static final Uri URI_BASE = Uri.parse(PREFIX + AUTHORITY);
  public static final Uri CONTENT_URI = URI_BASE.buildUpon().appendPath(MovieDbHelper.TABLE_NAME).build();

  public static final String COLUMN_ID = "_ID";
  public static final String COLUMN_MOVIE_ID = "movie_id";
  public static final String COLUMN_VOTE_AVERAGE = "vote_average";
  public static final String COLUMN_TITLE = "title";
//  public static final String COLUMN_POPULARITY = "popularity";
  public static final String COLUMN_POSTER = "poster_path";
//  public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
//  public static final String COLUMN_ORIGINAL_TITLE = "original_title";
//  public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
//  public static final String COLUMN_ADULT = "adult";
  public static final String COLUMN_DESCRIPTION = "description";
  public static final String COLUMN_RELEASE_DATE = "release_date";
//  public static final String COLUMN_VIDEOS = "videos";
//  public static final String COLUMN_REVIEWS = "reviews";

  private MovieDbHelper movieDbHelper;


  //Not implemented for this project, no need.
  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    return null;
  }

  @Override
  public boolean onCreate() {
    movieDbHelper = new MovieDbHelper(this.getContext());
    return true;
  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    Cursor cursor;
    cursor = movieDbHelper.getReadableDatabase().query(MovieDbHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
    cursor.setNotificationUri(getContext().getContentResolver(), uri);
    return cursor;
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
    long movieId = db.insert(MovieDbHelper.TABLE_NAME, null, values);
    if(movieId > 0){
      Uri result = ContentUris.withAppendedId(uri, movieId);
      getContext().getContentResolver().notifyChange(result, null);
      return result;
    }else{
      return null;
    }
  }

  @Override
  public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
    final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
    if(null == selection){
      selection = "1";
    }
    int deletedFavorite = db.delete(MovieDbHelper.TABLE_NAME, selection, selectionArgs);
    getContext().getContentResolver().notifyChange(uri, null);
    return deletedFavorite;
  }

  //Not implemented for this project, no need.
  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
    return 0;
  }

}
