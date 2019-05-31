package com.example.safi.muslimissues.api;

import com.example.safi.muslimissues.model.Articles;
import com.example.safi.muslimissues.model.Stories;
import com.example.safi.muslimissues.model.WomenArticle;
import com.example.safi.muslimissues.model.WomenQAnswer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("articles")
    Call<List<Articles>> getAllArticles();

    @GET("stories")
    Call<List<Stories>> getAllStories();

    @GET("womenarticles")
    Call<WomenArticle> getAllWomenArticles(@Query("page") int page);

    @GET("womenqa")
    Call<WomenQAnswer> getAllWomenQA(@Query("page") int page);

}
