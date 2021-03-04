package com.codershil.newshunt.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codershil.newshunt.R;
import com.codershil.newshunt.interfaces.NewsItemClicked;
import com.codershil.newshunt.models.News;

import java.util.ArrayList;

public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsHolder> {

    NewsItemClicked listener;
    ArrayList<News> newsList = new ArrayList<>();

    public SavedNewsAdapter(NewsItemClicked listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SavedNewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_news_item,parent,false);
        SavedNewsHolder savedNewsHolder = new SavedNewsHolder(view);
        ImageView newsImage  = savedNewsHolder.newsImage;
        ImageView btnShare = savedNewsHolder.btnShare;
        ImageView btnDelete = savedNewsHolder.btnDelete;
        newsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.newsImageClicked(newsList.get(savedNewsHolder.getAdapterPosition()));
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.shareButtonClicked(newsList.get(savedNewsHolder.getAdapterPosition()));
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(), "delete button is clicked", Toast.LENGTH_SHORT).show();
            }
        });
        return savedNewsHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull SavedNewsHolder holder, int position) {
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

class SavedNewsHolder extends RecyclerView.ViewHolder{
    TextView txtTitle ,txtAuthor , txtShare;
    ImageView newsImage , btnShare ,btnDelete;
    public SavedNewsHolder(@NonNull View itemView) {
        super(itemView);

        txtAuthor = itemView.findViewById(R.id.txtAuthor);
        txtShare = itemView.findViewById(R.id.txtShare);
        txtTitle = itemView.findViewById(R.id.txtTitle);
        newsImage = itemView.findViewById(R.id.img_news);
        btnShare = itemView.findViewById(R.id.btnShare);
        btnDelete = itemView.findViewById(R.id.btnDelete);
    }

}
