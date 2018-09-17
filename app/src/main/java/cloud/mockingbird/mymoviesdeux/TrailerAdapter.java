package cloud.mockingbird.mymoviesdeux;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cloud.mockingbird.mymoviesdeux.model.MovieTrailer;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

  private static final String YOUTUBE_URL = "http://img.youtube.com/vi/";
  private static final int TRAILER_TEXT_INDEX = 0;
  private static final int TRAILER_NAME_INDEX = 0;


  private Context context;
  private String[][] trailers;

  final private TrailerAdapter.TrailerAdapterOnClickHandler clickHandler;

  public interface TrailerAdapterOnClickHandler{ void onClick(String[] trailerSelected);}

  public TrailerAdapter(TrailerAdapterOnClickHandler handler) { clickHandler = handler; }

  public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements
      View.OnClickListener{

    ImageView trailerImage;
    TextView trailerName;

    public TrailerAdapterViewHolder(@NonNull View itemView) {
      super(itemView);
      trailerImage = itemView.findViewById(R.id.iv_trailer_image);
      trailerName = itemView.findViewById(R.id.tv_trailer_name);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      int adapterPosition = getAdapterPosition();
      clickHandler.onClick(trailers[adapterPosition]);
    }

//    public void bind(MovieTrailer movieTrailer){
//      trailerMovie = movieTrailer;
//      String id = trailerMovie.getTrailerId();
//      Picasso.get()
//          .load(YOUTUBE_URL + trailerMovie.getTrailerKey() + "/0.jpg")
//          .into(trailerImage);
//      trailerName.setText(trailerMovie.getTrailerName());
//    }

  }

  @NonNull
  @Override
  public TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    int layoutIdForListItem = R.layout.movie_reviews;
    LayoutInflater inflater = LayoutInflater.from(context);
    boolean attachToParentImmediately = false;

    View view = inflater.inflate(layoutIdForListItem, parent, attachToParentImmediately);
    return new TrailerAdapterViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull TrailerAdapterViewHolder holder, int position) {
    String listedTrailers[] = trailers[position];
    holder.trailerName.setText(listedTrailers[1]);
    Picasso.get()
        .load(YOUTUBE_URL + listedTrailers[0])
        .into(holder.trailerImage);
  }

  @Override
  public int getItemCount() {
    if(trailers == null){
      return 0;
    }
    return trailers.length;
  }



  public void updateTrailers(String[][] updatedTrailers){
    trailers = updatedTrailers;
    notifyDataSetChanged();
  }




}
