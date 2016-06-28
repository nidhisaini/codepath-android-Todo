package com.tutorial.nidhi.todoapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


public class TodoCursorAdapter extends CursorAdapter {
    TextView tvName;
    TextView tvPriority;
    TextView tvDueDate;

    public TodoCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvPriority = (TextView) view.findViewById(R.id.tvPriority);
        tvDueDate = (TextView) view.findViewById(R.id.tvDueDate);


        String name = cursor.getString(cursor.getColumnIndex(TodoItemsDbHelper.TODO_NAME));
        String priority = cursor.getString(cursor.getColumnIndex(TodoItemsDbHelper.TODO_PRIORITY));
        String dueDate = cursor.getString(cursor.getColumnIndex(TodoItemsDbHelper.TODO_DUEDATE));

        tvName.setText(name);
        tvPriority.setText(priority);
        tvDueDate.setText(dueDate);


    }
}
