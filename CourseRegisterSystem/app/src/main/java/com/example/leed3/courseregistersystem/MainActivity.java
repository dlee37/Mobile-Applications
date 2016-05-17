package com.example.leed3.courseregistersystem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONException;

import java.io.IOException;
import java.io.InterruptedIOException;

public class MainActivity extends AppCompatActivity {

    private final int EDIT_COURSES_CODE = 100;
    private final int VIEW_STUDENT_CODE = 101;
    private final int MODIFY_STUDENTS_CODE = 102;
    private final int ADD_STUDENT_CODE = 103;
    private StudentCourseModel model;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new StudentCourseModel(this);
        add = (Button) findViewById(R.id.main_add_student);
        LoadModel load = new LoadModel();
        load.start();
        setListView();
        setButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        populateMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.save();
        model.deleteDatabase();
    }

    protected void populateMenu(Menu menu) {
        int groupId = 0;
        int order = 0;
        menu.add(groupId, 2,++order,"Add/Delete Courses");
        menu.add(groupId, 3,++order,"Delete Selected Students");
        menu.add(groupId, 4, ++order, "View Student's Information");
        menu.add(groupId, 5, ++order, "Modify Student's Information");
        menu.add(groupId, 6, ++order, "Sort by Name");
        menu.add(groupId, 7, ++order, "Sort by Graduation Year");
        menu.add(groupId, 8, ++order, "Sort by School");
        menu.add(groupId, 1, ++order, "Close application");
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
            finish();
        }

        else if (id == 2) {
            if (model.isMultipleStudentsSelected()) {
                Toast.makeText(this,"Please Select Only One Student",Toast.LENGTH_SHORT).show();
            }

            else if (model.getStudentAdapter().getSelectedStudents().size() == 0) {
                Toast.makeText(this,"Please Select a student!",Toast.LENGTH_SHORT).show();
            }

            else if (model.getStudentAdapter().getStudentsList().size() == 0) {
                Toast.makeText(this,"There are no students!",Toast.LENGTH_SHORT).show();
            }

            else {
                Intent intent = new Intent(this, CoursesActivity.class);
                intent.putExtra("student", model.getStudent());
                intent.putExtra("index",model.indexOf(model.getStudent()));
                startActivityForResult(intent, EDIT_COURSES_CODE);
            }
        }

        else if (id == 3) {
            //creates a popup window confirming to delete

            if (model.getStudentAdapter().getSelectedStudents().size() == 0) {
                Toast.makeText(this,"Please Select a student!",Toast.LENGTH_SHORT).show();
            }

            else if (model.getStudentAdapter().getStudentsList().size() == 0) {
                Toast.makeText(this,"There are no students!",Toast.LENGTH_SHORT).show();
            }
            else {
                callDialog().show();
            }
        }

        else if (id == 4) {
            if (model.isMultipleStudentsSelected()) {
                Toast.makeText(this,"Please Select Only One Student",Toast.LENGTH_SHORT).show();
            }

            else if (model.getStudentAdapter().getSelectedStudents().size() == 0) {
                Toast.makeText(this,"Please Select a student!",Toast.LENGTH_SHORT).show();
            }

            else if (model.getStudentAdapter().getStudentsList().size() == 0) {
                Toast.makeText(this,"There are no students!",Toast.LENGTH_SHORT).show();
            }

            else {
                Intent intent = new Intent(this, ViewActivity.class);
                intent.putExtra("name", model.getStudent().getStudentName());
                intent.putExtra("gradYear", model.getStudent().getGradYear());
                intent.putExtra("school", model.getStudent().getSchoolName());
                startActivityForResult(intent, VIEW_STUDENT_CODE);
            }
        }

        else if (id == 5) {
            if (model.isMultipleStudentsSelected()) {
                Toast.makeText(this,"Please Select Only One Student",Toast.LENGTH_SHORT).show();
            }

            else if (model.getStudentAdapter().getSelectedStudents().size() == 0) {
                Toast.makeText(this,"There are no students!",Toast.LENGTH_SHORT).show();
            }

            else if (model.getStudentAdapter().getStudentsList().size() == 0) {
                Toast.makeText(this,"There are no students!",Toast.LENGTH_SHORT).show();
            }

            else {
                Intent intent = new Intent(this, ModifyActivity.class);
                intent.putExtra("student", model.getStudent());
                //System.out.println("Index is: " + index);
                intent.putExtra("index",model.indexOf(model.getStudent()));
                startActivityForResult(intent, MODIFY_STUDENTS_CODE);
            }
        }
        else if (id == 6) {
            model.setSort(0);
        }

        else if (id == 7) {
            model.setSort(1);
        }

        else if (id == 8) {
            model.setSort(2);
        }
        return true;
    }

    private void setListView() {
        ListView studentList = (ListView) findViewById(R.id.students_list_view);
        studentList.setChoiceMode(studentList.CHOICE_MODE_MULTIPLE);
        //System.out.println(model.getStudentAdapter().getStudentsList());
        studentList.setAdapter(model.getStudentAdapter());
    }

    private Dialog callDialog() {
        final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(MainActivity.this);
        deleteDialog.setMessage("Are you sure you want to delete?").
                setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (Student s : model.getSelectedStudents()) {
                            model.deleteStudent(s);
                        }
                        model.clearSelectedStudents();
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

    private void setButton() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddStudentActivity.class);
                startActivityForResult(i, ADD_STUDENT_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_COURSES_CODE && resultCode == RESULT_OK) {
            int index = data.getIntExtra("index", 0);
            Student s = (Student) data.getSerializableExtra("student");
            model.setStudent(index, s);
            model.clearSelectedStudents();
        }

        else if (requestCode == VIEW_STUDENT_CODE && resultCode == RESULT_OK) {
            return;
        }

        else if (requestCode == MODIFY_STUDENTS_CODE && resultCode == RESULT_OK) {
            Student s = (Student) data.getSerializableExtra("student");
            int index = data.getIntExtra("index", 0);
            model.setStudent(index, s);
            model.clearSelectedStudents();
        }

        else if (requestCode == ADD_STUDENT_CODE && resultCode == RESULT_OK) {
            Student s = (Student) data.getSerializableExtra("student");
            //System.out.println(s);
            model.addStudent(s);
            model.clearSelectedStudents();
            //System.out.println(model.getStudentAdapter().getStudentsList());
        }
    }

    private class LoadModel extends Thread {
        ProgressDialog progress = new ProgressDialog(MainActivity.this);
        @Override
        public void run() {
            try {
                model.load();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            finally {
                return;
            }
        }
    }
}
