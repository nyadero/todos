package com.bronyst.roomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.bronyst.roomdb.models.Task;

public class NewTask extends AppCompatActivity {
    private EditText taskName, taskDetails;
    private ImageView saveTask;
    private Task task;
    private Intent intent;
    private boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        // initialize views
        initViews();

       try {
          task = (Task) getIntent().getSerializableExtra("task");
          taskName.setText(task.getTaskName());
          taskDetails.setText(task.getTaskDetails());
          isEditing = true;
       }catch(Exception e) {
           Toast.makeText(NewTask.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
       }

        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get edittext input values
                String taskTitle = taskName.getText().toString();
                String taskContent = taskDetails.getText().toString();
                if (taskTitle.isEmpty() || taskContent.isEmpty()) {
                    Toast.makeText(NewTask.this, "Empty fields", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    if(!isEditing){
                        task = new Task();
                    }
                    task.setTaskName(taskTitle);
                    task.setTaskDetails(taskContent);

                    intent = new Intent();
                    intent.putExtra("task", task);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });

    }


    public void initViews()  {
        taskName = findViewById(R.id.edt_task_title);
        taskDetails = findViewById(R.id.edt_task_details);
        saveTask = findViewById(R.id.img_save_note);
    }
}