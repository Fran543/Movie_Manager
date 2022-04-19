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
import static java.util.Arrays.asList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import moviemanager.dal.ActorRepository;
import moviemanager.dal.DirectorRepository;
import moviemanager.dal.MovieRepository;
import moviemanager.dal.RepositoryFactory;
import moviemanager.model.Actor;
import moviemanager.model.Director;
import moviemanager.model.Movie;
import moviemanager.parsers.rss.MovieParser;

/**
 *
 * @author fran
 */
public class MovieSQLRepository implements MovieRepository {
    
    private static  DirectorRepository directorRepository;
    private static  MovieRepository movieRepository;
    private static  ActorRepository actorRepository;


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
    private static final String ID_DIRECTOR = "IdDirector";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    
    private static final String CREATE_MOVIE = "{ CALL createMovie (?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String UPDATE_MOVIE = "{ CALL updateMovie (?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String DELETE_MOVIE = "{ CALL deleteMovie (?)}";
    private static final String SELECT_MOVIE = "{ CALL selectMovie (?)}";
    private static final String SELECT_MOVIES = "{ CALL selectMovies }";
    private static final String SELECT_MOVIE_ACTORS = "{ CALL selectMovieActors (?)}";
    private static final String SELECT_MOVIE_DIRECTORS = "{ CALL selectMovieDirectors (?)}";
    private static final String INSERT_ACTOR_IN_MOVIE = "{ CALL InsertActorInMovie (?,?)}";
    private static final String INSERT_DIRECTOR_IN_MOVIE = "{ CALL InsertDirectorInMovie (?,?)}";
    private static final String DELETE_ACTOR_FROM_MOVIE = "{ CALL deleteActorFromMovie (?,?)}";
    private static final String DELETE_DIRECTOR_FROM_MOVIE = "{ CALL deleteDirectorFromMovie (?,?)}";
    private static final String DELETE_ACTORS_FROM_MOVIE = "{ CALL deleteActorsFromMovie (?)}";
    private static final String DELETE_DIRECTORS_FROM_MOVIE = "{ CALL deleteDirectorsFromMovie (?)}";
    private static final String DELETE_DATA = "{ CALL deleteData }";


    @Override
    public int CreateMovie(Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE)){
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getPublishedDate().format(Movie.DATE_FORMATTER));
            stmt.setString(3, movie.getDescription());
            stmt.setString(4, movie.getOriginalTitle());
            stmt.setInt(5, movie.getDuration());
            stmt.setInt(6, movie.getYear());
            stmt.setString(7, movie.getGenre());
            stmt.setString(8, movie.getImagePath());
            stmt.setInt(9, movie.getRating());
            stmt.setString(10, movie.getType());
            stmt.setString(11, movie.getLink());
            stmt.setString(12, movie.getReservationLink());
            stmt.setString(13, Movie.DATE_FORMAT.format(movie.getStartDate()));
            stmt.registerOutParameter(14, Types.INTEGER);
            
            stmt.executeUpdate();
            HandleDirectorsAndActors(stmt.getInt(14), movie.getDirectors(), movie.getActors());
            return stmt.getInt(14);
        }
    }

    @Override
    public void CreateMovies(List<Movie> movies) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE)){
            
            for (Movie movie : movies) {
                stmt.setString(1, movie.getTitle());
                stmt.setString(2, movie.getPublishedDate().format(Movie.DATE_FORMATTER));
                stmt.setString(3, movie.getDescription());
                stmt.setString(4, movie.getOriginalTitle());
                stmt.setInt(5, movie.getDuration());
                stmt.setInt(6, movie.getYear());
                stmt.setString(7, movie.getGenre());
                stmt.setString(8, movie.getImagePath());
                stmt.setInt(9, movie.getRating());
                stmt.setString(10, movie.getType());
                stmt.setString(11, movie.getLink());
                stmt.setString(12, movie.getReservationLink());
                stmt.setString(13, Movie.DATE_FORMAT.format(movie.getStartDate()));
                stmt.registerOutParameter(14, Types.INTEGER);
                
                stmt.executeUpdate();
                HandleDirectorsAndActors(stmt.getInt(14), movie.getDirectors(), movie.getActors());
            }
        }
    }

    @Override
    public void UpdateMovie(int id, Movie data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_MOVIE)) {
            stmt.setString(1, data.getTitle());
            stmt.setString(2, data.getPublishedDate().format(Movie.DATE_FORMATTER));
            stmt.setString(3, data.getDescription());
            stmt.setString(4, data.getOriginalTitle());
            stmt.setInt(5, data.getDuration());
            stmt.setInt(6, data.getYear());
            stmt.setString(7, data.getGenre());
            stmt.setString(8, data.getImagePath());
            stmt.setInt(9, data.getRating());
            stmt.setString(10, data.getType());
            stmt.setString(11, data.getLink());
            stmt.setString(12, data.getReservationLink());
            stmt.setString(13, Movie.DATE_FORMAT.format(data.getStartDate()));
            stmt.setInt(14, id);
  
            stmt.executeUpdate();
        }
    }

    @Override
    public void DeleteMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_MOVIE)) {
            
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Movie> SelectMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIE)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Movie(
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
        return Optional.empty();
    }

    @Override
    public List<Movie> SelectMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIES);
                ResultSet rs = stmt.executeQuery()) {

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
        return movies;
    }

    @Override
    public List<Actor> SelectMovieActors(int id) throws Exception {
        List<Actor> actors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIE_ACTORS)) {
                stmt.setInt(1, id);
               
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    actors.add(new Actor(
                        rs.getInt(ID_ACTOR),
                        rs.getString(FIRST_NAME),
                        rs.getString(LAST_NAME)));
                }
            }
        }
        return actors;
    }

    @Override
    public List<Director> SelectMovieDirectors(int id) throws Exception {
        List<Director> directors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIE_DIRECTORS)) {
                stmt.setInt(1, id);
               
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    directors.add(new Director(
                        rs.getInt(ID_DIRECTOR),
                        rs.getString(FIRST_NAME),
                        rs.getString(LAST_NAME)));
                }
            }
        }
        return directors;
    }

    @Override
    public void InsertActorInMovie(int movieId, int actorId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(INSERT_ACTOR_IN_MOVIE)) {
            stmt.setInt(1, movieId);
            stmt.setInt(2, actorId);

            stmt.executeUpdate();
        }
    }

    @Override
    public void InsertDirectorInMovie(int movieId, int directorId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(INSERT_DIRECTOR_IN_MOVIE)) {
            stmt.setInt(1, movieId);
            stmt.setInt(2, directorId);

            stmt.executeUpdate();
        }
    }

    @Override
    public void DeleteActorFromMovie(int movieId, int actorId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_ACTOR_FROM_MOVIE)) {
            
            stmt.setInt(1, movieId);
            stmt.setInt(2, actorId);

            stmt.executeUpdate();
        }
    }

    @Override
    public void DeleteDirectorFromMovie(int movieId, int directorId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_DIRECTOR_FROM_MOVIE)) {
            
            stmt.setInt(1, movieId);
            stmt.setInt(2, directorId);

            stmt.executeUpdate();
        }
    }
 
    @Override
    public void DeleteActorsFromMovie(int movieId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_ACTORS_FROM_MOVIE)) {
            
            stmt.setInt(1, movieId);

            stmt.executeUpdate();
        }
    }

    @Override
    public void DeleteDirectorsFromMovie(int movieId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_DIRECTORS_FROM_MOVIE)) {
            
            stmt.setInt(1, movieId);

            stmt.executeUpdate();
        }
    }
    
    @Override
    public void DeleteData() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_DATA)) {

            stmt.executeUpdate();
        }
    } 

    private static void HandleDirectorsAndActors(int movieId, String directors, String actors) {
        String[] fullNames = null;
        if (directors.length() != 0) {
            fullNames = directors.split(",");
            asList(fullNames).forEach(fullName -> {
                
                fullName = fullName.trim();                
                int index = fullName.lastIndexOf(' ');
                Director newDirector = null;
                if (index > 0) {
                    String firstName = fullName.substring(0, index);
                    String lastName = fullName.substring(index + 1);
                    newDirector = new Director(firstName, lastName);
                } else {
                    newDirector = new Director(fullName, " ");
                }
                try {
                    directorRepository = RepositoryFactory.GetDirectorRepository();
                    movieRepository = RepositoryFactory.GetMovieRepository();
                    
                    List<Director> directorsInDatabase = directorRepository.SelectDirectors();
                    int directorId = 0;
                    if (!directorsInDatabase.contains(newDirector)) {
                        directorId = directorRepository.CreateDirector(newDirector);
                    } else {
                        for (int i = 0; i < directorsInDatabase.size(); i++) {
                            if (directorsInDatabase.get(i).equals(newDirector)) {
                                directorId = directorsInDatabase.get(i).getId();
                            }
                        }
                    }
                    movieRepository.InsertDirectorInMovie(movieId, directorId);
                } catch (Exception ex) {
                    Logger.getLogger(MovieParser.class.getName()).log(Level.SEVERE, null, ex);
                }                
            });
        }
        
        if (actors.length() != 0 ) {
            fullNames = actors.split(",");
            asList(fullNames).forEach(fullName -> {
                
                fullName = fullName.trim();                
                int index = fullName.lastIndexOf(' ');
                Actor newActor = null;
                if (index > 0) {
                    String firstName = fullName.substring(0, index);
                    String lastName = fullName.substring(index + 1);
                    newActor = new Actor(firstName, lastName);
                } else {
                    newActor = new Actor(fullName, " ");
                }
                
                try {
                    actorRepository = RepositoryFactory.GetActorRepository();
                    movieRepository = RepositoryFactory.GetMovieRepository();
                    
                    List<Actor> actorsInDatabase = actorRepository.SelectActors();
                    int actorId = 0;
                    if (!actorsInDatabase.contains(newActor)) {
                        actorId = actorRepository.CreateActor(newActor);
                    } else {
                        for (int i = 0; i < actorsInDatabase.size(); i++) {
                            if (actorsInDatabase.get(i).equals(newActor)) {
                                actorId = actorsInDatabase.get(i).getId();
                            }
                        }
                    }
                    movieRepository.InsertActorInMovie(movieId, actorId);
                } catch (Exception ex) {
                    Logger.getLogger(MovieParser.class.getName()).log(Level.SEVERE, null, ex);
                }                
            });
        }
    }
}
