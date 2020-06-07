package com.github.edngulele.popularmoviesstage2.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.github.edngulele.popularmoviesstage2.model.Movie;
import com.github.edngulele.popularmoviesstage2.util.AppExecutors;

import java.util.List;

public class AppRepository {
    private MovieDao movieDao;
    private AppExecutors appExecutors;
    private LiveData<List<Movie>> movies;

    public AppRepository(Application application) {
        movieDao = AppDatabase.getInstance(application).movieDao();
        movies = movieDao.loadAllFavoriteMovies();
        appExecutors = AppExecutors.getInstance();
    }

    public LiveData<List<Movie>> loadAllFavoriteMovies() {
        return movies;
    }

    public boolean isFavorite(int movie_id) {
        return movieDao.isFavorite(movie_id);
    }

    public void addFavoriteMovies(final Movie movie) {
        appExecutors.diskIO().execute(() -> movieDao.insertFavoriteMovie(movie));
    }

    public void updateFavoriteMovie(int movie_id, boolean isFavorite) {
        appExecutors.diskIO().execute(() -> {
            movieDao.updateFavoriteMovies(movie_id, isFavorite);
        });
    }

    public void deleteFavoriteMovie(Movie movie) {
        appExecutors.diskIO().execute(() -> movieDao.deleteFavoriteMovie(movie));
    }
}
