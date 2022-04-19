/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.dal;

import java.util.List;
import java.util.Optional;
import moviemanager.model.Director;
import moviemanager.model.Movie;

/**
 *
 * @author fran
 */
public interface DirectorRepository {
      
    int CreateDirector(Director director) throws Exception;
    void CreateDirectors(List<Director> directors) throws Exception;
    void UpdateDirector(int id, Director data) throws Exception;
    void DeleteDirector (int id) throws Exception;
    Optional<Director> SelectDirector (int id) throws Exception;
    List<Director> SelectDirectors() throws Exception;
    List<Movie> SelectDirectedMovies(int id) throws Exception;
    void DeleteDirectedMovies(int id) throws Exception;
}
