/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.dal;

import java.util.List;
import java.util.Optional;
import moviemanager.model.Actor;
import moviemanager.model.Movie;

/**
 *
 * @author fran
 */
public interface ActorRepository {
    
    int CreateActor(Actor actor) throws Exception;
    void CreateActors(List<Actor> actors) throws Exception;
    void UpdateActor(int id, Actor data) throws Exception;
    void DeleteActor (int id) throws Exception;
    Optional<Actor> SelectActor (int id) throws Exception;
    List<Actor> SelectActors() throws Exception;
    List<Movie> SelectActedMovies(int id) throws Exception;
    void DeleteActedMovies(int id) throws Exception;

}
