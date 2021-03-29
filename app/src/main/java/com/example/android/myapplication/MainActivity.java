package com.example.android.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;
import java.util.Random;

/* Pokeapi imports */
import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;

public class MainActivity extends AppCompatActivity {
    PokeApi pokeApi = new PokeApiClient();
    final PokeObject[] po = {null, null};
    private TrainerDataSource dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dataSource = new TrainerDataSource(this);
    }

    //TODO make damage amounts actually reflect the attack modifier logic
    @SuppressLint("SetTextI18n")
    public void onPokemon1AttackButtonClick(View v) {
        if (po[0] != null && po[1] != null) {
            if (po[1].health.getCurrentHP() != 0) {
                int damage = (int) (Math.random() * 50);
                po[1].health.takeDamage(damage);

                TextView pokeHP2 = (TextView) findViewById(R.id.pokemon_2_HP);
                pokeHP2.setText(po[1].health.getCurrentHP() + " HP");

                TextView battleText = (TextView) findViewById(R.id.battleText);
                double matchupEffectivenessResult = po[0].getMatchupEffectiveness(po[1]);
                if (matchupEffectivenessResult == 0.0) {
                    // no effect
                    battleText.setText(po[0].getName() + " attacked " + po[1].getName() + " with no effect...");
                } else if (matchupEffectivenessResult > 1) {
                    //super effective
                    battleText.setText(po[0].getName() + " attacked " + po[1].getName() + " for " + damage + " damage! Super effective!");
                } else if (matchupEffectivenessResult < 1) {
                    // not very effective
                    battleText.setText(po[0].getName() + " attacked " + po[1].getName() + " for " + damage + " damage! Not very effective...");
                } else {
                    battleText.setText(po[0].getName() + " attacked " + po[1].getName() + " for " + damage + " damage!");
                }
            } else {
                TextView battleText = (TextView) findViewById(R.id.battleText);
                battleText.setText("You can't do that! " + po[1].getName() + " is already fainted!");
            }
        }
    }

    //TODO make damage amounts actually reflect the attack modifier logic
    @SuppressLint("SetTextI18n")
    public void onPokemon2AttackButtonClick(View v) {
        if (po[0] != null && po[1] != null) {
            if (po[0].health.getCurrentHP() != 0) {
                int damage = (int) (Math.random() * 50);
                po[0].health.takeDamage(damage);

                TextView pokeHP1 = (TextView) findViewById(R.id.pokemon_1_HP);
                pokeHP1.setText(po[0].health.getCurrentHP() + " HP");

                TextView battleText = (TextView) findViewById(R.id.battleText);
                double matchupEffectivenessResult = po[1].getMatchupEffectiveness(po[0]);
                if (matchupEffectivenessResult == 0.0) {
                    // no effect
                    battleText.setText(po[1].getName() + " attacked " + po[0].getName() + " with no effect...");
                } else if (matchupEffectivenessResult > 1) {
                    //super effective
                    battleText.setText(po[1].getName() + " attacked " + po[0].getName() + " for " + damage + " damage! Super effective!");
                } else if (matchupEffectivenessResult < 1) {
                    // not very effective
                    battleText.setText(po[1].getName() + " attacked " + po[0].getName() + " for " + damage + " damage! Not very effective...");
                } else {
                    battleText.setText(po[1].getName() + " attacked " + po[0].getName() + " for " + damage + " damage!");
                }


            } else {
                TextView battleText = (TextView) findViewById(R.id.battleText);
                battleText.setText("You can't do that! " + po[0].getName() + " is already fainted!");
            }

        }
    }

    @SuppressLint("SetTextI18n")
    public void onPokemon1HealButtonClick(View v) {
        if (po[0] != null && po[1] != null) {
            if (po[0].health.getCurrentHP() != po[0].health.getMaxHP()) {
                int previousHealth = po[0].health.getCurrentHP();
                int healAmount = (int) (Math.random() * 50);
                po[0].health.healDamage((int) healAmount);

                TextView pokeHP1 = (TextView) findViewById(R.id.pokemon_1_HP);
                pokeHP1.setText(po[0].health.getCurrentHP() + " HP");

                TextView battleText = (TextView) findViewById(R.id.battleText);
                battleText.setText(po[0].getName() + " healed for " + (po[0].health.getCurrentHP() - previousHealth) + " hit points!");
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void onPokemon2HealButtonClick(View v) {
        if (po[0] != null && po[1] != null) {
            if (po[1].health.getCurrentHP() != po[1].health.getMaxHP()) {
                int previousHealth = po[1].health.getCurrentHP();
                int healAmount = (int) (Math.random() * 50);
                po[1].health.healDamage((int) healAmount);

                TextView pokeHP2 = (TextView) findViewById(R.id.pokemon_2_HP);
                pokeHP2.setText(po[1].health.getCurrentHP() + " HP");

                TextView battleText = (TextView) findViewById(R.id.battleText);
                battleText.setText(po[1].getName() + " healed for " + (po[1].health.getCurrentHP() - previousHealth) + " hit points!");
            }
        }
    }


    // the @SuppressLint allows string literals in the onButtonClick function
    @SuppressLint("SetTextI18n")
    public void onGenerateButtonClick(View v) {

        GenerateRunnable runnable = new GenerateRunnable();
        new Thread(runnable).start();


        /*
        If no data populated display error message
         */

    } // end onButtonClick

    class GenerateRunnable implements Runnable {
        Random rand = new Random();

        @Override
        public void run() {
            Looper.prepare();
            for (int i = 0; i < 2; i++) {
                po[i] = new PokeObject(rand.nextInt(151) + 1);
            }

            runOnUiThread(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    // grab reference to relevant data fields
                    TextView pokeName1 = (TextView) findViewById(R.id.pokemon_1_name);
                    TextView pokeName2 = (TextView) findViewById(R.id.pokemon_2_name);

                    pokeName1.setText(po[0].getName());
                    pokeName2.setText(po[1].getName());

                    ImageView image1 = (ImageView) findViewById(R.id.pokemon_1_sprite);
                    ImageView image2 = (ImageView) findViewById(R.id.pokemon_2_sprite);

                    // set the image accordingly
                    image1.setImageBitmap(po[0].getBitmap());
                    image2.setImageBitmap(po[1].getBitmap());

                    TextView pokeHP1 = (TextView) findViewById(R.id.pokemon_1_HP);
                    TextView pokeHP2 = (TextView) findViewById(R.id.pokemon_2_HP);

                    pokeHP1.setText(po[0].health.getCurrentHP() + " HP");
                    pokeHP2.setText(po[1].health.getCurrentHP() + " HP");

                    TextView battleText = (TextView) findViewById(R.id.battleText);
                    battleText.setText("");

                    View pokeAttackButton1 = findViewById(R.id.button1);
                    View pokeAttackButton2 = findViewById(R.id.button2);

                    pokeAttackButton1.setBackgroundColor(Color.parseColor(po[0].getTypeColorString(0)));
                    pokeAttackButton2.setBackgroundColor(Color.parseColor(po[1].getTypeColorString(0)));


                } // end run


            }); // end run

        }
    }

    @SuppressLint("SetTextI18n")
    public void onSaveButtonClick(View v) {

        GenerateSavedRunnable runnable = new GenerateSavedRunnable();
        new Thread(runnable).start();


    } // end onButtonClick

    class GenerateSavedRunnable implements Runnable {

        @Override
        public void run() {

            if (dataSource != null)
            {
                Log.d("onSaveButtonClick", "creating saved pokemon");
                Looper.prepare();
                for (int i = 0; i < 2; i++)
                {
                    Log.d("onSaveButtonClick", "createPokemon: pokemon number: " + po[i].getId());
                    dataSource.createPokemon(new Trainer(po[i].getId()));
                }
            }
            else
                Log.d("SaveButton", " db is null ");

            runOnUiThread(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    // grab reference to relevant data fields
                    TextView pokeName1 = (TextView) findViewById(R.id.pokemon_1_name);
                    TextView pokeName2 = (TextView) findViewById(R.id.pokemon_2_name);

                    pokeName1.setText(po[0].getName());
                    pokeName2.setText(po[1].getName());

                    ImageView image1 = (ImageView) findViewById(R.id.pokemon_1_sprite);
                    ImageView image2 = (ImageView) findViewById(R.id.pokemon_2_sprite);

                    // set the image accordingly
                    image1.setImageBitmap(po[0].getBitmap());
                    image2.setImageBitmap(po[1].getBitmap());

                    TextView pokeHP1 = (TextView) findViewById(R.id.pokemon_1_HP);
                    TextView pokeHP2 = (TextView) findViewById(R.id.pokemon_2_HP);

                    pokeHP1.setText(po[0].health.getCurrentHP() + " HP");
                    pokeHP2.setText(po[1].health.getCurrentHP() + " HP");

                    TextView battleText = (TextView) findViewById(R.id.battleText);
                    battleText.setText("");

                    View pokeAttackButton1 = findViewById(R.id.button1);
                    View pokeAttackButton2 = findViewById(R.id.button2);

                    pokeAttackButton1.setBackgroundColor(Color.parseColor(po[0].getTypeColorString(0)));
                    pokeAttackButton2.setBackgroundColor(Color.parseColor(po[1].getTypeColorString(0)));


                } // end run


            }); // end run

        }
    }

    // the @SuppressLint allows string literals in the onButtonClick function
    @SuppressLint("SetTextI18n")
    public void onLoadButtonClick(View v)
    {
        GenerateLoadedRunnable runnable = new GenerateLoadedRunnable();
        new Thread(runnable).start();

        /*
        If no data populated display error message
         */

    } // end onButtonClick

    class GenerateLoadedRunnable implements Runnable {

        @Override
        public void run() {

            List<Trainer> savedPokemon = dataSource.getAllPokemon();

            Log.d("onLoadButtonClick", "creating loaded pokemon");

            Looper.prepare();
            for (int i = 0; i < 2; i++) {
                po[i] = new PokeObject(savedPokemon.get(i).getPokemonId());
                Log.d("onLoadButtonClick", "created Loaded Pokemon: pokemon number: " + po[i].getId());
            }

            runOnUiThread(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    // grab reference to relevant data fields
                    TextView pokeName1 = (TextView) findViewById(R.id.pokemon_1_name);
                    TextView pokeName2 = (TextView) findViewById(R.id.pokemon_2_name);

                    pokeName1.setText(po[0].getName());
                    pokeName2.setText(po[1].getName());

                    ImageView image1 = (ImageView) findViewById(R.id.pokemon_1_sprite);
                    ImageView image2 = (ImageView) findViewById(R.id.pokemon_2_sprite);

                    // set the image accordingly
                    image1.setImageBitmap(po[0].getBitmap());
                    image2.setImageBitmap(po[1].getBitmap());

                    TextView pokeHP1 = (TextView) findViewById(R.id.pokemon_1_HP);
                    TextView pokeHP2 = (TextView) findViewById(R.id.pokemon_2_HP);

                    pokeHP1.setText(po[0].health.getCurrentHP() + " HP");
                    pokeHP2.setText(po[1].health.getCurrentHP() + " HP");

                    TextView battleText = (TextView) findViewById(R.id.battleText);
                    battleText.setText("");

                    View pokeAttackButton1 = findViewById(R.id.button1);
                    View pokeAttackButton2 = findViewById(R.id.button2);

                    pokeAttackButton1.setBackgroundColor(Color.parseColor(po[0].getTypeColorString(0)));
                    pokeAttackButton2.setBackgroundColor(Color.parseColor(po[1].getTypeColorString(0)));


                } // end run


            }); // end run

        }
    }

    @SuppressLint("SetTextI18n")
    public void onClearDataButtonClick(View v) {

        GenerateClearDataRunnable runnable = new GenerateClearDataRunnable();
        new Thread(runnable).start();


    } // end onButtonClick

    class GenerateClearDataRunnable implements Runnable {

        @Override
        public void run() {

            if (dataSource != null)
            {
                Log.d("onClearDataButtonClick", "deleting saved pokemon");
                dataSource.deleteAllFromTable();
            }
            else
                Log.d("ClearDataButtonClick", " db is null ");

            runOnUiThread(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    // grab reference to relevant data fields
                    TextView pokeName1 = (TextView) findViewById(R.id.pokemon_1_name);
                    TextView pokeName2 = (TextView) findViewById(R.id.pokemon_2_name);

                    pokeName1.setText(po[0].getName());
                    pokeName2.setText(po[1].getName());

                    ImageView image1 = (ImageView) findViewById(R.id.pokemon_1_sprite);
                    ImageView image2 = (ImageView) findViewById(R.id.pokemon_2_sprite);

                    // set the image accordingly
                    image1.setImageBitmap(po[0].getBitmap());
                    image2.setImageBitmap(po[1].getBitmap());

                    TextView pokeHP1 = (TextView) findViewById(R.id.pokemon_1_HP);
                    TextView pokeHP2 = (TextView) findViewById(R.id.pokemon_2_HP);

                    pokeHP1.setText(po[0].health.getCurrentHP() + " HP");
                    pokeHP2.setText(po[1].health.getCurrentHP() + " HP");

                    TextView battleText = (TextView) findViewById(R.id.battleText);
                    battleText.setText("");

                    View pokeAttackButton1 = findViewById(R.id.button1);
                    View pokeAttackButton2 = findViewById(R.id.button2);

                    pokeAttackButton1.setBackgroundColor(Color.parseColor(po[0].getTypeColorString(0)));
                    pokeAttackButton2.setBackgroundColor(Color.parseColor(po[1].getTypeColorString(0)));


                } // end run


            }); // end run

        }
    }
}