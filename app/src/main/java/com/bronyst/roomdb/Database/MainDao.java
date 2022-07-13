package com.bronyst.roomdb.Database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.bronyst.roomdb.models.Task;

import java.util.List;

@Dao
public interface MainDao {
    // insert data method
    @Insert(onConflict = REPLACE)
    void createTask(Task newTask);

    // get all tasks
    @Query("SELECT * FROM Task ORDER BY id DESC")
    List<Task> getAllTasks();

    // update task
    @Query("UPDATE Task SET taskName = :taskName, taskDetails = :taskDetails WHERE id= :id")
    void upDateTask(int id, String taskName, String taskDetails);

    //delete task
    @Delete()
    void deleteTask(Task task);

    // update task
    @Query("UPDATE Task SET isActive = :isActive WHERE id= :id")
    void updateTask(int id, boolean isActive);
}
