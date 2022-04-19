/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author fran
 */
public class MovieTableModel extends AbstractTableModel {
    
    private static final String[] COLUMN_NAMES = { "ID", "Title", "Published Date", "Description", "Original Title", "Duration(min)", "Year", "Genre", "Image path", "Rating", "Type", "Page link", "ReservationLink", "StartDate"};
    
    private List<Movie> movies;

    public MovieTableModel(List<Movie> movies) {
        this.movies = movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        //return COLUMN_NAMES.length;
        return Movie.class.getDeclaredFields().length - 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0:
                return movies.get(rowIndex).getId();
            case 1:
                return movies.get(rowIndex).getTitle();
            case 2:
                return movies.get(rowIndex).getPublishedDate().format(Movie.DATE_FORMATTER);
            case 3:
                return movies.get(rowIndex).getDescription();
            case 4:
                return movies.get(rowIndex).getOriginalTitle();
            case 5:
                return movies.get(rowIndex).getDuration();
            case 6:
                return movies.get(rowIndex).getYear();
            case 7:
                return movies.get(rowIndex).getGenre();
            case 8:
                return movies.get(rowIndex).getImagePath();
            case 9:
                return movies.get(rowIndex).getRating();
            case 10:
                return movies.get(rowIndex).getType();
            case 11:
                return movies.get(rowIndex).getLink();
            case 12:
                return movies.get(rowIndex).getReservationLink();
            case 13:
                return Movie.DATE_FORMAT.format(movies.get(rowIndex).getStartDate());
            default:
                return new RuntimeException("No Such column");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return Integer.class;
            case 2:
                return Integer.class;
            case 3:
                return Integer.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }
    
}
