package cloud.mockingbird.mymoviesdeux.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class FavoriteProvider extends ContentProvider {

  private MovieDbHelper dbHelper;

  //Not implemented for this project, no need.
  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    return null;
  }

  @Override
  public boolean onCreate() {
    dbHelper = new MovieDbHelper(this.getContext());
    return true;
  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    Cursor cursor;
    cursor = dbHelper.getReadableDatabase().query(MovieDbHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
    cursor.setNotificationUri(getContext().getContentResolver(), uri);
    return cursor;
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    final SQLiteDatabase db = dbHelper.getWritableDatabase();
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
    final SQLiteDatabase db = dbHelper.getWritableDatabase();
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
