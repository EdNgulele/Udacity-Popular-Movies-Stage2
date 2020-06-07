package com.github.edngulele.popularmoviesstage2.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.edngulele.popularmoviesstage2.R;
import com.github.edngulele.popularmoviesstage2.model.Trailer;

import java.util.List;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.ViewHolder> {
    List<Trailer> trailers;
    public static Context context;

    public MovieTrailerAdapter(List<Trailer> trailers, Context context) {
        this.trailers = trailers;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_movie_trailer, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(videoImagePath(trailers.get(position).getKey()))
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView thumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.iv_movieTrailer_thumbnail);
            thumbnail.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Trailer trailer = trailers.get(getAdapterPosition());
            Intent playVideo = new Intent(Intent.ACTION_VIEW);
            playVideo.setData(Uri.parse(videoPath(trailer.getKey())));
            playVideo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (playVideo.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(playVideo);
            } else {
                Toast.makeText(context, "Error playing the video", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static String videoPath(String path) {
        return context.getString(R.string.youtube_base_url_video)
                + path;
    }

    public static String videoImagePath(String path) {
        Log.d("MoviePath", R.string.youtube_base_url_image + path
                + R.string.youtube_image_extension);

//        return R.string.youtube_base_url_image + path
//                + R.string.youtube_image_extension;

        return "https://img.youtube.com/vi/" + path
                + "/mqdefault.jpg";
    }
}
