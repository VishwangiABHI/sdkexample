package com.example.examplelibrary;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;

public class RetrofitService {

    private static Retrofit retrofit, retrofitUnencrypted;
    private static OkHttpClient.Builder okHttpClientBuilder;
    private static OkHttpClient okHttpClient;
    private static HttpLoggingInterceptor interceptor;


    public static Retrofit createService() {

        if (null == interceptor) {
            interceptor = new HttpLoggingInterceptor();
        }
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (okHttpClientBuilder == null) {
            okHttpClientBuilder = new OkHttpClient.Builder();
            okHttpClientBuilder.addInterceptor(interceptor);
           // okHttpClientBuilder.addNetworkInterceptor(new StethoInterceptor());
        }

        if (okHttpClient == null) {
            okHttpClient = okHttpClientBuilder
                  //  .addInterceptor(new HeaderInterceptor_ED_health())
                    .addInterceptor(interceptor)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();
        }

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }


}
