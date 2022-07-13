package com.bronyst.roomdb;

import com.bronyst.roomdb.models.Task;

public interface TasksClickListener {
    void onClickCreate(Task task);
    void onClickDelete(Task task);
    boolean onClickUpdate(Task task);
}
