package com.github.edngulele.popularmoviesstage2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.github.edngulele.popularmoviesstage2.R;
import com.github.edngulele.popularmoviesstage2.model.Review;

import java.util.List;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ViewHolder> {

    List<Review> reviews;
    Context context;

    public MovieReviewAdapter(List<Review> reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_movie_review, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Review review = reviews.get(position);
//        boolean expand = review.is


        holder.author.setText(review.getAuthor());
        holder.reviewContent.setText(review.getContent());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout reviewLayout;
        TextView author;
        TextView reviewContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //reviewLayout = itemView.findViewById(R.id.cl_review_layout);
            author = itemView.findViewById(R.id.tv_review_author);
            reviewContent = itemView.findViewById(R.id.tv_movie_review);
        }
    }
}
