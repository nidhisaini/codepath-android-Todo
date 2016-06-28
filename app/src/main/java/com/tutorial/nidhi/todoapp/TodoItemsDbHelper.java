package com.tutorial.nidhi.todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TodoItemsDbHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 7;

    // Database Name
    private static final String DATABASE_NAME = "todoDB7";

    // Contacts table name
    public static final String TABLE_TODO = "todoListTable";

    // Contacts Table Columns names
    public static final String TODO_ID = "_id";
    public static final String TODO_NAME = "todo_name";
    public static final String TODO_DESCRIPTION = "todo_describtion";
    public static final String TODO_PRIORITY = "todo_priority";
    public static final String TODO_CREATED = "todo_created";
    public static final String TODO_DUEDATE = "todo_duedate";

    public static final String[] ALL_COLUMNS =
            {TODO_ID, TODO_NAME, TODO_DESCRIPTION, TODO_PRIORITY, TODO_CREATED, TODO_DUEDATE};


    private static final String CREATE_TODO_TABLE =
            "CREATE TABLE " + TABLE_TODO + " (" +
                    TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TODO_NAME + " TEXT, " +
                    TODO_DESCRIPTION + " TEXT, " +
                    TODO_PRIORITY + " TEXT, " +
                    TODO_CREATED + " TEXT default CURRENT_TIMESTAMP, " +
                    TODO_DUEDATE + " TEXT " +
                    ")";



    public TodoItemsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        // Create tables again
        onCreate(db);
    }



}
