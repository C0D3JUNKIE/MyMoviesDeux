package cloud.mockingbird.mymoviesdeux;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cloud.mockingbird.mymoviesdeux.model.MovieReview;
import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder>{

  private Context context;
  private List<MovieReview> reviews = new ArrayList<>();

  final private ReviewAdapter.ReviewAdapterOnClickHandler clickHandler;

  public interface ReviewAdapterOnClickHandler{
    void onClick(MovieReview review);
  }

  public ReviewAdapter(Context context,
      List<MovieReview> reviews,
      ReviewAdapterOnClickHandler clickHandler) {
    this.context = context;
    this.reviews = reviews;
    this.clickHandler = clickHandler;
  }

  @NonNull
  @Override
  public ReviewAdapter.ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull ReviewAdapter.ReviewAdapterViewHolder holder,
      int position) {

  }

  @Override
  public int getItemCount() {
    return 0;
  }

  public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder implements
      View.OnClickListener{

    TextView tvReviewName;
    TextView tvReviewUrl;
    TextView tvReviewContent;
    MovieReview review;

    public ReviewAdapterViewHolder(View itemView){
      super(itemView);
      tvReviewName = itemView.findViewById(R.id.tv_reviewer_label);
      tvReviewUrl = itemView.findViewById(R.id.tv_movie_review_title);
      tvReviewContent = itemView.findViewById(R.id.tv_reviewer_text);

      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      MovieReview r = reviews.get(getAdapterPosition());
      clickHandler.onClick(r);
    }

    public void bind(MovieReview individualReview){
      review = individualReview;
      String id = review.getReviewId();
      tvReviewName.setText(review.getReviewAuthor());
      tvReviewUrl.setText(review.getReviewUrl());
      tvReviewContent.setText(review.getReviewContent());

    }

  }

  public void updateReviews(List<MovieReview> updatedReviews){
    reviews = updatedReviews;
    notifyDataSetChanged();
  }

}
