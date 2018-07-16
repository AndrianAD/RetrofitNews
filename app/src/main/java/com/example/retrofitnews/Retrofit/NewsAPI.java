package com.example.retrofitnews.Retrofit;

import com.example.retrofitnews.Model.News;
import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsAPI {
    @GET("/v2/top-headlines?country=ua&apiKey=f6b10daf8fcc43efba18176dd0a71bfe")
    Call<News> getNews();
}
