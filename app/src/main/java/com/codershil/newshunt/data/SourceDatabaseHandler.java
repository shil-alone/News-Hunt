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
import com.codershil.newshunt.models.Source;
import com.codershil.newshunt.params.Params;
import com.codershil.newshunt.params.SourceParams;

import java.nio.channels.Pipe;
import java.util.ArrayList;

public class SourceDatabaseHandler extends SQLiteOpenHelper {
    /**
     * mContext : context of the activity which will create the database object
     */
    Context mContext;

    //constructor
    public SourceDatabaseHandler(Context context){
        super(context, SourceParams.DB_NAME,null,SourceParams.DB_VERSION);
        mContext = context;
    }

    // method for creating database with given name
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + SourceParams.TABLE_NAME + "("
                + SourceParams.KEY_ID + " INTEGER PRIMARY KEY," + SourceParams.KEY_NAME
                + " TEXT, " + SourceParams.KEY_DESCRIPTION + " TEXT, "+SourceParams.KEY_URL
                + " TEXT, "+SourceParams.KEY_COUNTRY+" TEXT, "
                +SourceParams.KEY_CATEGORY+" TEXT, "+SourceParams.KEY_LANGUAGE+" TEXT"+ ")";
        //this runs the sql query
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // this method adds the source into the database
    public void addSource(Source source){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SourceParams.KEY_NAME, source.getName());
        values.put(SourceParams.KEY_DESCRIPTION, source.getDescription());
        values.put(SourceParams.KEY_URL, source.getUrl());
        values.put(SourceParams.KEY_CATEGORY,source.getCategory());
        values.put(SourceParams.KEY_LANGUAGE,source.getLanguage());
        values.put(SourceParams.KEY_COUNTRY,source.getCountry());

        db.insert(SourceParams.TABLE_NAME, null, values);
        db.close();
    }


    // this method return the list of sources from the database
    public ArrayList<Source> getAllSources(){
        ArrayList<Source> sourceList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + SourceParams.TABLE_NAME;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(select,null);
        // cursor is the pointer to each row in the database
        if (cursor.moveToFirst()){
            do {
                Source source = new Source();
                source.setId(cursor.getInt(0));
                source.setName(cursor.getString(1));
                source.setDescription(cursor.getString(2));
                source.setUrl(cursor.getString(3));
                source.setCategory(cursor.getString(4));
                source.setLanguage(cursor.getString(5));
                source.setCountry(cursor.getString(6));
                sourceList.add(source);
            }while(cursor.moveToNext());
        }
        return sourceList;
    }


    // this method deletes the source by using the id
    public void deleteSourceById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SourceParams.TABLE_NAME,SourceParams.KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }

    // this method deletes the news using source object
    public void deleteSource(Source source){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SourceParams.TABLE_NAME,Params.KEY_ID+"=?",new String[]{String.valueOf(source.getId())});
        db.close();
    }

    // it deletes all the source from the database
    public void deleteAllSources(){
        ArrayList<Source> sourceList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + SourceParams.TABLE_NAME;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(select,null);
        if (cursor.moveToFirst()){
            do {
                db.delete(SourceParams.TABLE_NAME,SourceParams.KEY_ID+"=?",new String[]{String.valueOf(cursor.getInt(0))});
            }while(cursor.moveToNext());
        }
    }

    // return the no. of rows in the database
    public int getCount(){
        String query = "SELECT * FROM " + SourceParams.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor.getCount();
    }



}
