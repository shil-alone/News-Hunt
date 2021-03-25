package com.codershil.newshunt.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import com.codershil.newshunt.models.News;
import com.codershil.newshunt.params.Params;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyDbHandler extends SQLiteOpenHelper {
    Context mContext;
    public MyDbHandler(Context context){
        super(context, Params.DB_NAME,null,Params.DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + Params.TABLE_NAME + "("
                + Params.KEY_ID + " INTEGER PRIMARY KEY," + Params.KEY_TITLE
                + " TEXT, " + Params.KEY_AUTHOR + " TEXT, "+Params.KEY_URL + " TEXT, "+Params.KEY_URL_TO_IMAGE+" TEXT"+ ")";
        //this runs the sql query
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addNews(News news){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_TITLE, news.getTitle());
        values.put(Params.KEY_AUTHOR, news.getAuthor());
        values.put(Params.KEY_URL, news.getUrl());
        values.put(Params.KEY_URL_TO_IMAGE, news.getUrlToImage());

        db.insert(Params.TABLE_NAME, null, values);
        db.close();
    }


    public ArrayList<News> getAllNews(){
        ArrayList<News> newsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Params.TABLE_NAME;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(select,null);

        if (cursor.moveToFirst()){
            do {
                News news  = new News();
                news.setId(cursor.getInt(0));
                news.setTitle(cursor.getString(1));
                news.setAuthor(cursor.getString(2));
                news.setUrl(cursor.getString(3));
                news.setUrlToImage(cursor.getString(4));
                newsList.add(news);
            }while(cursor.moveToNext());
        }
        return newsList;
    }


    public int updateNews(News news){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.KEY_TITLE, news.getTitle());
        values.put(Params.KEY_AUTHOR, news.getAuthor());
        values.put(Params.KEY_URL, news.getUrl());
        values.put(Params.KEY_URL_TO_IMAGE, news.getUrlToImage());

        return db.update(Params.TABLE_NAME,values,Params.KEY_ID+"=?",
                new String[]{ String.valueOf(news.getId()) }
                ) ;
    }

    public void deleteNewsById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Params.TABLE_NAME,Params.KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteNews(News news){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Params.TABLE_NAME,Params.KEY_ID+"=?",new String[]{String.valueOf(news.getId())});
        db.close();
    }

    public ArrayList<News> deleteAllNews(){
        ArrayList<News> newsList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + Params.TABLE_NAME;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(select,null);
        if (cursor.moveToFirst()){
            do {
                db.delete(Params.TABLE_NAME,Params.KEY_ID+"=?",new String[]{String.valueOf(cursor.getInt(0))});
            }while(cursor.moveToNext());
        }
        return newsList;
    }

    public int getCount(){
        String query = "SELECT * FROM " + Params.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor.getCount();
    }



}
