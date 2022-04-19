/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.dal;

import moviemanager.model.Movie;
import java.util.List;
import java.util.Optional;
import moviemanager.model.Actor;
import moviemanager.model.Director;

/**
 *
 * @author fran
 */
public interface MovieRepository {
    
    int CreateMovie(Movie movie) throws Exception;
    void CreateMovies(List<Movie> movies) throws Exception;
    void UpdateMovie(int id, Movie data) throws Exception;
    void DeleteMovie(int id) throws Exception;
    Optional<Movie> SelectMovie(int id) throws Exception;
    List<Movie> SelectMovies() throws Exception;
    List<Actor> SelectMovieActors(int id) throws Exception;
    List<Director> SelectMovieDirectors(int id) throws Exception;
    void InsertActorInMovie(int movieId, int actorId) throws Exception;
    void InsertDirectorInMovie(int movieId, int directorId) throws Exception;
    void DeleteActorFromMovie(int movieId, int actorId) throws Exception;
    void DeleteDirectorFromMovie(int movieId, int directorId) throws Exception;
    void DeleteActorsFromMovie(int movieId) throws Exception;
    void DeleteDirectorsFromMovie(int movieId) throws Exception;
    void DeleteData() throws Exception;    
}
