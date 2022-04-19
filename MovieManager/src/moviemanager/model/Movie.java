/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.model;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author fran
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"title", "publishedDate", "description", "originalTitle",  "directors", "actors", "duration", "year", "genre", "imagePath", "rating", "type", "link", "reservationLink", "startDate"})
public class Movie implements Comparable<Movie>{
    

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.M.yyyy");
    @XmlTransient
    private int id;
    @XmlElement(name = "title")
    private String title;
    @XmlElement(name = "publishedDate")
    private ZonedDateTime publishedDate;
    @XmlElement(name = "description")
    private String description;
    @XmlElement(name = "originalTitle")
    private String originalTitle;
    @XmlElement(name = "directors")
    private String directors;
    @XmlElement(name = "actors")
    private String actors;
    @XmlElement(name = "duration") 
    private int duration;
    @XmlElement(name = "year") 
    private int year;
    @XmlElement(name = "genre") 
    private String genre;
    @XmlElement(name = "imagePath") 
    private String imagePath;
    @XmlElement(name = "rating") 
    private int rating;
    @XmlElement(name = "type") 
    private String type;
    @XmlElement(name = "link")    
    private String link;
    @XmlElement(name = "reservationLink")    
    private String reservationLink;
    @XmlElement(name = "startDate")    
    private Date startDate;

    public Movie() {
    }

    public Movie(String title, ZonedDateTime publishedDate, String description, String originalTitle, int duration, int year, String genre, String imagePath, int rating, String type, String link, String reservationLink, Date startDate) {
        this.title = title;
        this.publishedDate = publishedDate;
        this.description = description;
        this.originalTitle = originalTitle;
        this.duration = duration;
        this.year = year;
        this.genre = genre;
        this.imagePath = imagePath;
        this.rating = rating;
        this.type = type;
        this.link = link;
        this.reservationLink = reservationLink;
        this.startDate = startDate;
    }

    public Movie(int id, String title, ZonedDateTime publishedDate, String description, String originalTitle, int duration, int year, String genre, String imagePath, int rating, String type, String link, String reservationLink, Date startDate) {
        this.id = id;
        this.title = title;
        this.publishedDate = publishedDate;
        this.description = description;
        this.originalTitle = originalTitle;
        this.duration = duration;
        this.year = year;
        this.genre = genre;
        this.imagePath = imagePath;
        this.rating = rating;
        this.type = type;
        this.link = link;
        this.reservationLink = reservationLink;
        this.startDate = startDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(ZonedDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalName) {
        this.originalTitle = originalName;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getReservationLink() {
        return reservationLink;
    }

    public void setReservationLink(String reservationLonk) {
        this.reservationLink = reservationLonk;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public String directorsToStrong(List<Director> directors) {
        String directorsString = "";
        LinkedList<Director> linkedActors = new LinkedList<>(directors);
        for (Director director : linkedActors) {
            if (director != linkedActors.getLast()) {
                directorsString += director.toString() + ", ";
            } else {
                directorsString += director.toString();
            }
        }
        return directorsString;
    }
    
    public String actorsToStrong(List<Actor> actors) {
        String actorsString = "";
        LinkedList<Actor> linkedDirectors = new LinkedList<>(actors);
        for (Actor actor : linkedDirectors) {
            if (actor != linkedDirectors.getLast()) {
                actorsString += actor.toString() + ", ";
            } else {
                actorsString += actor.toString();
            }
        }
        return actorsString;
    }
    
    @Override
    public String toString() {
        return title;
    }

    @Override
    public int compareTo(Movie o) {
        return title.compareTo(o.getTitle());
    }
}
