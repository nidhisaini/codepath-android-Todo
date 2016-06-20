package com.tutorial.nidhi.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private final int REQUEST_CODE = 20;
    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    private int requestCode;
    private int resultCode;
    private Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateArrayItems();

        lvItems =(ListView) findViewById(R.id.lvitems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText =(EditText) findViewById(R.id.etEditText);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = todoItems.get(position);
                int pos = position;
                Log.i(TAG, String.valueOf(pos));
                /*launch EditItemActivity activities and pass data*/
                Intent intent = new Intent(MainActivity2.this, EditItemActivity.class);
                intent.putExtra("pos", pos);
                intent.putExtra("text", text);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    /*populateArrayItems*/
    private void populateArrayItems() {
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String editedText = data.getExtras().getString("editedText");
            int editedPos = data.getExtras().getInt("editedPos", 60);
            //
            todoItems.remove(editedPos);
            todoItems.add(editedPos, editedText);
            aToDoAdapter.notifyDataSetChanged();
            writeItems();
            // Toast the name to display temporarily on screen
            Toast.makeText(this, editedText, Toast.LENGTH_SHORT).show();
        }
    }

    public void onAddItem(View view) {
        etEditText = (EditText) findViewById(R.id.etEditText);
        String itemText = etEditText.getText().toString();
        aToDoAdapter.add(itemText);
        etEditText.setText("");
        writeItems();
    }

    /*read from txt file -internal storage*/
    private void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*write to txt file- internal storage*/
    private void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
