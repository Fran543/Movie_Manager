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
import moviemanager.dal.DirectorRepository;
import moviemanager.model.Director;
import moviemanager.model.Movie;

/**
 *
 * @author fran
 */
public class DirectorSQLRepository implements DirectorRepository {

    
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

    private static final String ID_DIRECTOR = "IdDirector";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    
    private static final String CREATE_DIRECTOR = "{ CALL createDirector (?,?,?)}";
    private static final String UPDATE_DIRECTOR = "{ CALL updateDirector (?,?,?)}";
    private static final String DELETE_DIRECTOR = "{ CALL deleteDirector (?)}";
    private static final String SELECT_DIRECTOR = "{ CALL selectDirector (?)}";
    private static final String SELECT_DIRECTORS = "{ CALL selectDirectors }";
    private static final String SELECT_DIRECTED_MOVIES = "{ CALL selectDirectedMovies (?) }";
    private static final String DELETE_DIRECTED_MOVIES = "{ CALL deleteDirectedMovies (?) }";
    
    
    @Override
    public int CreateDirector(Director director) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_DIRECTOR)) {
            stmt.setString(1, director.getFirstName());
            stmt.setString(2, director.getLastName());
            stmt.registerOutParameter(3, Types.INTEGER);
            
            stmt.executeUpdate();
            return stmt.getInt(3);
        }

    }

    @Override
    public void CreateDirectors(List<Director> directors) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try (Connection con = dataSource.getConnection();
            CallableStatement stmt = con.prepareCall(CREATE_DIRECTOR)) {
            
            for (Director director : directors) {
            stmt.setString(1, director.getFirstName());
            stmt.setString(2, director.getLastName());
            stmt.registerOutParameter(3, Types.INTEGER);
            stmt.executeUpdate();
            }            
        }
    }

    @Override
    public void UpdateDirector(int id, Director data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_DIRECTOR)) {
            stmt.setString(1, data.getFirstName());
            stmt.setString(2, data.getLastName());
            stmt.setInt(3, id);
            
            stmt.executeUpdate();
        }
    }

    @Override
    public void DeleteDirector(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_DIRECTOR)) {
            
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Director> SelectDirector(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_DIRECTOR)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Director(
                        rs.getInt(ID_DIRECTOR),
                        rs.getString(FIRST_NAME),
                        rs.getString(LAST_NAME)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Director> SelectDirectors() throws Exception {
        List<Director> directors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_DIRECTORS);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                directors.add(new Director(
                    rs.getInt(ID_DIRECTOR),
                    rs.getString(FIRST_NAME),
                    rs.getString(LAST_NAME)));
            }
        }
        return directors;
    }

    @Override
    public List<Movie> SelectDirectedMovies(int id) throws Exception {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_DIRECTED_MOVIES)) {
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
    public void DeleteDirectedMovies(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_DIRECTED_MOVIES)) {
            
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }


    
}
