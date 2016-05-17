package com.example.leed3.courseregistersystem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leed3 on 5/1/2016.
 */
public class CoursesActivity extends AppCompatActivity {

    private final int ADD_COURSE_CODE = 200;
    private final int SHOW_COURSE_DETAILS = 201;
    private Student s;
    private int index;
    private Button done;
    private Button add;
    private CourseAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses_activity);
        s = (Student) getIntent().getSerializableExtra("student");
        index = getIntent().getIntExtra("index",0);
        adapter = new CourseAdapter(this,R.layout.check_list_view,s.getCourses());
        setSpinner();
        setButtons();
    }

    protected void setButtons() {
        done = (Button) findViewById(R.id.course_done_button);
        add = (Button) findViewById(R.id.add_course_button);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CoursesActivity.this, MainActivity.class);
                i.putExtra("student", s);
                i.putExtra("index", index);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CoursesActivity.this, AddCourseActivity.class);
                startActivityForResult(i, ADD_COURSE_CODE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_COURSE_CODE && resultCode == RESULT_OK) {
            Course c = (Course) data.getSerializableExtra("course");
            adapter.addCourse(c);
            adapter.clearSelectedCoursesList();
        }

        else if (requestCode == SHOW_COURSE_DETAILS && resultCode == RESULT_OK) {
            return;
        }
    }

    private void setSpinner() {
        ListView lv = (ListView) findViewById(R.id.course_list);
        lv.setChoiceMode(lv.CHOICE_MODE_MULTIPLE);
        lv.setAdapter(adapter);
    }

    private Dialog callDialog() {
        final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(CoursesActivity.this);
        deleteDialog.setMessage("Are you sure you want to delete selected courses?").
                setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (Course c : adapter.getSelectedCourseList()) {
                            adapter.removeCourse(c);
                        }
                        adapter.clearSelectedCoursesList();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        return deleteDialog.create();
    }

    private Dialog callDeleteAllDialog() {
        final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(CoursesActivity.this);
        deleteDialog.setMessage("Are you sure you want to delete all?").
                setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.clearAll();
                        adapter.clearSelectedCoursesList();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        return deleteDialog.create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        populateMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    protected void populateMenu(Menu menu) {
        int groupId = 0;
        int order = 0;
        menu.add(groupId, 2,++order,"Delete Courses");
        menu.add(groupId,5,++order,"View Course Details");
        menu.add(groupId,3,++order,"Sort by Course Name");
        menu.add(groupId,4,++order,"Sort by Course ID");
        menu.add(groupId, 1, ++order, "Delete All Courses");
    }

    public boolean onContextItemSelected(MenuItem item)  {
        return (applyMenuOption(item));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return (applyMenuOption(item));
    }

    public boolean applyMenuOption(MenuItem item) {
        int id = item.getItemId();
        if (id == 1) {

            if (adapter.getCoursesList().size() == 0) {
                Toast.makeText(this,"There are no courses!",Toast.LENGTH_SHORT).show();
            }

            else {
                callDeleteAllDialog().show();
            }
        }

        else if (id == 2) {

            if (adapter.getSelectedCourseList().size() == 0) {
                Toast.makeText(this,"Please Select a Course!",Toast.LENGTH_SHORT).show();
            }

            else if (adapter.getCoursesList().size() == 0) {
                Toast.makeText(this,"There are no Courses!",Toast.LENGTH_SHORT).show();
            }

            else {
                callDialog().show();
            }
        }

        else if (id == 3) {
            adapter.setSort(0);
        }

        else if (id == 4) {
            adapter.setSort(1);
        }

        else if (id == 5) {
            if (adapter.getSelectedCourseList().size() == 0) {
                Toast.makeText(this,"Please Select a Course!",Toast.LENGTH_SHORT).show();
            }

            else if (adapter.getCoursesList().size() == 0) {
                Toast.makeText(this,"There are no Courses!",Toast.LENGTH_SHORT).show();
            }

            else if (adapter.getSelectedCourseList().size() > 1) {
                Toast.makeText(this,"Please select only one course!",Toast.LENGTH_SHORT).show();
            }
            else {
                Intent i = new Intent(CoursesActivity.this, ShowCourseDetails.class);
                i.putExtra("name",adapter.getSelectedCourse().getCourseName());
                i.putExtra("id",adapter.getSelectedCourse().getCourseID());
                startActivityForResult(i,SHOW_COURSE_DETAILS);
            }
        }

        return true;
    }
}