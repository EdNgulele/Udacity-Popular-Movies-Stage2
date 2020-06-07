package com.github.edngulele.popularmoviesstage2.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "movie")
public class Movie implements Serializable {

    @Ignore
    @SerializedName("popularity")
    @Expose
    private Double popularity;

    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite;

    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    @SerializedName("id")
    @Expose
    private Integer movie_id;

    @Ignore
    @SerializedName("video")
    @Expose
    private Boolean video;

    @Ignore
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @Ignore
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;

    @Ignore
    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    @Ignore
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = null;

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @Ignore
    @SerializedName("adult")
    @Expose
    private Boolean adult;

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    @Expose
    private String overview;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    @Expose
    private String posterPath;


    @Ignore
    public Movie(Double popularity, boolean isFavorite, Integer movie_id, Boolean video, Integer voteCount, Double voteAverage, String title, String releaseDate, String originalLanguage, String originalTitle, List<Integer> genreIds, String backdropPath, Boolean adult, String overview, String posterPath) {
        this.popularity = popularity;
        this.isFavorite = isFavorite;
        this.movie_id = movie_id;
        this.video = video;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.title = title;
        this.releaseDate = releaseDate;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.genreIds = genreIds;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.posterPath = posterPath;
    }

    public Movie(int movie_id, boolean isFavorite, Double voteAverage, String title, String releaseDate, String backdropPath, String overview, String posterPath) {
        this.movie_id = movie_id;
        this.isFavorite = isFavorite;
        this.voteAverage = voteAverage;
        this.title = title;
        this.releaseDate = releaseDate;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.posterPath = posterPath;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Integer movie_id) {
        this.movie_id = movie_id;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

}
