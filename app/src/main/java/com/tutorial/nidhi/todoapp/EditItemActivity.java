package com.tutorial.nidhi.todoapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;

public class EditItemActivity extends AppCompatActivity {
    private static final String TAG = "EditItemActivity";
    private String action;
    private String todoListFilter;//where in SQL

    private EditText etEditName;
    private EditText etEditDesc;
    private EditText etEditPriority;
    private EditText etDueDate;

    private String oldNameText;
    private String oldDescText;
    private String oldPriorityText;
    private String oldDueDateText;
    ImageButton imgBtnDueDate;
    int day_x;
    int month_x;
    int year_x;
    static final int DIALOG_ID =0;
    String selectedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etEditName = (EditText) findViewById(R.id.etEditName);
        etEditDesc = (EditText) findViewById(R.id.etEditDesc);
        etEditPriority = (EditText) findViewById(R.id.etEditPriority);
        etDueDate =(EditText) findViewById(R.id.etDueDate);
        imgBtnDueDate =(ImageButton) findViewById(R.id.imgBtnDatePicker);


        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x =cal.get(Calendar.MONTH);
        day_x =cal.get(Calendar.DAY_OF_MONTH);

        imgBtnDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogOnButtonClick();
            }
        });
        etDueDate.setText(selectedDate);

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra(TodoProvider.CONTENT_ITEM_TYPE);

        if(uri == null) {
            action = Intent.ACTION_INSERT;
            setTitle("Add new todo list");
        }else{
            action = Intent.ACTION_EDIT;
            todoListFilter = TodoItemsDbHelper.TODO_ID + "=" + uri.getLastPathSegment();
            Cursor cursor = getContentResolver().query(uri, TodoItemsDbHelper.ALL_COLUMNS, todoListFilter, null, null);
            cursor.moveToFirst();
            oldNameText = cursor.getString(cursor.getColumnIndex(TodoItemsDbHelper.TODO_NAME));
            oldDescText = cursor.getString(cursor.getColumnIndex(TodoItemsDbHelper.TODO_DESCRIPTION));
            oldPriorityText = cursor.getString(cursor.getColumnIndex(TodoItemsDbHelper.TODO_PRIORITY));
            oldDueDateText =cursor.getString(cursor.getColumnIndex(TodoItemsDbHelper.TODO_DUEDATE));

            etEditName.setText(oldNameText);
            etEditDesc.setText(oldDescText);
            etEditPriority.setText(oldPriorityText);
            etDueDate.setText(oldDueDateText);
            etDueDate.requestFocus();
            etEditPriority.requestFocus();
            etEditDesc.requestFocus();
            etEditName.requestFocus();//move the cursor to the end of the existing text
        }


    }

    private void finishEditing(){
        String newName = etEditName.getText().toString().trim();
        String newDesc = etEditDesc.getText().toString().trim();
        String newPriority =etEditPriority.getText().toString().trim();
        String newDueDate =etDueDate.getText().toString().trim();

        switch (action){
            case Intent.ACTION_INSERT:
                if(newName.length() == 0){
                    setResult(RESULT_CANCELED);
                }
                else {
                       insertNote(newName, newDesc, newPriority, newDueDate);
                }
                break;
            case Intent.ACTION_EDIT:
                if(newName.length() == 0){
                    deleteTodo();
                   }
                else if(oldNameText.equals(newName) && oldDescText.equals(newDesc)
                        && oldPriorityText.equals(newPriority) && oldDueDateText.equals(newDueDate) ){
                     setResult(RESULT_CANCELED);
                }
                else{
                     updateNote(newName, newDesc, newPriority, newDueDate);
                }

        }finish();

    }

    private void updateNote(String todoText, String todoDesc, String todoPriority, String todoDueDate) {
        ContentValues values = new ContentValues();
        values.put(TodoItemsDbHelper.TODO_NAME, todoText);
        values.put(TodoItemsDbHelper.TODO_DESCRIPTION, todoDesc);
        values.put(TodoItemsDbHelper.TODO_PRIORITY, todoPriority);
        values.put(TodoItemsDbHelper.TODO_DUEDATE, todoDueDate);
        getContentResolver().update(TodoProvider.CONTENT_URI, values, todoListFilter, null);
        Toast.makeText(this, R.string.note_updated, Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
    }

    private void insertNote(String todoText, String todoDesc, String todoPriority, String todoDueDate) {
        ContentValues values = new ContentValues();
        values.put(TodoItemsDbHelper.TODO_NAME, todoText);
        values.put(TodoItemsDbHelper.TODO_DESCRIPTION, todoDesc);
        values.put(TodoItemsDbHelper.TODO_PRIORITY, todoPriority);
        values.put(TodoItemsDbHelper.TODO_DUEDATE, todoDueDate);
        getContentResolver().insert(TodoProvider.CONTENT_URI, values);
        setResult(RESULT_OK);
    }

    @Override
    public void onBackPressed() {
      finishEditing();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(action.equals(Intent.ACTION_EDIT)){
            getMenuInflater().inflate(R.menu.menu_edit, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()){
            case android.R.id.home:
                finishEditing();
                break;
            case R.id.action_delete:
                deleteTodo();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteTodo() {
        getContentResolver().delete(TodoProvider.CONTENT_URI, todoListFilter, null);
        Toast.makeText(this, R.string.todo_deleted, Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }

    public void showDialogOnButtonClick(){
        imgBtnDueDate =(ImageButton) findViewById(R.id.imgBtnDatePicker);
        imgBtnDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(id == DIALOG_ID)
            return new DatePickerDialog(this, dpickerListner, year_x, month_x,day_x);
        return null;

    }

    private DatePickerDialog.OnDateSetListener dpickerListner
            = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;

            selectedDate = day_x + "/" + month_x + "/" + year_x;
            etDueDate.setText(selectedDate);
            Toast.makeText(EditItemActivity.this,selectedDate, Toast.LENGTH_LONG).show();

        }
    };

}
