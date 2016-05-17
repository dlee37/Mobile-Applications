package com.example.leed3.courseregistersystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by leed3 on 5/10/2016.
 */
public class ShowCourseDetails extends AppCompatActivity {

    private String name;
    private String id;
    private TextView coursename;
    private TextView courseid;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_course_details);
        name = getIntent().getStringExtra("name");
        System.out.println("Name is: " + name);
        id = getIntent().getStringExtra("id");
        coursename = (TextView) findViewById(R.id.view_course_name);
        courseid = (TextView) findViewById(R.id.view_course_id);
        back = (Button) findViewById(R.id.view_course_back_button);
        setTextViews();
        setBackButton();
    }

    private void setTextViews() {
        coursename.setText(name);
        courseid.setText(id);
    }

    private void setBackButton() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShowCourseDetails.this,CoursesActivity.class);
                setResult(RESULT_OK,i);
                finish();
            }
        });
    }
}
