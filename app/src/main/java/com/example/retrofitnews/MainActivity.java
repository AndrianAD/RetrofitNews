package com.example.retrofitnews;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.retrofitnews.Adapter.RecyclerAdapter;
import com.example.retrofitnews.Model.News;
import com.example.retrofitnews.Retrofit.GoRetrofit;
import com.example.retrofitnews.Retrofit.NewsAPI;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    android.app.AlertDialog dialog;
    NewsAPI newsAPI;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout=findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews(true);
            }
        });

        Paper.init(this);
        newsAPI = GoRetrofit.getNewAPI();

        recyclerView = findViewById(R.id.recycle_viewID);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        dialog = new SpotsDialog.Builder().setContext(this).build();
        loadNews(false);

    }

    private void loadNews(boolean isRefreshed) {
        if (!isRefreshed) {
            String cache = Paper.book().read("cache");
            if (cache != null && cache.isEmpty())                    //if have cache
            {
                News news = new Gson().fromJson(cache, News.class);
                adapter = new RecyclerAdapter(news, this);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            } else {
                dialog.show();
                newsAPI.getNews().enqueue(new Callback<News>() {
                    @Override
                    public void onResponse(Call<News> call, Response<News> response) {
                        adapter = new RecyclerAdapter(response.body(), getBaseContext());
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                        //save to cache
                        Paper.book().write("cache", new Gson().toJson(response.body()));
                        dialog.dismiss();

                    }
                    @Override
                    public void onFailure(Call<News> call, Throwable t) {

                    }
                });

            }
        }
        else {
            swipeRefreshLayout.setRefreshing(true);
            newsAPI.getNews().enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    adapter = new RecyclerAdapter(response.body(), getBaseContext());
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    //save to cache
                    Paper.book().write("cache", new Gson().toJson(response.body()));
                  swipeRefreshLayout.setRefreshing(false);

                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {

                }
            });

        }

    }
}
