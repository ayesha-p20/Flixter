package com.example.flixter.Models;
//objects of movie class represent each movie
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel

public class Movie {
    int movieID;
    String posterPath;
    String title;
    String overview;
    double rating;
    String language;
    String releaseDate;

    public Movie(){

    }  //empty constructor needed by parcel

    public Movie(JSONObject jsonObject) throws JSONException {  //creating JSONObject to access data
        posterPath = jsonObject.getString("poster_path"); //'poster_path' is the name used in db
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
        language = jsonObject.getString("original_language");
        releaseDate = jsonObject.getString("release_date");
        movieID = jsonObject.getInt("id");
    }

    //creating a list called movies of type Movie in a method that will be invoked by JsonArray
    //can also do this in MainActivity
    //takes movieJsonArray of type JSONArray as argument
    //method responsible for iterating through JSONArray and constructing movie for each element in array

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for(int i = 0;i < movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i))); //creating objects of type Movie using JSONObject in JSONArray and adding these objects to movies
        }
         return movies;
    }

    //creating getters or methods to read data stored in all 3 variables
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating(){return rating;}

    public  String getLanguage(){return language;}

    public String getDate(){return releaseDate;}

    public int getMovieID(){return movieID;}
}
