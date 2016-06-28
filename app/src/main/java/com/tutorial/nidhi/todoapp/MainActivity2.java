package com.tutorial.nidhi.todoapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "MainActivity";
    private static final int ADD_REQUEST_CODE = 500;
    private final int REQUEST_CODE = 20;
    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    private int requestCode;
    private int resultCode;
    private Intent data;
    private TodoItemsDbHelper handler;

    private CursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        cursorAdapter = new TodoCursorAdapter(this, null, 0);

        lvItems = (ListView) findViewById(R.id.lvitems);
        lvItems.setAdapter(cursorAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity2.this, EditItemActivity.class);
                Uri uri = Uri.parse(TodoProvider.CONTENT_URI + "/" + id);
                intent.putExtra(TodoProvider.CONTENT_ITEM_TYPE, uri);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });

        getLoaderManager().initLoader(0, null, this);


    }

    private void insertTodo(String todo, String todoDes, String todoPriority, String todoDueDate) {
        ContentValues values = new ContentValues();
        values.put(TodoItemsDbHelper.TODO_NAME,todo);
        values.put(TodoItemsDbHelper.TODO_DESCRIPTION, todoDes);
        values.put(TodoItemsDbHelper.TODO_PRIORITY, todoPriority);
        values.put(TodoItemsDbHelper.TODO_DUEDATE, todoDueDate);
        Uri todoUri = getContentResolver().insert(TodoProvider.CONTENT_URI, values);

       // Log.d("MainActivity", "Inserted todo" + todoUri.getPathSegments());
    }




    public void onAddItem(View view) {
        Intent intent = new Intent(this, EditItemActivity.class);
        startActivityForResult(intent, ADD_REQUEST_CODE);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, TodoProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_delete_all:
                deleteAllNotes();
                break;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }



    private void restartLoader() {
        getLoaderManager().restartLoader(0, null, this);
    }

    private void deleteAllNotes() {

        DialogInterface.OnClickListener dialogClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        if (button == DialogInterface.BUTTON_POSITIVE) {
                            //Insert Data management code here
                            getContentResolver().delete(TodoProvider.CONTENT_URI, null, null);
                            restartLoader();
                            Toast.makeText(MainActivity2.this,
                                    getString(R.string.all_deleted),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.are_you_sure))
                .setPositiveButton(getString(android.R.string.yes), dialogClickListener)
                .setNegativeButton(getString(android.R.string.no), dialogClickListener)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ADD_REQUEST_CODE && resultCode == RESULT_OK){
            restartLoader();
        }
    }
}
