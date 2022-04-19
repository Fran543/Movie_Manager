/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.dal;

import moviemanager.dal.sql.ActorSQLRepository;
import moviemanager.dal.sql.DirectorSQLRepository;
import moviemanager.dal.sql.MovieSQLRepository;
import moviemanager.dal.sql.UserSQLRepository;

/**
 *
 * @author fran
 */
public class RepositoryFactory {

    public RepositoryFactory() {
    }
    
    public static ActorRepository GetActorRepository() throws Exception {
        return new ActorSQLRepository();
    }
    
    public static MovieRepository GetMovieRepository() throws Exception {
        return new MovieSQLRepository();
    }
    
    public static DirectorRepository GetDirectorRepository() throws Exception {
        return new DirectorSQLRepository();
    }
    
    public static UserRepository GetUserRepository() throws Exception {
        return new UserSQLRepository();
    }
}
