package cloud.mockingbird.mymoviesdeux.model;

import android.graphics.Movie;
import android.os.Parcel;
import android.os.Parcelable;

public class MovieTrailer implements Parcelable {

  private String trailerId;
  private String trailerKey;
  private String trailerName;

  public MovieTrailer(String trailerId, String trailerKey, String trailerName) {
    this.trailerId = trailerId;
    this.trailerKey = trailerKey;
    this.trailerName = trailerName;
  }

  private MovieTrailer(Parcel in){
    String[] trailer = new String[3];
    in.readStringArray(trailer);

    trailerId = trailer[0];
    trailerKey = trailer[1];
    trailerName = trailer[2];

  }

  public String getTrailerId() {
    return trailerId;
  }

  public void setTrailerId(String trailerId) {
    this.trailerId = trailerId;
  }

  public String getTrailerKey() {
    return trailerKey;
  }

  public void setTrailerKey(String trailerKey) {
    this.trailerKey = trailerKey;
  }

  public String getTrailerName() {
    return trailerName;
  }

  public void setTrailerName(String trailerName) {
    this.trailerName = trailerName;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeStringArray(new String[]{ trailerId, trailerKey, trailerName });
  }

  public static final Creator<MovieTrailer> CREATOR = new Creator<MovieTrailer>(){
    @Override
    public MovieTrailer createFromParcel(Parcel source) {
      return new MovieTrailer(source);
    }

    @Override
    public MovieTrailer[] newArray(int size) {
      return new MovieTrailer[size];
    }
  };

}
