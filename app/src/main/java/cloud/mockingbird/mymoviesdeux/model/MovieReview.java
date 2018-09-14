package cloud.mockingbird.mymoviesdeux.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieReview implements Parcelable{

  private String reviewId;
  private String reviewAuthor;
  private String reviewUrl;
  private String reviewContent;

  public MovieReview(String reviewId, String reviewAuthor, String reviewUrl,
      String reviewContent) {
    this.reviewId = reviewId;
    this.reviewAuthor = reviewAuthor;
    this.reviewUrl = reviewUrl;
    this.reviewContent = reviewContent;
  }

  private MovieReview(Parcel in){
    reviewId = in.readString();
    reviewAuthor = in.readString();
    reviewUrl = in.readString();
    reviewContent = in.readString();
  }

  public String getReviewId() {
    return reviewId;
  }

  public void setReviewId(String reviewId) {
    this.reviewId = reviewId;
  }

  public String getReviewAuthor() {
    return reviewAuthor;
  }

  public void setReviewAuthor(String reviewAuthor) {
    this.reviewAuthor = reviewAuthor;
  }

  public String getReviewUrl() {
    return reviewUrl;
  }

  public void setReviewUrl(String reviewUrl) {
    this.reviewUrl = reviewUrl;
  }

  public String getReviewContent() {
    return reviewContent;
  }

  public void setReviewContent(String reviewContent) {
    this.reviewContent = reviewContent;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(reviewId);
    dest.writeString(reviewAuthor);
    dest.writeString(reviewUrl);
    dest.writeString(reviewContent);
  }

  public static final Creator<MovieReview> CREATOR = new Creator<MovieReview>() {
    @Override
    public MovieReview createFromParcel(Parcel source) {
      return new MovieReview(source);
    }

    @Override
    public MovieReview[] newArray(int size) {
      return new MovieReview[size];
    }
  };


}
