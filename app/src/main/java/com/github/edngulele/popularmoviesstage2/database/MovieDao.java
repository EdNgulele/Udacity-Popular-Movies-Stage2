package com.github.edngulele.popularmoviesstage2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.github.edngulele.popularmoviesstage2.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie ORDER BY vote_average DESC")
    LiveData<List<Movie>> loadAllFavoriteMovies();

    @Query("SELECT is_favorite FROM movie WHERE movie_id= :movie_id")
    boolean isFavorite(int movie_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteMovie(Movie movie);

    @Query("UPDATE movie SET is_favorite = :is_favorite WHERE movie_id = :movie_id")
    void updateFavoriteMovies(int movie_id, boolean is_favorite);

    @Delete
    void deleteFavoriteMovie(Movie movie);

    @Query("SELECT * FROM movie WHERE movie_id = :movie_id")
    Movie getMovie(int movie_id);
}
