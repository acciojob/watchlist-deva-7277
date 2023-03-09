package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MovieRepository {

    private HashMap<String, Movie> movieMap;
    private HashMap<String, Director> directorMap;
    private HashMap<String, List<String>> directorMovieMapping;

    public MovieRepository(List<Movie> movieList, List<Director> directorList, Map<String, String> directorMovieMap) {
        this.movieMap = new HashMap<>();
        this.directorMap = new HashMap<>();
        this.directorMovieMapping = new HashMap<>();
    }

    public void saveMovie(Movie movie) {
        movieMap.put(movie.getName(), movie);
    }

    public void saveDirector(Director director) {
        directorMap.put(director.getName(), director);
    }

    public void saveMovieDirectorPair(String movie, String director) {
        if(movieMap.containsKey(movie) && directorMap.containsKey(director)){
            movieMap.put(movie, movieMap.get(movie));
            directorMap.put(director, directorMap.get(director));
            List<String> currentMovies = new ArrayList<String>();
            if(directorMovieMapping.containsKey(director)) currentMovies = directorMovieMapping.get(director);
            currentMovies.add(movie);
            directorMovieMapping.put(director, currentMovies);
        }
    }

    public Movie findMovie(String movieName) {
        return movieMap.get(movieName);
    }

    public Director findDirector(String directorName) {
        return directorMap.get(directorName);
    }

    public List<String> findMoviesFromDirector(String director) {
        List<String> moviesList = new ArrayList<String>();
        if(directorMovieMapping.containsKey(director)) moviesList = directorMovieMapping.get(director);
        return moviesList;
    }

    public List<String> findAllMovies() {
        List<String> allMovies = new ArrayList<>();
        for(String s: movieMap.keySet()){
            allMovies.add(s);
        }
        return allMovies;
    }
    public void deleteDirector(String director){
        List<String> movies = new ArrayList<String>();
        if(directorMovieMapping.containsKey(director)){
            movies = directorMovieMapping.get(director);
            for(String movie: movies){
                if(movieMap.containsKey(movie)){
                    movieMap.remove(movie);
                }
            }

            directorMovieMapping.remove(director);
        }

        if(directorMap.containsKey(director)){
            directorMap.remove(director);
        }
    }

    public void deleteAllDirector(){
        HashSet<String> moviesSet = new HashSet<String>();

        //directorMap = new HashMap<>();

        for(String director: directorMovieMapping.keySet()){
            for(String movie: directorMovieMapping.get(director)){
                moviesSet.add(movie);
            }
        }

        for(String movie: moviesSet){
            if(movieMap.containsKey(movie)){
                movieMap.remove(movie);
            }
        }
    }
}
