package com.github.edngulele.popularmoviesstage2.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.edngulele.popularmoviesstage2.R;
import com.github.edngulele.popularmoviesstage2.adapter.MovieReviewAdapter;
import com.github.edngulele.popularmoviesstage2.adapter.MovieTrailerAdapter;
import com.github.edngulele.popularmoviesstage2.database.AppDatabase;
import com.github.edngulele.popularmoviesstage2.model.Movie;
import com.github.edngulele.popularmoviesstage2.model.MovieReview;
import com.github.edngulele.popularmoviesstage2.model.MovieTrailer;
import com.github.edngulele.popularmoviesstage2.model.Review;
import com.github.edngulele.popularmoviesstage2.model.Trailer;
import com.github.edngulele.popularmoviesstage2.networking.MovieService;
import com.github.edngulele.popularmoviesstage2.networking.RetrofitClient;
import com.github.edngulele.popularmoviesstage2.util.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {


    private ImageView backdrop;
    private ImageView poster;
    private ImageView favBtn;
    private TextView title;
    private TextView rating;
    private TextView plot;
    private TextView releaseDate;

    private RecyclerView recyclerView;
    private RecyclerView rv_reviews;
    private MovieTrailerAdapter movieTrailerAdapter;
    private MovieReviewAdapter movieReviewAdapter;
    private List<Trailer> trailers;
    private Call<MovieTrailer> movieTrailerCall;
    private Call<MovieReview> movieReviewCall;
    private MovieService movieService;

    private int movieId;
    private boolean isFavorite;
    private MovieDetailViewModel viewModel;

    private Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        backdrop = findViewById(R.id.iv_movie_backdrop);
        poster = findViewById(R.id.iv_movie_poster_detail);
        title = findViewById(R.id.tv_movie_title);
        rating = findViewById(R.id.tv_movie_rating);
        plot = findViewById(R.id.tv_movie_overview);
        releaseDate = findViewById(R.id.tv_movie_release_date);
        favBtn = findViewById(R.id.im_fav_movie);

        viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        favBtn.setOnClickListener(v -> onFavButtonClicked());

        if (getIntent() != null) {
            if (getIntent().hasExtra(getApplicationContext().getString(R.string.bundle_key))) {
                movie = (Movie) getIntent().getSerializableExtra(getApplicationContext().getString(R.string.bundle_key));
                movieId = movie.getMovie_id();
                AppExecutors.getInstance().diskIO().execute(() -> {
                    isFavorite = viewModel.isFavorite(movieId);
                    if (isFavorite) {
                        movie = AppDatabase.getInstance(this).movieDao().getMovie(movieId);
                        runOnUiThread(() -> favBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_selected)));
                    } else {
                        runOnUiThread(() -> favBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_not_selected)));
                    }
                });
            }
        }

        getMovieData(movie);

        recyclerView = findViewById(R.id.rv_movies_trailer);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        rv_reviews = findViewById(R.id.rv_movie_review);
        rv_reviews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        loadTrailers(movieId);
        loadReviews(movieId);

    }

    private void onFavButtonClicked() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            boolean isFavorite = viewModel.isFavorite(movieId);
            if (isFavorite) {
                viewModel.deleteFavoriteMovie(movie);
                runOnUiThread(() -> {
                    favBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_not_selected));
                    Toast.makeText(this, "Favorite Removed", Toast.LENGTH_SHORT).show();
                });
            } else {
                viewModel.addFavoriteMovie(movie);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Favorite Added", Toast.LENGTH_SHORT).show();
                    favBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_selected));
                });
            }
            viewModel.updateFavoriteMovie(movieId, !isFavorite);
            finish();
        });
    }

    private void getMovieData(Movie movie) {
        Glide.with(this)
                .load(imagePath(movie.getBackdropPath()))
                .into(backdrop);

        Glide.with(this)
                .load(imagePath(movie.getPosterPath()))
                .into(poster);

        title.setText(movie.getTitle());
        rating.setText(String.format("%s/10", movie.getVoteAverage()));
        plot.setText(movie.getOverview());
        releaseDate.setText(movie.getReleaseDate());
    }

    private void loadTrailers(int movieId) {

        if (movieService == null) {
            movieService = RetrofitClient.getRetrofitClient(this);
        }
        movieTrailerCall = movieService.getMovieTrailers(movieId, this.getString(R.string.apiKey));
        movieTrailerCall.enqueue(new Callback<MovieTrailer>() {
            @Override
            public void onResponse(Call<MovieTrailer> call, Response<MovieTrailer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //trailers = response.body().getTrailers();
                    generateData(response.body().getTrailers());
                }
            }

            @Override
            public void onFailure(Call<MovieTrailer> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });

    }


    private void loadReviews(int movieId) {

        if (movieService == null) {
            movieService = RetrofitClient.getRetrofitClient(this);
        }
        movieReviewCall = movieService.getMovieReviews(movieId, this.getString(R.string.apiKey), 1);
        movieReviewCall.enqueue(new Callback<MovieReview>() {
            @Override
            public void onResponse(Call<MovieReview> call, Response<MovieReview> response) {
                if (response.isSuccessful() && response.body() != null) {
                    generateReviews(response.body().getReviews());
                    Log.d("Response", "Successful");
                } else {
                    Log.d("loadReviews", "HERE");
                }
            }

            @Override
            public void onFailure(Call<MovieReview> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                Log.d("Response", "Not Successful");

            }
        });

    }

    public static String imagePath(String path) {
        return "https://image.tmdb.org/t/p/" +
                "w500" +
                path;
    }

    private void generateData(List<Trailer> trailers) {
        movieTrailerAdapter = new MovieTrailerAdapter(trailers, getApplicationContext());
        if (trailers != null) {
            recyclerView.setAdapter(movieTrailerAdapter);
        } else {
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void generateReviews(List<Review> reviews) {
        movieReviewAdapter = new MovieReviewAdapter(reviews, getApplicationContext());
        if (reviews != null) {
            rv_reviews.setAdapter(movieReviewAdapter);
        } else {
            rv_reviews.setVisibility(View.GONE);
            Log.d("Reviews", "Not Visible");

        }
    }

}
