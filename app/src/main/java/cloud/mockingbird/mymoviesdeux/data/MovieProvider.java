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
  public static final String COLUMN_TITLE = "title";
  public static final String COLUMN_POSTER = "poster_key";
  public static final String COLUMN_DESCRIPTION = "description";
  public static final String COLUMN_RATING = "rating";
  public static final String COLUMN_RELEASE_DATE = "release_date";

  private MovieDbHelper movieDbHelper;

  @Override
  public boolean onCreate() {
    movieDbHelper = new MovieDbHelper(this.getContext());
    return true;
  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
    Cursor cursor;
    cursor = movieDbHelper.getReadableDatabase().query(movieDbHelper.TABLE_NAME, strings, s, strings1, null, null, s1);
    return cursor;
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    return null;
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
    final SQLiteDatabase database = movieDbHelper.getWritableDatabase();
    long movieId = database.insert(movieDbHelper.TABLE_NAME, null, contentValues);
    if(movieId > 0){
      Uri r = ContentUris.withAppendedId(uri, movieId);
      getContext().getContentResolver().notifyChange(r, null);
      return r;
    }else{
      return null;
    }
  }

  @Override
  public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
    return 0;
  }

  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
    return 0;
  }

}
