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
public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("why is this happening");
        setContentView(R.layout.add);
        setButtons();
    }

    private void setButtons() {
        Button back = (Button) findViewById(R.id.backCreateTask);
        Button create = (Button) findViewById(R.id.createTaskButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            EditText t = (EditText) findViewById(R.id.addTitle);
            EditText d = (EditText) findViewById(R.id.addDescription);

            @Override
            public void onClick(View v) {
                String title = t.getText().toString();
                String description = d.getText().toString();
                String date = new Date().toString();
                ToDo task = new ToDo(title, description,date);
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                intent.putExtra("newtask", task);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}