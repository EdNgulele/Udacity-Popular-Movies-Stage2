package com.github.edngulele.popularmoviesstage2.ui;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.edngulele.popularmoviesstage2.R;
import com.github.edngulele.popularmoviesstage2.adapter.MovieMainAdapter;
import com.github.edngulele.popularmoviesstage2.model.Movie;
import com.github.edngulele.popularmoviesstage2.model.MovieResults;
import com.github.edngulele.popularmoviesstage2.networking.MovieService;
import com.github.edngulele.popularmoviesstage2.networking.RetrofitClient;
import com.github.edngulele.popularmoviesstage2.util.OnItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    //Set standard sort mode to Popular
    private static int SORT_MODE = 1;
    private static int PAGE = 1;

    private RecyclerView recyclerView;
    private MovieMainAdapter movieMainAdapter;

    private List<Movie> moviesData;
    private MainViewModel viewModel;

    private Call<MovieResults> movieResultsCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_movies_main);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        //moviesData = new ArrayList<>();
        movieMainAdapter = new MovieMainAdapter(this);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
//        viewModel.getFavoriteMovies().observe(this, favorites -> {
//            if ()
//        });

        loadPage(PAGE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_topRated:
                SORT_MODE = 2;
                setTitle(this.getString(R.string.top_rated));
                loadPage(PAGE);
                break;
            case R.id.action_popular:
                SORT_MODE = 1;
                setTitle(this.getString(R.string.app_name));
                loadPage(PAGE);
                break;
            case R.id.action_favotire:
                viewModel.getFavoriteMovies().observe(this, movies -> {
                    if (!movies.isEmpty()) {
                        setTitle("Favs");
                        movieMainAdapter.setData(movies);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Fav movies", Toast.LENGTH_SHORT).show();
                    }
                });
//                movieMainAdapter.clear();
//                List<Movie> favoriteMovies = viewModel.getFavoriteMovies().getValue();
//                if (favoriteMovies != null && favoriteMovies.size() > 0) {
//                    movieMainAdapter.addAll(favoriteMovies);
//                } else {
//                    Toast.makeText(getApplicationContext(), "No Fav movies", Toast.LENGTH_SHORT).show();
//                }
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void loadPage(final int page) {

        MovieService movieService = RetrofitClient.getRetrofitClient(MainActivity.this);

        switch (SORT_MODE) {
            case 1:
                movieResultsCall = movieService.getPopularMovies(page, MainActivity.this.getString(R.string.apiKey));
                break;
            case 2:
                movieResultsCall = movieService.getTopRatedMovies(page, this.getString(R.string.apiKey));
                break;
        }

        movieResultsCall.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                if (page == 1) {
                    generateData(response.body().getMovies());
                } else {
                    List<Movie> movieList = response.body().getMovies();
                    for (Movie movie : movieList) {
                        moviesData.add(movie);
                        movieMainAdapter.notifyItemInserted(moviesData.size() - 1);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void generateData(List<Movie> movies) {

        movieMainAdapter = new MovieMainAdapter(movies, new OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {
                Log.d("Movie clicked", movie.getTitle());

                //Pass the Movie data to MovieDetailActivity
                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(getApplicationContext().getString(R.string.bundle_key), movie);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(movieMainAdapter);

    }
}
