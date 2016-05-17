package com.example.leed3.courseregistersystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by leed3 on 5/1/2016.
 */
public class AddCourseActivity extends AppCompatActivity {

    private Button back;
    private Button add;
    private EditText name;
    private EditText id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course_activity);
        back = (Button) findViewById(R.id.add_course_back_button);
        add = (Button) findViewById(R.id.add_course_button);
        name = (EditText) findViewById(R.id.course_name);
        id = (EditText) findViewById(R.id.course_id);
        setButtons();
    }

    private void setButtons() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddCourseActivity.this,CoursesActivity.class);
                setResult(RESULT_CANCELED,i);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = name.getText().toString();
                String courseId = id.getText().toString();
                Course c = new Course(courseId,courseName);
                Intent i = new Intent(AddCourseActivity.this,CoursesActivity.class);
                i.putExtra("course",c);
                setResult(RESULT_OK,i);
                finish();
            }
        });
    }
}