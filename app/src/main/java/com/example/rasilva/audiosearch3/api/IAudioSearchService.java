package com.example.rasilva.audiosearch3.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IAudioSearchService {

    // Retorna o top 10 de músicas
    @GET("/api/v1/json/1/trending.php")
    Call<JSONResponse> recuperarTrendingMusic(@Query("country") String country, @Query("type") String type, @Query("format") String format);

    // Pesquisa pelo artista
    @GET("/api/v1/json/195003/search.php")
    Call<JSONResponse>recuperarDadosArtista(@Query("s") String artist);

    // Pesquisa pelo album
    @GET("/api/v1/json/195003/searchalbum.php")
    Call<JSONResponse>recuperarDadosAlbum(@Query("s") String artist);
}
