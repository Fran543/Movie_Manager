/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.parsers.rss;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import moviemanager.factory.ParserFactory;
import moviemanager.factory.UrlConnectionFactory;
import moviemanager.model.Movie;
import moviemanager.utils.FileUtils;

/**
 *
 * @author fran
 */
public class MovieParser {

    
    
//    private static final String RSS_URL = "https://www.blitz-cinestar.hr/rss.aspx?id=6";
//    private static final String FILE_PATH = "CinestarXML.xml";
    private static final int TIMEOUT = 10000;
    private static final String REQUEST_METHOD = "GET";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME;
    private static final Random RANDOM = new Random();
    
    public static List<Movie> parse(String url) throws IOException, XMLStreamException {
        List<Movie> movies = new ArrayList<>();
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(url, TIMEOUT, REQUEST_METHOD);
        XMLEventReader reader = ParserFactory.createStaxParser(con.getInputStream());
//        XMLEventReader reader = ParserFactory.createStaxParser(new FileInputStream(FILE_PATH));

        Optional<TagType> tagType = Optional.empty();
        Movie movie = null;
        StartElement startElement = null;
        
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            switch (event.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:
                    startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();
                    tagType = TagType.from(qName);
                    if (tagType.isPresent()) {
                        if (tagType.get() == TagType.ITEM) {
                            movie = new Movie();
                            movies.add(movie);
                        }
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if (tagType.isPresent()) {
                        Characters characters = event.asCharacters();
                        String data = characters.getData().trim();
                        switch (tagType.get()) {
                            case ITEM:
                                movie = new Movie();
                                movies.add(movie);
                            break;
                            case TITLE:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setTitle(data);
                                }
                                break;
                            case PUBLISHED_DATE:
                                if (movie != null && !data.isEmpty()) {
                                    ZonedDateTime publishedDate = ZonedDateTime.parse(data, DATE_FORMATTER);
                                    movie.setPublishedDate(publishedDate);
                                }
                                break;
                            case DESCRIPTION:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setDescription(handleDescription(data));
                                }
                                break;
                            case ORIGINAL_TITLE:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setOriginalTitle(data);
                                }
                                break;
                            case YEAR:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setYear(Integer.parseInt(data));
                                }
                            break;
                            case DIRECTOR:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setDirectors(data);
                                }
                            break;
                            case ACTORS:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setActors(data);
                                }
                            break;
                            case DURATION:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setDuration(Integer.parseInt(data));
                                }
                            break;
                            case GENRE:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setGenre(data);
                                }
                            break;
                            case IMAGE_PATH:
                                if (movie != null && !data.isEmpty()) {
                                    handlePicture(movie, data);
                                }
                            break;
                            case RATING:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setRating(Integer.parseInt(data));
                                }
                            break;
                            case TYPE:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setType(data);
                                }
                            break;
                            case LINK:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setLink(data);
                                }
                            break;
                            case RESERVATION_LINK:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setReservationLink(data);
                                }
                            break;
                            case START_DATE:
                                if (movie != null && !data.isEmpty()) {
                            try {
                                movie.setStartDate(Movie.DATE_FORMAT.parse(data));
                            } catch (ParseException ex) {
                                Logger.getLogger(MovieParser.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                }
                            break;
                        }
                    }
                break;
            }
        }
        return movies;
    }

    private static void handlePicture(Movie movie, String pictureUrl) throws IOException {

        String ext = pictureUrl.substring(pictureUrl.lastIndexOf("."));
        if (ext.length() > 4) {
            ext = EXT;
        }
        String pictureName = Math.abs(RANDOM.nextInt()) + ext;
        String localPicturePath = DIR + File.separator + pictureName;

        FileUtils.copyFromUrl(pictureUrl, localPicturePath);
        movie.setImagePath(localPicturePath);
    }
    
    private static String handleDescription(String data) {

        String[] dataParts = data.split(">");
        for (int i = 0; i < dataParts.length; i++) {
            if (!dataParts[i + 1].startsWith("<")) {
                String[] dataPart = dataParts[i + 1].split("<");
                String description = dataPart[0].trim();
                return description;
            }
        }
        return null;
    }
        
    private enum TagType {

        ITEM("item"), 
        TITLE("title"), 
        PUBLISHED_DATE("pubDate"), 
        DESCRIPTION("description"), 
        ORIGINAL_TITLE("orignaziv"), 
        DURATION("trajanje"), 
        YEAR("godina"),
        DIRECTOR("redatelj"), 
        ACTORS("glumci"), 
        GENRE("zanr"), 
        IMAGE_PATH("plakat"),
        RATING("rating"),
        TYPE("vrsta"),
        LINK("link"),
        RESERVATION_LINK("rezervacija"),
        START_DATE("datumprikazivanja");

        private final String name;

        private TagType(String name) {
            this.name = name;
        }

        private static Optional<TagType> from(String name) {
            for (TagType value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
    }
}
