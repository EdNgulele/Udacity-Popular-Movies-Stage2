package com.github.edngulele.popularmoviesstage2.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.github.edngulele.popularmoviesstage2.database.AppRepository;
import com.github.edngulele.popularmoviesstage2.model.Movie;

public class MovieDetailViewModel extends AndroidViewModel {

    private AppRepository appRepository;

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        appRepository = new AppRepository(application);
    }

    public boolean isFavorite(int movie_id) {
        return appRepository.isFavorite(movie_id);
    }

    public void addFavoriteMovie(Movie movie) {
        appRepository.addFavoriteMovies(movie);
    }

    public void updateFavoriteMovie(int movie_id, boolean isFavorite) {
        appRepository.updateFavoriteMovie(movie_id, isFavorite);
    }

    public void deleteFavoriteMovie(Movie movie) {
        appRepository.deleteFavoriteMovie(movie);
    }

}
