package com.bronyst.roomdb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.bronyst.roomdb.Database.MainDao;
import com.bronyst.roomdb.Database.RoomDb;
import com.bronyst.roomdb.adapters.TasksRecViewAdapter;
import com.bronyst.roomdb.models.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   private SearchView searchView;
   private RecyclerView tasksRecView;
   private TasksRecViewAdapter tasksRecViewAdapter;
   private RoomDb database;
   private FloatingActionButton createTask;
   private Intent intent;

    List<Task> tasks  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get initial tasks
        database = RoomDb.getInstance(this);
        tasks = database.mainDao().getAllTasks();
        Toast.makeText(MainActivity.this, "tasks " + tasks, Toast.LENGTH_SHORT).show();

        // initialize views
        initViews();

        // update recycler view
        updateTasksRecView(tasks);

        // navigate to create task activity
        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCreateTaskActivity();
            }
        });

        // search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               filter(newText);
                return true;
            }
        });

    }

    // update recycler view
    public void updateTasksRecView(List<Task> tasks) {
        tasksRecViewAdapter = new TasksRecViewAdapter(tasks, MainActivity.this, clickListener);
        tasksRecViewAdapter.setTasks(tasks);
        tasksRecView.setAdapter(tasksRecViewAdapter);
        tasksRecView.setLayoutManager(new LinearLayoutManager(this));
    }

    // navigate to create task activity
    public  void navigateToCreateTaskActivity() {
        intent = new Intent(MainActivity.this, NewTask.class);
        startActivityForResult(intent, 101);
    }

    // activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // create task
        if (requestCode == 101) {
           if (resultCode == Activity.RESULT_OK){
               Task newTask = (Task) data.getSerializableExtra("task");
               database.mainDao().createTask(newTask);
               tasks.clear();
               tasks.addAll(database.mainDao().getAllTasks());
               tasksRecViewAdapter.notifyDataSetChanged();
//               Toast.makeText(MainActivity.this, "task " + newTask, Toast.LENGTH_SHORT).show();
           }
        }

        // edit task
        if (requestCode == 102){
            if (resultCode == Activity.RESULT_OK){
              Task taskToEdit = (Task) data.getSerializableExtra("task");
               database.mainDao().upDateTask(taskToEdit.getId(), taskToEdit.getTaskName(), taskToEdit.getTaskDetails());
               tasks.clear();
               tasks.addAll(database.mainDao().getAllTasks());
               tasksRecViewAdapter.notifyDataSetChanged();
            }
        }
    }

    // implement task click listener
    TasksClickListener clickListener = new TasksClickListener() {
        @Override
        public void onClickCreate(Task task) {
            intent = new Intent(MainActivity.this, NewTask.class);
            intent.putExtra("task", task);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onClickDelete(Task task) {
            database.mainDao().deleteTask(task);
            tasks.clear();
            tasks.addAll(database.mainDao().getAllTasks());
            tasksRecViewAdapter.notifyDataSetChanged();
        }

        @Override
        public boolean onClickUpdate(Task task) {
            if(task.isActive()){
                database.mainDao().updateTask(task.getId(), false);
                Toast.makeText(MainActivity.this, "task not finished", Toast.LENGTH_SHORT).show();
            }else{
                database.mainDao().updateTask(task.getId(), true);
                Toast.makeText(MainActivity.this, "task finished", Toast.LENGTH_SHORT).show();
            }
            tasks.clear();
            tasks.addAll(database.mainDao().getAllTasks());
            tasksRecViewAdapter.notifyDataSetChanged();
            return true;
        }
    };


    //        filter tasks
    private  void filter(String newText){
        List<Task> filteredTasks = new ArrayList<>();
        for (Task filteredTask: tasks) {
            if (filteredTask.getTaskName().toLowerCase().contains(newText.toLowerCase()) ||
                    filteredTask.getTaskDetails().toLowerCase().contains(newText.toLowerCase())){
                filteredTasks.add(filteredTask);
                tasksRecViewAdapter.filterTasks(filteredTasks);
            }
        }
    }

    //    initialize views
    public void initViews() {
        searchView = findViewById(R.id.search_bar);
        tasksRecView = findViewById(R.id.tasks_rec_view);
        createTask = findViewById(R.id.fab_new_task);
        searchView = findViewById(R.id.search_bar);
    }
}