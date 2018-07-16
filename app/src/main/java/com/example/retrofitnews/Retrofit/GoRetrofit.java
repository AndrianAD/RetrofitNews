package com.example.retrofitnews.Retrofit;

public class GoRetrofit {
    private final static String BASE_URL="https://newsapi.org";
    private final String API_KEY="f6b10daf8fcc43efba18176dd0a71bfe";

    public static NewsAPI getNewAPI(){
        return RetrofitSingleton.getInstance(BASE_URL).create(NewsAPI.class);
    }


}
