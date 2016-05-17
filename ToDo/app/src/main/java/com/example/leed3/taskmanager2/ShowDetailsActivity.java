package com.example.leed3.taskmanager2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by leed3 on 2/29/2016.
 */
public class ShowDetailsActivity extends AppCompatActivity {

    private ToDo task;
    private static final int REQUEST_CODE = 103;

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
