package com.codershil.newshunt.models;

public class News {
    private String author , title , url , urlToImage;
    private int id ;

    public News(String author, String title, String url, String urlToImage) {
        this.author = author;
        this.title = title;
        this.url = url;
        this.urlToImage = urlToImage;
    }
    public News(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }
}
