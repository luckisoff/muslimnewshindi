package com.example.safi.muslimissues.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.safi.muslimissues.Utils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;


    public static Boolean hasNetwork(Context context) {
        Boolean isConnected = false; // Initial Value
        if (context!=null){
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnected())
                isConnected = true;
        }
        return isConnected;
    }





    public static Retrofit getCacheEnabledRetrofit(final Context context) {


        Interceptor onLineInterceptor= chain -> {
            Response response=chain.proceed(chain.request());
            int maxAge=60*60;
            return response.newBuilder()
                    .header("Cache-Control","public, max-age="+maxAge)
                    .removeHeader("Pragma")
                    .build();
        };

        Interceptor offLineInterceptor= chain -> {
            Request request=chain.request();
            if (!hasNetwork(context)){
                int maxStale = 60 * 60 * 24 * 7; // Offline cache available for 7 days
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
            return chain.proceed(request);
        };




        if (retrofit == null) {

            Cache cache=new Cache(context.getCacheDir(),5 * 1024 * 1024);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(offLineInterceptor)
                    .addNetworkInterceptor(onLineInterceptor)
                    .cache(cache)
                    .build();

            Retrofit.Builder builder=new Retrofit.Builder()
                                .baseUrl(Utils.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .client(okHttpClient);
            retrofit=builder.build();
    }
        return retrofit;
    }
}

