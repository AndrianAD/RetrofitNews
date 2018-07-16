package com.example.retrofitnews.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.retrofitnews.Detail_NEWS_Activity;
import com.example.retrofitnews.Interface.ItemClickListener;
import com.example.retrofitnews.Model.News;
import com.example.retrofitnews.R;
import com.squareup.picasso.Picasso;


import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
  private News news;
    Context context;


    public RecyclerAdapter(News news,Context context) {
        this.context=context;
        this.news=news;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String UrlToImage=news.getArticles().get(position).getUrlToImage();
        if (UrlToImage==null) {
            Picasso.with(context).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSS6wnfu9372TIPwu3cOcY5-FbxNpSmTMPSfsl2fVWSBRnWxMZ2XQ").into(holder.image);
        } else {
            Picasso.with(context).load(news.getArticles().get(position).getUrlToImage()).into(holder.image);
        }
        holder.name.setText(news.getArticles().get(position).getTitle());
        holder.author.setText(news.getArticles().get(position).getSource().getName());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position, boolean isLongClick) {
                Intent intent= new Intent(context, Detail_NEWS_Activity.class);
                intent.putExtra("url",news.getArticles().get(position).getUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return news.getTotalResults();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name,author;
        CircleImageView image;
        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public ViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.source_name);
            image=view.findViewById(R.id.source_image);
            author=view.findViewById(R.id.source_author);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.OnClick(view,getAdapterPosition(),false);
        }
    }
}