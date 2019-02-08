package com.example.rasilva.audiosearch3.api;

import com.example.rasilva.audiosearch3.model.Album;
import com.example.rasilva.audiosearch3.model.Artist;
import com.example.rasilva.audiosearch3.model.TrendingMusic;

public class JSONResponse {
    private TrendingMusic[] trending;
    private Artist[] artists;
    private Album[] album;

    public Album[] getAlbum() {
        return album;
    }
    public Artist[] getArtists() {
        return artists;
    }

    public TrendingMusic[] getTrending() {
        return trending;
    }
}
