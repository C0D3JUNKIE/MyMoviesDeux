package cloud.mockingbird.mymoviesdeux;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import cloud.mockingbird.mymoviesdeux.model.MovieReview;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

    private Context context;
    private String[][] reviews;

    final private ReviewAdapter.ReviewAdapterOnClickHandler clickHandler;

    public interface ReviewAdapterOnClickHandler {
        void onClick(String[] reviewSelected);
    }

    public ReviewAdapter(ReviewAdapterOnClickHandler handler) { clickHandler = handler; }

  public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView tvReviewName;
    //    TextView tvReviewUrl;
    TextView tvReviewContent;
    MovieReview review;

    public ReviewAdapterViewHolder(View itemView) {
      super(itemView);
      tvReviewName = itemView.findViewById(R.id.tv_reviewer_label);
//      tvReviewUrl = itemView.findViewById(R.id.tv_movie_review_title);
      tvReviewContent = itemView.findViewById(R.id.tv_reviewer_text);
      itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
      int adapterPosition = getAdapterPosition();
      clickHandler.onClick(reviews[adapterPosition]);
    }

  }

    @NonNull
    @Override
    public ReviewAdapter.ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_reviews;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParentImmediately = true;

        View view = inflater.inflate(layoutIdForListItem, parent, attachToParentImmediately);
        return new ReviewAdapterViewHolder(view);
    }

//    @Override
//    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewAdapterViewHolder holder,
//                                 int position) {
//        MovieReview movieReview = reviews.get(position);
//        holder.bind(movieReview);
//    }


    @Override
    public void onBindViewHolder(@NonNull ReviewAdapterViewHolder holder, int position) {
        String[] reviewList = reviews[position];
        holder.tvReviewName.setText(reviewList[0]);
        holder.tvReviewContent.setText(reviewList[1]);
    }

    @Override
    public int getItemCount() {
        if(null == reviews){
          return 0;
        }
        return reviews.length;
    }

    public void updateReviews(String[][] updatedReviews) {
        reviews = updatedReviews;
        notifyDataSetChanged();
    }

}
