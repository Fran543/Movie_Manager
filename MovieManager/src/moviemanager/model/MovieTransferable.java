/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 *
 * @author fran
 */
public class MovieTransferable implements Transferable{
    
    
    public static final DataFlavor MOVIE_FLAVOR = new DataFlavor(Movie.class, "Movie");

    public static final DataFlavor[] SUPPORTED_FLAVORS = {MOVIE_FLAVOR};

    private final Movie movie;

    public MovieTransferable(Movie movie) {
        this.movie = movie;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return SUPPORTED_FLAVORS;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(MOVIE_FLAVOR);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (flavor.equals(MOVIE_FLAVOR)) {
            return movie;
        } 
        throw new UnsupportedFlavorException(flavor);        
    }
}
