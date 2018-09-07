package cloud.mockingbird.mymoviesdeux.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * MoviePoster class is model for movie objects implementing Parceable
 */
public class MoviePoster implements Parcelable {

    //Local vars
    private String movieTitle;
    private String movieReleaseDate;
    private String movieRating;
    private String movieDescription;
    private String movieImagePath;


    //Constructor
    public MoviePoster(String title, String releaseDate, String rating, String description,
                       String imagePath) {
        movieTitle = title;
        movieReleaseDate = releaseDate;
        movieRating = rating;
        movieDescription = description;
        movieImagePath = imagePath;
    }

    //Parceable constructor
    private MoviePoster(Parcel source) {

        movieTitle = source.readString();
        movieReleaseDate = source.readString();
        movieRating = source.readString();
        movieDescription = source.readString();
        movieImagePath = source.readString();

    }

    //Getters and Setters
    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getMovieImagePath() {
        return movieImagePath;
    }

    public void setMovieImagePath(String movieImagePath) {
        this.movieImagePath = movieImagePath;
    }

    //Cookie cutter CREATOR method for Parcelable implementation
    public static final Parcelable.Creator<MoviePoster> CREATOR = new Parcelable.Creator<MoviePoster>() {

        @Override
        public MoviePoster createFromParcel(Parcel parcel) {
            return new MoviePoster(parcel);
        }

        @Override
        public MoviePoster[] newArray(int i) {
            return new MoviePoster[i];
        }

    };

    //Required for implementing Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    //Required for implementing Parcelable - associating values to parcelable
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieTitle);
        parcel.writeString(movieReleaseDate);
        parcel.writeString(movieRating);
        parcel.writeString(movieDescription);
        parcel.writeString(movieImagePath);

    }

}


