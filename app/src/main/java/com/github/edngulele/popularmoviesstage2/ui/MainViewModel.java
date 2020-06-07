package com.github.edngulele.popularmoviesstage2.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.github.edngulele.popularmoviesstage2.database.AppRepository;
import com.github.edngulele.popularmoviesstage2.model.Movie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private static final String TAG = MainViewModel.class.getSimpleName();
    private LiveData<List<Movie>> movies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppRepository appRepository = new AppRepository(application);
        Log.d(TAG, "Retrieving tasks from database via ViewModel");
        movies = appRepository.loadAllFavoriteMovies();
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return movies;
    }
}
