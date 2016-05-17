        package com.example.leed3.taskmanager;

        import android.content.*;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.*;
        import java.util.*;
        import java.io.*;

        public class MainActivity extends AppCompatActivity {

            private AllTasks at;
            private List<ToDo> todo;
            private int index;
            private int amountChecked;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                load();
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
                        intent.putExtra("alltasks", at);
                        startActivity(intent);
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
                        intent.putExtra("alltasks", at);
                        startActivity(intent);
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
                        ToDo item = at.getToDoItem(index);
                        intent.putExtra("task", item);
                        intent.putExtra("alltasks", at);
                        intent.putExtra("index", index);
                        startActivity(intent);
                    }
                });

                taskDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cannotDoOperation()) return;
                        Intent intent = new Intent(MainActivity.this, ShowDetailsActivity.class);
                        intent.putExtra("task", at.getToDoItem(index));
                        startActivity(intent);
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
                            if (amountChecked == 1) {
                                index = position;
                            }
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

            public void save() {
                try {
                    Context context = getApplicationContext();
                    FileOutputStream os = context.openFileOutput("listoftasks", Context.MODE_PRIVATE);
                    ObjectOutputStream output = new ObjectOutputStream(os);
                    output.writeObject(at);
                    output.close();
                }

                catch (Exception e) {
                    Log.e("From MainActivity","Did not save!");
                }
            }

            public void load() {
                try {
                    Context context = getApplicationContext();
                    FileInputStream is = context.openFileInput("listoftasks");
                    ObjectInputStream input = new ObjectInputStream(is);
                    at = (AllTasks) input.readObject();
                    todo = at.getList();
                    input.close();
                    AllTasks.setToDoList(todo);
                }

                catch (Exception e) {
                    at = new AllTasks();
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                setButtons();
                setListView();
                amountChecked = 0;
                index = -1;
                at.clearSelectedTasks();
            }

            @Override
            public void onDestroy() {
                super.onDestroy();
                save();
            }
        }