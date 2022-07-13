package com.bronyst.roomdb.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bronyst.roomdb.TasksClickListener;

import java.io.Serializable;

@Entity
public class Task implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id = 0;

    @ColumnInfo(name = "taskName")
    String taskName = "";

    @ColumnInfo(name = "taskDetails")
    String taskDetails = "";

    @ColumnInfo(name = "isActive")
    boolean isActive  = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", taskDetails='" + taskDetails + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
