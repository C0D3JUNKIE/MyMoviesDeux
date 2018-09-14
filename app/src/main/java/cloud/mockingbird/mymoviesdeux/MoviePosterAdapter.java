package cloud.mockingbird.mymoviesdeux;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.w3c.dom.Text;

/**
 * Adapter class for RecyclerView implementation.  Modeled on the Udacity's Sunshine projects Forecast Adapter.
 */
public class MoviePosterAdapter extends
    RecyclerView.Adapter<MoviePosterAdapter.MoviePosterAdapterViewHolder> {

  //Class variables
  private static final String MOVIEDB_IMAGE_URL = "https://image.tmdb.org/t/p/w780";

  //Local variables
  private String[][] moviePosterData;
  final private MoviePosterAdapterOnClickHandler clickHandler;

  //Nested interface for onClick implementation
  public interface MoviePosterAdapterOnClickHandler {

    void onClick(String[] moviePosterSelected);
  }

  //Adapter constructor
  public MoviePosterAdapter(MoviePosterAdapterOnClickHandler handler) {
    clickHandler = handler;
  }

  /**
   * AdapterViewHolder Inner Class with super class RecyclerView.ViewHolder with onClickListener interface.
   */
  public class MoviePosterAdapterViewHolder extends RecyclerView.ViewHolder implements
      OnClickListener {

    //Local variables
    ImageView movieImage;
    TextView movieText;

    //AdapterViewHolder constructor
    public MoviePosterAdapterViewHolder(@NonNull View itemView) {
      super(itemView);
      movieImage = itemView.findViewById(R.id.iv_movie_poster);
      movieText = itemView.findViewById(R.id.tv_movie_text);
      itemView.setOnClickListener(this);
    }

    //Implementing onClickListeners onClick method
    @Override
    public void onClick(View view) {
      int adapterPosition = getAdapterPosition();
      clickHandler.onClick(moviePosterData[adapterPosition]);
    }

  }

  //Implementing onCreateViewHolder from RecyclerView.Adapter
  @NonNull
  @Override
  public MoviePosterAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    int layoutIdForListItem = R.layout.movie_poster_item;
    LayoutInflater inflater = LayoutInflater.from(context);
    boolean attachToParentImmediately = false;

    View view = inflater.inflate(layoutIdForListItem, parent, attachToParentImmediately);
    return new MoviePosterAdapterViewHolder(view);
  }

  //Implementing onBindViewHolder from RecyclerView.Adapter utilizing Picasso to display MoviePoster images.
  @Override
  public void onBindViewHolder(@NonNull MoviePosterAdapterViewHolder holder, int position) {
    String listedMovies[] = moviePosterData[position];
    holder.movieText.setText(listedMovies[MainActivity.TEXT_INDEX_ID]);
    Picasso.get()
        .load(MOVIEDB_IMAGE_URL + listedMovies[5])
        .into(holder.movieImage);
  }

  //Implementing getItemCount from RecyclerView.Adapter.
  @Override
  public int getItemCount() {
    if (null == moviePosterData) {
      return 0;
    }
    return moviePosterData.length;
  }

  /**
   * Setting movieData to class variable moviePosterData.
   */
  public void setMoviePosterData(String[][] movieData) {
    moviePosterData = movieData;
    notifyDataSetChanged();
  }

}
