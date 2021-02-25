package com.codershil.newshunt;

public class News {
    private String author , title , url , urlToImage;

    public News(String author, String title, String url, String urlToImage) {
        this.author = author;
        this.title = title;
        this.url = url;
        this.urlToImage = urlToImage;
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
}
