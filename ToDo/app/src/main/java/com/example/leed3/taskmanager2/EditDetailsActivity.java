package com.example.leed3.taskmanager2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

/**
 * Created by leed3 on 2/29/2016.
 */
public class EditDetailsActivity extends AppCompatActivity {

    private ToDo td;
    private long id;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editdetails);
        Intent intent = getIntent();
        td = (ToDo) intent.getSerializableExtra("task");
        id = intent.getLongExtra("id",0);
        index = td.getIndex();
        setButtons();
        setEditTexts();
    }

    public void setButtons() {
        Button back = (Button) findViewById(R.id.backCreateTask);
        Button update = (Button) findViewById(R.id.updateTaskButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditDetailsActivity.this,MainActivity.class);
                setResult(RESULT_CANCELED, i);
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            EditText title = (EditText) findViewById(R.id.editTitle);
            EditText description = (EditText) findViewById(R.id.editDescriptionText);
            @Override
            public void onClick(View v) {
                String t = title.getText().toString();
                String d = description.getText().toString();
                td.setTitle(t);
                td.setDescription(d);
                Intent i = new Intent(EditDetailsActivity.this,MainActivity.class);
                i.putExtra("title", t);
                i.putExtra("description", d);
                i.putExtra("date",new Date().toString());
                i.putExtra("id",id);
                i.putExtra("index",index);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }

    private void setEditTexts() {
        EditText title = (EditText) findViewById(R.id.editTitle);
        EditText description = (EditText) findViewById(R.id.editDescriptionText);
        title.setText(td.toString());
        description.setText(td.getDescription());
    }
}