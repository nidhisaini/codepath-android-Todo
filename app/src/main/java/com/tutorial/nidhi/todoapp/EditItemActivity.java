package com.tutorial.nidhi.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    private static final String TAG = "EditItemActivity";
    EditText etEditItem;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*get the intent data*/
        final int position = getIntent().getIntExtra("pos", 12);
        String text = getIntent().getStringExtra("text");

        etEditItem = (EditText) findViewById(R.id.etEditItem);
        btnSave = (Button) findViewById(R.id.btnSave);
        etEditItem.setText(text);

        etEditItem.setSelection(etEditItem.getText().length());
        /*Button setOnClickListener to pass data back to the main activity after editing it*/
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentData = new Intent();
                intentData.putExtra("editedText", etEditItem.getText().toString());
                intentData.putExtra("editedPos", position);

                setResult(RESULT_OK, intentData);
                // closes the activity, pass data to parent
                finish();
            }
        });


    }

}
