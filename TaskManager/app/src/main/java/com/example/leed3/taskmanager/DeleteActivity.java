package com.example.leed3.taskmanager;

import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import java.util.*;

/**
 * Created by leed3 on 3/4/2016.
 */
public class DeleteActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private AllTasks at;
    private List<ToDo> selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_page);
        Intent intent = getIntent();
        at = (AllTasks) intent.getExtras().getSerializable("alltasks");
        selected = at.getSelectedList();
        setButtons();
    }

    public void setButtons() {
        Button yes = (Button) findViewById(R.id.yesButton);
        Button no = (Button) findViewById(R.id.noButton);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ToDo x : selected) {
                    at.deleteTask(x);
                }
                Intent i = new Intent(DeleteActivity.this,MainActivity.class);
                setResult(REQUEST_CODE, i);
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeleteActivity.this,MainActivity.class);
                setResult(REQUEST_CODE, i);
                finish();
            }
        });
    }
}
