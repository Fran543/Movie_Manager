/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.dal.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import moviemanager.dal.ActorRepository;
import moviemanager.model.Actor;
import moviemanager.model.Movie;

/**
 *
 * @author fran
 */
public class ActorSQLRepository implements ActorRepository {

    
    private static final String ID_MOVIE = "IdMovie";
    private static final String TITLE = "Title";
    private static final String PUBLISHED_DATE = "PublishedDate";
    private static final String DESCRIPTION = "Description";
    private static final String ORIGINAL_TITLE = "OriginalTitle";
    private static final String DURATION = "Duration";
    private static final String YEAR = "Year";
    private static final String GENRE = "Genre";
    private static final String IMAGE_PATH = "ImagePath";
    private static final String RATING = "Rating";
    private static final String TYPE = "Type";
    private static final String LINK = "PageLink";
    private static final String RESERVATION_LINK = "ReservationLink";
    private static final String START_DATE = "StartDate";

    
    private static final String ID_ACTOR = "IdActor";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    
    private static final String CREATE_ACTOR = "{ CALL createActor (?,?,?)}";
    private static final String UPDATE_ACTOR = "{ CALL updateActor (?,?,?)}";
    private static final String DELETE_ACTOR = "{ CALL deleteActor (?)}";
    private static final String SELECT_ACTOR = "{ CALL selectActor (?)}";
    private static final String SELECT_ACTORS = "{ CALL selectActors }";
    private static final String SELECT_ACTED_MOVIES = "{ CALL selectActedMovies (?) }";
    private static final String DELETE_ACTED_MOVIES = "{ CALL deleteActedMovies (?) }";

    
    
    @Override
    public int CreateActor(Actor actor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_ACTOR)) {
            stmt.setString(1, actor.getFirstName());
            stmt.setString(2, actor.getLastName());
            stmt.registerOutParameter(3, Types.INTEGER);
            
            stmt.executeUpdate();
            return stmt.getInt(3);
        }

    }

    @Override
    public void CreateActors(List<Actor> actors) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(CREATE_ACTOR)) {
            
            for (Actor actor : actors) {
            stmt.setString(1, actor.getFirstName());
            stmt.setString(2, actor.getLastName());
            stmt.registerOutParameter(3, Types.INTEGER);
            
            stmt.executeUpdate();
            }

        }
    }

    @Override
    public void UpdateActor(int id, Actor data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_ACTOR)) {
            stmt.setString(1, data.getFirstName());
            stmt.setString(2, data.getLastName());
            stmt.setInt(3, id);
            
            stmt.executeUpdate();
        }
    }

    @Override
    public void DeleteActor(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_ACTOR)) {
            
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Actor> SelectActor(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_ACTOR)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Actor(
                        rs.getInt(ID_ACTOR),
                        rs.getString(FIRST_NAME),
                        rs.getString(LAST_NAME)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Actor> SelectActors() throws Exception {
        List<Actor> actors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_ACTORS);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                actors.add(new Actor(
                    rs.getInt(ID_ACTOR),
                    rs.getString(FIRST_NAME),
                    rs.getString(LAST_NAME)));
            }
        }
        return actors;
    }

    @Override
    public List<Movie> SelectActedMovies(int id) throws Exception {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_ACTED_MOVIES)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    movies.add(new Movie(
                        rs.getInt(ID_MOVIE),
                        rs.getString(TITLE),
                        ZonedDateTime.parse(rs.getString(PUBLISHED_DATE), Movie.DATE_FORMATTER),
                        rs.getString(DESCRIPTION),
                        rs.getString(ORIGINAL_TITLE),
                        rs.getInt(DURATION),
                        rs.getInt(YEAR),
                        rs.getString(GENRE),
                        rs.getString(IMAGE_PATH),
                        rs.getInt(RATING),
                        rs.getString(TYPE),
                        rs.getString(LINK),
                        rs.getString(RESERVATION_LINK),
                        Movie.DATE_FORMAT.parse(rs.getString(START_DATE))
                    ));
                }
            }
        }
        return movies;
    }

    @Override
    public void DeleteActedMovies(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_ACTED_MOVIES)) {
            
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }
    
}