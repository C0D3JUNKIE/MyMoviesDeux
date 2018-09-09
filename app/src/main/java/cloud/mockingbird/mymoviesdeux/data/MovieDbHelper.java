package cloud.mockingbird.mymoviesdeux.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.Provider;

public class MovieDbHelper extends SQLiteOpenHelper {

  public static final String DATABASE_NAME = "movies.db";
  private static int DATABASE_VERSION = 1;
  public static final String TABLE_NAME = "movies";

  public MovieDbHelper(Context context){
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    final String SQL_CREATE_MOVIES_TABLE = "CREATE_TABLE " + TABLE_NAME + " (" +
        MovieProvider.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        MovieProvider.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
        MovieProvider.COLUMN_TITLE + " TEXT NOT NULL, " +
        MovieProvider.COLUMN_DESCRIPTION + " TEXT, " +
        MovieProvider.COLUMN_POSTER + " TEXT, " +
        MovieProvider.COLUMN_RATING + " REAL, " +
        MovieProvider.COLUMN_RELEASE_DATE + " TEXT);";
    sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    onCreate(sqLiteDatabase);
  }
}
