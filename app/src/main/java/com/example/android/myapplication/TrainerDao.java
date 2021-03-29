package com.example.android.myapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
import com.example.android.myapplication.Trainer;

@Dao
public interface TrainerDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long createPokemon(Trainer trainer);

    @Query("SELECT * FROM trainer")
    List<Trainer> getAllPokemon();

    @Query("DELETE FROM trainer")
    void deleteAllFromTable();
}
