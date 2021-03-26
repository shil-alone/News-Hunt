package com.codershil.newshunt.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.codershil.newshunt.interfaces.NewsItemClicked;
import com.codershil.newshunt.R;
import com.codershil.newshunt.models.News;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {

    /**
     * listener : NewsItemClicked interface object
     * newsList : list for saving news objects
     */
    NewsItemClicked listener;
    ArrayList<News> newsList = new ArrayList<>();

    // constructor
    public NewsAdapter(NewsItemClicked listener) {
        this.listener = listener;
    }


    // called when view is created
    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // converting the news_item layout into the view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        NewsHolder newsHolder = new NewsHolder(view);

        // initializing the views
        ImageView newsImage = newsHolder.newsImage;
        ImageView btnShare = newsHolder.btnShare;
        ImageView btnSave = newsHolder.btnSave;

        // setting up onClickListeners on news image and share image and save button
        newsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.newsImageClicked(newsList.get(newsHolder.getAdapterPosition()));
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.shareButtonClicked(newsList.get(newsHolder.getAdapterPosition()));
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.saveButtonClicked(newsList.get(newsHolder.getAdapterPosition()));
            }
        });
        return newsHolder;
    }

    // this method is called when viewHolder is bind on recyclerview
    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        // loading all the data in the news eg title,image,etc
        News news = newsList.get(position);
        holder.txtTitle.setText(news.getTitle());
        holder.txtAuthor.setText("Author : "+news.getAuthor());
        if (news.getUrlToImage().equals("null")){
            holder.newsImage.setImageResource(R.drawable.no_image);
        }
        else {
            Glide.with(holder.itemView.getContext()).load(news.getUrlToImage())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.imgProgressBar1.setVisibility(View.GONE);
                            holder.newsImage.setImageResource(R.drawable.no_image);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.imgProgressBar1.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.newsImage);
        }
    }

    // return the no. of items in the recyclerview
    @Override
    public int getItemCount() {
        return newsList.size();
    }

    // this is the method for passing the data to the adapter
    public void updateNews(ArrayList<News> updatedNews){
        newsList.clear();
        newsList.addAll(updatedNews);
        // this will call all the adapter methods again and refresh recyclerview
        notifyDataSetChanged();
    }
}

class NewsHolder extends RecyclerView.ViewHolder{

    // declaring views
    TextView txtTitle ,txtAuthor , txtShare;
    ImageView newsImage , btnShare ,btnSave;
    ProgressBar imgProgressBar1;
    public NewsHolder(@NonNull View itemView) {
        super(itemView);

        // initializing views
        btnSave = itemView.findViewById(R.id.btnSave);
        txtAuthor = itemView.findViewById(R.id.txtAuthor);
        txtShare = itemView.findViewById(R.id.txtShare);
        txtTitle = itemView.findViewById(R.id.txtTitle);
        newsImage = itemView.findViewById(R.id.img_news);
        btnShare = itemView.findViewById(R.id.btnShare);
        imgProgressBar1 = itemView.findViewById(R.id.imgProgressBar1);

    }
}


