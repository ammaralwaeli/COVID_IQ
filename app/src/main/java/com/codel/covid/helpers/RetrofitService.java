package com.codel.covid.helpers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitService {

    private static String BASE_URL="https://corona.lmao.ninja/";
    private static Gson gson=new GsonBuilder().setLenient().create();

    public static Gson getGson() {
        return gson;
    }

    public static String getBaseUrl(){
        return BASE_URL;
    }

    private static Retrofit.Builder mBuilder=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(
                    GsonConverterFactory.create(gson)
            );

    private static Retrofit retrofit =
            mBuilder.client(NetworkHandler.getUnsafeOkHttpClient())
            .build();

    public static <S> S cteateService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }


}
