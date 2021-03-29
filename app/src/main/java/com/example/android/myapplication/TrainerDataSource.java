package com.example.android.myapplication;

import android.content.Context;
import android.util.Log;

import java.util.List;


public class TrainerDataSource
{
    private static final String TAG = TrainerDataSource.class.getSimpleName();
    private final TrainerDao trainerDao;

    public TrainerDataSource ( Context context)
    {
        TrainerDatabase database = TrainerDatabase.getInstance(context);
        trainerDao = database.trainerDao();
    }

    public void createPokemon(Trainer pokemon)
    {
        long rowID = trainerDao.createPokemon(pokemon);
        //int pokeId = pokemon.getPokemonId();

        Log.d(TAG, "createPokemon: rowID "+ rowID);
    }

    public List<Trainer> getAllPokemon()
    {
        List<Trainer> savedTrainer = trainerDao.getAllPokemon();
        return savedTrainer;
    }
}
