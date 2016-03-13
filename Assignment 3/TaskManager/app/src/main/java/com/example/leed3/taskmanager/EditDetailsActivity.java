package com.example.leed3.taskmanager;

import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;

/**
 * Created by leed3 on 2/29/2016.
 */
public class EditDetailsActivity extends AppCompatActivity {

    private AllTasks at;
    private ToDo td;
    private int index;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editdetails);
        Intent intent = getIntent();
        td = (ToDo) intent.getExtras().getSerializable("task");
        at = (AllTasks) intent.getExtras().getSerializable("alltasks");
        index = intent.getIntExtra("index",0);
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
                setResult(REQUEST_CODE, i);
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
                at.set(index,td);
                Intent i = new Intent(EditDetailsActivity.this,MainActivity.class);
                i.putExtra("alltasks", at);
                setResult(REQUEST_CODE, i);
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
