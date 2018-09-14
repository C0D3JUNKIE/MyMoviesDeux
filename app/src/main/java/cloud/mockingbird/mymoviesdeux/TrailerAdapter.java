package cloud.mockingbird.mymoviesdeux;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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


  private Context context;
  private List<MovieTrailer> trailers = new ArrayList<>();

  final private TrailerAdapter.TrailerAdapterOnClickHandler clickHandler;

  public interface TrailerAdapterOnClickHandler{
    void onClick(MovieTrailer trailer);
  }

  public TrailerAdapter(Context context,
      List<MovieTrailer> trailers,
      TrailerAdapterOnClickHandler clickHandler) {
    this.context = context;
    this.trailers = trailers;
    this.clickHandler = clickHandler;
  }

  @NonNull
  @Override
  public TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull TrailerAdapterViewHolder holder, int position) {
    MovieTrailer movieTrailer = trailers.get(position);
    holder.bind(movieTrailer);
  }

  @Override
  public int getItemCount() {
    if(trailers == null){
      return 0;
    }
    return trailers.size();
  }

  public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements
      View.OnClickListener{

    ImageView trailerImage;
    TextView trailerName;
    MovieTrailer trailerMovie;

    public TrailerAdapterViewHolder(@NonNull View itemView) {
      super(itemView);
      trailerImage = itemView.findViewById(R.id.iv_trailer_image);
      trailerName = itemView.findViewById(R.id.tv_trailer_name);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      MovieTrailer mt = trailers.get(getAdapterPosition());
      clickHandler.onClick(mt);
    }

    public void bind(MovieTrailer movieTrailer){
      trailerMovie = movieTrailer;
      String id = trailerMovie.getTrailerId();
      Picasso.get()
          .load(YOUTUBE_URL + trailerMovie.getTrailerKey() + "/0.jpg")
          .into(trailerImage);
      trailerName.setText(trailerMovie.getTrailerName());
    }

  }

  public void updateTrailers(List<MovieTrailer> updatedTrailers){
    trailers = updatedTrailers;
    notifyDataSetChanged();
  }




}
