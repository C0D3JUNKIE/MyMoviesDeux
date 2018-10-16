package cloud.mockingbird.mymoviesdeux.data;

import android.provider.BaseColumns;

public final class MovieDbContract{

  private MovieDbContract() {}

  public static class MovieEntity implements BaseColumns{
    public static final String TABLE_NAME = "movies";
    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_VOTE_AVERAGE = "vote_average";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_POSTER = "movies";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_RELEASE_DATE = "release_date";
  }

}
