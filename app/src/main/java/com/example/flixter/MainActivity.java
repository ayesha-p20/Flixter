package com.example.flixter;
//home screen, code for user interaction
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.Models.Movie;
import com.example.flixter.adapters.MovieAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;


public class MainActivity extends AppCompatActivity {


    //declare variables with diff views
    //static final - variable cannot be changed
    public static final String Now_Playing_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"; //includes api key so no need to pass it as parameter
    public static final String TAG  = "MainActivity"; //for logging

    List<Movie> movies; //different from local variable movies created in Movie class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  //bridging activity and layout
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        //create adapter
        final MovieAdapter movieAdapter = new MovieAdapter(this,movies);
        //set adapter on recyclerview
        rvMovies.setAdapter(movieAdapter);
        //set layout manager on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Now_Playing_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {  //using JSON bec movie db uses it too
                Log.d(TAG, "onSuccess"); //d = debug
                JSONObject jsonObject = json.jsonObject; //referencing jsonObject bec it stores data

                //try-catch bec key  may not exist so parsing would not be possible
                try {
                    JSONArray results = jsonObject.getJSONArray("results"); //retrieving array that contains data and storing it in another array
                    Log.i(TAG,"Results:" + results.toString()); //info log
                    movies.addAll(Movie.fromJsonArray(results)); //passing results to the method fromJsonArray in Movie class, where a Movie object with specific attributes will be created for eac object which will then be stored in another array called movies
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG,"Movies: " + movies.size()); //to ensure data extracted correctly
                } catch (JSONException e) {
                    Log.e(TAG,"Hit json exception",e);
                    //e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG,"onFailure");
            }
        });

        //widget variables referenced using IDs defined in xml file
    }
}
