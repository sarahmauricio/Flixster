package com.example.flixsterreal.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.Layout;
import android.text.method.MovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.flixsterreal.DetailActivity;
import com.example.flixsterreal.MainActivity;
import com.example.flixsterreal.R;
import com.example.flixsterreal.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }


    //inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewholder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Populates data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewholder " + position);
        // get the movie at the position
        Movie movie= movies.get(position);
        //bind the movie data into the view holder
        holder.bind(movie);
    }

    // Returns total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout container;
        TextView  tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageURL;
            // if phone is in landscape
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageURL = movie.getBackdropPath();
            } else
                imageURL = movie.getPosterPath();

            int radius = 30; // corner radius, higher value = more rounded
            int margin = 0;

            Glide.with(context)
                    .load(imageURL)
                    .placeholder(R.drawable.ic_launcher_foreground).dontAnimate().dontTransform()
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivPoster);
            //register click listener on whole container
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // navigate to new activity on click
                    Intent i = new Intent(context, DetailActivity.class);
                    // put "extras" into the bundle for access in the second activity
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }
}
