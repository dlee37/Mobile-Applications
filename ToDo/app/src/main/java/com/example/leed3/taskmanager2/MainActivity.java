package com.example.leed3.taskmanager2;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private AllTasks at;
    private List<ToDo> todo;
    private int amountChecked;
    private int lastCheckedIndex;
    private static final int ADD_RESULT = 100;
    private static final int DELETE_RESULT = 101;
    private static final int EDIT_RESULT = 102;
    private static final int SHOW_DETAILS_RESULT = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_tasks);
        at = new AllTasks(this);
        todo = at.getList();
        setButtons();
        setListView();
    }
    private void setButtons() {
        Button delete = (Button) findViewById(R.id.deleteButton);
        Button add = (Button) findViewById(R.id.addButton);
        Button list = (Button) findViewById(R.id.listButton);
        Button editDetails = (Button) findViewById(R.id.editDetailsButton);
        Button taskDetails = (Button) findViewById(R.id.showDetailsButton);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, ADD_RESULT);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
                startActivityForResult(intent, DELETE_RESULT);
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cannotDoOperation()) return;
                Intent intent = new Intent(MainActivity.this, EditDetailsActivity.class);
                ToDo item = at.getSelectedList().get(0);
                intent.putExtra("task", item);
                intent.putExtra("id",item.getId());
                startActivityForResult(intent, EDIT_RESULT);
            }
        });

        taskDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cannotDoOperation()) return;
                Intent intent = new Intent(MainActivity.this, ShowDetailsActivity.class);
                intent.putExtra("task", at.getSelectedList().get(0));
                startActivityForResult(intent,SHOW_DETAILS_RESULT);
            }
        });
    }
     public void setListView() {
         ListView taskList = (ListView) findViewById(R.id.taskList);
         taskList.setChoiceMode(taskList.CHOICE_MODE_MULTIPLE);
         todo = at.getList();
         ArrayAdapter<ToDo> adapter = new ArrayAdapter<ToDo>(this,
                 android.R.layout.simple_list_item_checked,
                 todo);
         taskList.setAdapter(adapter);
         taskList.setClickable(true);

         taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 CheckedTextView item = (CheckedTextView) view;
                 if (item.isChecked()) {
                     amountChecked++;
                     at.addSelectedTask(at.getToDoItem(position));
                 } else {
                     amountChecked--;
                     at.deleteSelectedTask(at.getToDoItem(position));
                 }
             }
         });
     }

    private boolean cannotDoOperation() {

        if (amountChecked != 1) return true;
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        setButtons();
        setListView();
        amountChecked = 0;
        at.clearSelectedTasks();
        //Toast.makeText(this,"start",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        at.close();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode ==  ADD_RESULT) {
            if (resultCode == RESULT_OK) {
                ToDo task = (ToDo) data.getSerializableExtra("newtask");
                at.addTask(task);
                System.out.println("add correct");
                Toast.makeText(this,"Added Successfully!", LENGTH_SHORT).show();
            }

            else {
                System.out.println("add failed");
            }
        }

        if (requestCode == DELETE_RESULT) {
            if (resultCode == RESULT_OK) {
                for (ToDo td : at.getSelectedList()) {
                    at.deleteTask(td);
                    Toast.makeText(this,"Delete Successful!", LENGTH_SHORT).show();
                }
            }
            at.clearSelectedTasks();
        }

        if (requestCode == EDIT_RESULT) {
            if (resultCode == RESULT_OK) {
                long idnum = data.getLongExtra("id", 0);
                int index = data.getIntExtra("index",0);
                String t = data.getStringExtra("title");
                String d = data.getStringExtra("description");
                String date = data.getStringExtra("date");
                ToDo task = new ToDo(t,d,date);
                task.setId(idnum);
                task.setIndex(index);
                at.set(index, task);
            }
        }

        if (requestCode == SHOW_DETAILS_RESULT) {
            if (resultCode == RESULT_OK) {
                at.clearSelectedTasks();
            }
        }
    }
}