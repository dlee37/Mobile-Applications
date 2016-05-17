package com.example.leed3.taskmanager;

import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;

/**
 * Created by leed3 on 2/29/2016.
 */
public class AddActivity extends AppCompatActivity {

    private AllTasks at;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        Intent intent = getIntent();
        at = (AllTasks) intent.getExtras().getSerializable("alltasks");
        setButtons();
    }

    private void setButtons() {
        Button back = (Button) findViewById(R.id.backCreateTask);
        Button create = (Button) findViewById(R.id.createTaskButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                intent.putExtra("alltasks", at);
                setResult(REQUEST_CODE, intent);
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
                ToDo task = new ToDo(title, description, at);
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                setResult(REQUEST_CODE, intent);
                finish();
            }
        });
    }
}