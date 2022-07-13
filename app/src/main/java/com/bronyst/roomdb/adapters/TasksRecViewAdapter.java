package com.bronyst.roomdb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bronyst.roomdb.Database.RoomDb;
import com.bronyst.roomdb.R;
import com.bronyst.roomdb.TasksClickListener;
import com.bronyst.roomdb.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TasksRecViewAdapter extends RecyclerView.Adapter<TasksRecViewAdapter.ViewHolder> {
    List<Task> tasks = new ArrayList<>();
    private Context context;

    private TasksClickListener tasksClickListener;

    private RoomDb database;

    public TasksRecViewAdapter(List<Task> tasks, Context context, TasksClickListener tasksClickListener) {
        this.tasks = tasks;
        this.context = context;
        this.tasksClickListener = tasksClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.taskName.setText(tasks.get(position).getTaskName());
         holder.taskDetails.setText(tasks.get(position).getTaskDetails());

         if (tasks.get(position).isActive()) {
             holder.isActive.setChecked(true);
         }

         // onclick listener
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasksClickListener.onClickCreate(tasks.get(holder.getAdapterPosition()));
            }
        });

         holder.deleteTask.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 tasksClickListener.onClickDelete(tasks.get(holder.getAdapterPosition()));
             }
         });

         holder.isActive.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 tasksClickListener.onClickUpdate(tasks.get(holder.getAdapterPosition()));
             }
         });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }


    // filter tasks
    public void filterTasks(List<Task> filteredTasks){
         tasks = filteredTasks;
         notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox isActive;
        private TextView taskName, taskDetails;
        private ImageView deleteTask;
        private RelativeLayout parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            taskName = itemView.findViewById(R.id.txt_task_title);
            taskDetails = itemView.findViewById(R.id.txt_task_details);
            isActive = itemView.findViewById(R.id.checkbox_is_active);
            deleteTask = itemView.findViewById(R.id.img_deleteTask);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
