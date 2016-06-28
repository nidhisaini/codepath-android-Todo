package com.tutorial.nidhi.todoapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * ContentProvider for the Todoapp
 */
public class TodoProvider extends ContentProvider {

    private static final String AUTHORITY = "com.tutorial.nidhi.todoapp.todoprovider";
    //name of the table
    private static final String BASE_PATH = "todoListTable";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    // Constant to identify the requested operation
    private static final int TODO = 1;//give me the data
    private static final int TODO_ID = 2;//this will deal with a single record

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final String CONTENT_ITEM_TYPE ="todoItem";
    static {
               uriMatcher.addURI(AUTHORITY, BASE_PATH, TODO);
               uriMatcher.addURI(AUTHORITY, BASE_PATH +  "/#", TODO_ID);
           }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        TodoItemsDbHelper handler = new TodoItemsDbHelper(getContext());
        database = handler.getWritableDatabase();
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        if(uriMatcher.match(uri) == TODO_ID){
            selection = TodoItemsDbHelper.TODO_ID + "=" + uri.getLastPathSegment();
        }

        return database.query(TodoItemsDbHelper.TABLE_TODO, TodoItemsDbHelper.ALL_COLUMNS, selection,
                null, null, null, TodoItemsDbHelper.TODO_CREATED + " DESC");
    }


    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = database.insert(TodoItemsDbHelper.TABLE_TODO, null, values);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return database.delete(TodoItemsDbHelper.TABLE_TODO,selection,selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return database.update(TodoItemsDbHelper.TABLE_TODO, values, selection, selectionArgs);
    }
}
