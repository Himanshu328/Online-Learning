package com.sgnr.sgnrclasses.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit retrofit = null;
    private static final String BASEURL = "https://sgnr.000webhostapp.com/Users/";

    public static Retrofit getClient() {
        if(retrofit == null){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder().
                    addInterceptor(httpLoggingInterceptor).
                    build();
            Gson gson = new GsonBuilder().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
        }

        return retrofit;
    }
}
