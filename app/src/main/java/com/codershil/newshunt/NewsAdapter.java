package com.codershil.newshunt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {

    NewsItemClicked listener ;
    ArrayList<News> newsList = new ArrayList<>();

    public NewsAdapter(NewsItemClicked listener) {
        this.listener = listener;
    }

    public NewsAdapter() {

    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        NewsHolder newsHolder = new NewsHolder(view);
        ImageView newsImage  = newsHolder.newsImage;
        ImageView btnShare = newsHolder.btnShare;
        newsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.newImageClicked(newsList.get(newsHolder.getAdapterPosition()));
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.shareButtonClicked(newsList.get(newsHolder.getAdapterPosition()));
            }
        });
        return newsHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        News news = newsList.get(position);
        holder.txtTitle.setText(news.getTitle());
        holder.txtAuthor.setText("Author : "+news.getAuthor());
        if (news.getUrlToImage().equals("null")){
            holder.newsImage.setImageResource(R.drawable.no_image);
        }
        else {
            Glide.with(holder.itemView.getContext()).load(news.getUrlToImage()).into(holder.newsImage);
        }

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void updateNews(ArrayList<News> updatedNews){
        newsList.clear();
        newsList.addAll(updatedNews);
        notifyDataSetChanged();
    }
}



class NewsHolder extends RecyclerView.ViewHolder{

    TextView txtTitle ,txtAuthor , txtShare;
    ImageView newsImage , btnShare ;
    public NewsHolder(@NonNull View itemView) {
        super(itemView);

        txtAuthor = itemView.findViewById(R.id.txtAuthor);
        txtShare = itemView.findViewById(R.id.txtShare);
        txtTitle = itemView.findViewById(R.id.txtTitle);
        newsImage = itemView.findViewById(R.id.img_news);
        btnShare = itemView.findViewById(R.id.btnShare);

    }
}

