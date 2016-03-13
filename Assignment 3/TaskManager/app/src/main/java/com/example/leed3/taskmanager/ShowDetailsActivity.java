package com.example.leed3.taskmanager;

import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;

/**
 * Created by leed3 on 2/29/2016.
 */
public class ShowDetailsActivity extends AppCompatActivity {

    private ToDo task;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        task = (ToDo) intent.getExtras().getSerializable("task");
        setContentView(R.layout.showdetails);
        setTextViews();
        setButtons();
    }

    public void setButtons() {
        Button back = (Button) findViewById(R.id.backDetailsTask);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowDetailsActivity.this,MainActivity.class);
                setResult(REQUEST_CODE, intent);
                finish();
            }
        });
    }

    public void setTextViews() {
        TextView title = (TextView) findViewById(R.id.editTitle);
        TextView description = (TextView) findViewById(R.id.descriptionText);
        TextView date = (TextView) findViewById(R.id.dateCreatedDetails);
        title.setText(task.toString());
        description.setText(task.getDescription());
        date.setText(task.getDate());
    }
}
