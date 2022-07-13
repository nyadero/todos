package com.bronyst.roomdb.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.bronyst.roomdb.models.Task;

@Database(entities = Task.class, version = 1, exportSchema = false)
public abstract class RoomDb extends RoomDatabase {
    private static RoomDb  roomDb;
   private static String databaseName = "Task";

   public  synchronized static RoomDb getInstance(Context context) {
       if(roomDb == null) {
            roomDb = Room.databaseBuilder(context.getApplicationContext(), RoomDb.class, databaseName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
       }

       return roomDb;
   }

public abstract MainDao mainDao();

}
