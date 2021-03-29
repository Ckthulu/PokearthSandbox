package com.example.android.myapplication;

import android.content.*;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.android.myapplication.Trainer;

@Database(entities = Trainer.class, version = 1)
public abstract class TrainerDatabase extends RoomDatabase
{
    public static final String DATABASE_NAME = "saved_trainer";
    private static TrainerDatabase INSTANCE = null;

    public static TrainerDatabase getInstance(Context context)
    {
        if (INSTANCE == null)
        {
            synchronized (TrainerDatabase.class)
            {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                                TrainerDatabase.class,
                                                DATABASE_NAME)
                                .build();
                Log.d("TrainerDatabase", "DB created");
            }
        }
        return INSTANCE;
    }

    public abstract TrainerDao trainerDao();
}
