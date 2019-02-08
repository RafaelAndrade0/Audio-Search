package com.example.rasilva.audiosearch3.api;

import android.support.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static final String BASE_URL = "https://www.theaudiodb.com/";

    // Uma Instancia no app inteiro
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    // Caso só haja o uso de uma api no app
    @NonNull
    public static IAudioSearchService createAudioSearchService() {
        return retrofit.create(IAudioSearchService.class);
    }

    // Caso haja o uso de mais de uma api no app
    public static <T> T createGenericService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }

//    public Retrofit getAdapter(String url){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(url)
//                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//
//        return retrofit;
//    }

}
