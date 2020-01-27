package com.example.flixter.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.DetailActivity;
import com.example.flixter.Models.Movie;
import com.example.flixter.R;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> { //base class is abstract class

    Context context;  //used to get info about another part of program
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    //only need to create a specific number of views that fit the screen, onBindViewHolder binds new data to the same views so we don't have to create views for every data element
    //inflates layout from XML file and returns the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter","onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movies,parent,false); //used to create new view(or layout) object from xml file
        return new ViewHolder(movieView);
    }

    //populating data into the item through ViewHolder
    //get movie at a position
    //bind movie data into viewholder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter","onBindViewHolder" + position);
        Movie movie = movies.get(position);
        holder.bind(movie);

    }

    //returns the number of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{ //ViewHolder is representation of each row
        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        ImageView play_icon;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            //binding view ids to attributes of ViewHolder class
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
            play_icon = itemView.findViewById(R.id.play_icon);
        }

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            Glide.with(context).load(movie.getPosterPath()).into(ivPoster);
            if(movie.getRating() > 5.0){               //display icon only if rating > 5
                play_icon.setVisibility(View.VISIBLE);
            }
            else{
                play_icon.setVisibility(View.INVISIBLE);
            }

            //1. register click on whole row

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {  //registering a click on any part of a row
                    //2.navigate to new activity
                    Intent i = new Intent(context, DetailActivity.class);  // intent needed to access new activity from current activity
                    i.putExtra("movie", Parcels.wrap(movie)); //passing movie data to detail activity
                    context.startActivity(i);
                }
            });
        }
    }
}
