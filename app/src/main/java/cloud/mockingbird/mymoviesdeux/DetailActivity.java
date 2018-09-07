package cloud.mockingbird.mymoviesdeux;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

/**
 * Activity class with super class of AppCompatActivity
 */
public class DetailActivity extends AppCompatActivity {

    //Class variable for Image URL
    private static final String imageURL = "https://image.tmdb.org/t/p/w185";

    //Local variables
    private String[] movie;
    private ImageView movieImage;
    private TextView movieTitle;
    private TextView movieReleaseDate;
    private TextView movieRating;
    private TextView movieDescription;

    /**
     * Simple onCreate method for binding view, adapter and xml.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Binding view with xml
        movieImage = findViewById(R.id.iv_movie_poster_image);
        movieTitle = findViewById(R.id.tv_movie_title);
        movieReleaseDate = findViewById(R.id.tv_movie_release_date);
        movieRating = findViewById(R.id.tv_movie_vote_average);
        movieDescription = findViewById(R.id.tv_movie_plot);

        //Explicit intent from startActivity method from MainActivity.onClick
        //id[0], title[1], plot[2], language[3], date[4], image[5], vote[6], rating[7]
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                movie = intent.getStringArrayExtra(Intent.EXTRA_TEXT);
                movieTitle.setText(movie[1]);
                movieReleaseDate.setText(movie[4]);
                movieRating.setText(movie[7]);
                movieDescription.setText(movie[2]);
                Picasso.get().load(imageURL + movie[5]).into(movieImage);
            }
        }

    }

    //Activity life cycle support
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    //Activity life cycle support
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

    }


}
