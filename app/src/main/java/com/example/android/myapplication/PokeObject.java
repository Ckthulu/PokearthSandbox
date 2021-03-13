package com.example.android.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

import me.sargunvohra.lib.pokekotlin.model.Pokemon;
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies;

public class PokeObject extends MainActivity {


    public enum PokeTypes {
        normal, //0
        fighting, //1
        flying, //2
        poison, //3
        ground, //4
        rock, //5
        bug, //6
        ghost, //7
        steel, //8
        fire, //9
        water, //10
        grass, //11
        electric, //12
        psychic, //13
        ice, //14
        dragon, //15
        dark, //16
        fairy //17
    }

    public static final double[][] typeMatchupEffectiveness =
            {
                    {1, 1, 1, 1, 1, 0.5, 1, 0, 0.5, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //normal
                    {2, 1, 0.5, 0.5, 1, 2, 0.5, 0, 2, 1, 1, 1, 1, 0.5, 2, 1, 2, 0.5}, //fighting
                    {1, 2, 1, 1, 1, 0.5, 2, 1, 0.5, 1, 1, 2, 0.5, 1, 1, 1, 1, 1}, // flying
                    {1, 1, 1, 0.5, 0.5, 0.5, 1, 0.5, 0, 1, 1, 2, 1, 1, 1, 1, 1, 2}, // poison
                    {1, 1, 0, 2, 1, 2, 0.5, 1, 2, 2, 1, 0.5, 2, 1, 1, 1, 1, 1}, // ground
                    {1, 0.5, 2, 1, 0.5, 1, 2, 1, 0.5, 2, 1, 1, 1, 1, 2, 1, 1, 1}, // rock
                    {1, 0.5, 0.5, 0.5, 1, 1, 1, 0.5, 0.5, 0.5, 1, 2, 1, 2, 1, 1, 2, 0.5}, // bug
                    {0, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 0.5, 1}, // ghost
                    {1, 1, 1, 1, 1, 2, 1, 1, 0.5, 0.5, 0.5, 1, 0.5, 1, 2, 1, 1, 2}, // steel
                    {1, 1, 1, 1, 1, 0.5, 2, 1, 2, 0.5, 0.5, 2, 1, 1, 2, 0.5, 1, 1}, // fire
                    {1, 1, 1, 1, 2, 2, 1, 1, 1, 2, 0.5, 0.5, 1, 1, 1, 0.5, 1, 1}, // water
                    {1, 1, 0.5, 0.5, 2, 2, 0.5, 1, 0.5, 0.5, 2, 0.5, 1, 1, 1, 0.5, 1, 1}, // grass
                    {1, 1, 2, 1, 0, 1, 1, 1, 1, 1, 2, 0.5, 0.5, 1, 1, 0.5, 1, 1}, // electric
                    {1, 2, 1, 2, 1, 1, 1, 1, 0.5, 1, 1, 1, 1, 0.5, 1, 1, 0, 1}, // psychic
                    {1, 1, 2, 1, 2, 1, 1, 1, 0.5, 0.5, 0.5, 2, 1, 1, 0.5, 2, 1, 1}, // ice
                    {1, 1, 1, 1, 1, 1, 1, 1, 0.5, 1, 1, 1, 1, 1, 1, 2, 1, 0}, // dragon
                    {1, 0.5, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 0.5, 0.5}, // dark
                    {1, 2, 1, 0.5, 1, 1, 1, 1, 0.5, 0.5, 1, 1, 1, 1, 1, 2, 2, 1} // fairy
            };

    final Pokemon[] myPoke = {null};
    final PokemonSpecies[] myPokeSpecies = {null};
    final Bitmap[] bitmap = {null};
    final private Boolean[] isShiny = {null};
    final Health health = new Health();

    public PokeObject(int id) {
        this.myPoke[0] = pokeApi.getPokemon(id);
        this.myPokeSpecies[0] = pokeApi.getPokemonSpecies(id);
        this.isShiny[0] = Math.random() <= 0.25;
        String frontSpriteURL;


        if (this.isShiny[0])
            frontSpriteURL = this.myPoke[0].getSprites().getFrontShiny();
        else
            frontSpriteURL = this.myPoke[0].getSprites().getFrontDefault();
        try {
            this.bitmap[0] = BitmapFactory.decodeStream((InputStream) new URL(frontSpriteURL).getContent()); // networking
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return this.myPokeSpecies[0].getId();
    }

    public Bitmap getBitmap() {
        return this.bitmap[0];
    }

    public boolean getShinyStatus() {
        return this.isShiny[0];
    }

    public String getName() {
        return this.myPokeSpecies[0].getName();
    }

    public int getCaptureRate() {
        return this.myPokeSpecies[0].getCaptureRate();
    }

    public int getTypeIndex() {
        return PokeTypes.valueOf(this.myPoke[0].getTypes().get(0).getType().getName()).ordinal();
    }

    public double getMatchupEffectiveness(PokeObject opponent)
    {
        return typeMatchupEffectiveness[this.getTypeIndex()][opponent.getTypeIndex()];
    }

    public static double getMatchupEffectiveness(int attacker, int defender) {
        return typeMatchupEffectiveness[attacker][defender];
    }

    public String getTypeColorString(int i) {

        String type = this.myPoke[0].getTypes().get(i).getType().getName();
        Log.d("Logging info about type", type);
        String ans = "";
        switch (type) {
            case "normal":
                ans = "#A8A878";
                break;
            case "fighting":
                ans = "#C03028";
                break;
            case "flying":
                ans = "#A890F0";
                break;
            case "poison":
                ans = "#A040A0";
                break;
            case "ground":
                ans = "#E0C068";
                break;
            case "rock":
                ans = "#B8A038";
                break;
            case "bug":
                ans = "#A8B820";
                break;
            case "ghost":
                ans = "#705898";
                break;
            case "steel":
                ans = "#B8B8D0";
                break;
            case "fire":
                ans = "#F08030";
                break;
            case "water":
                ans = "#6890F0";
                break;
            case "grass":
                ans = "#78C850";
                break;
            case "electric":
                ans = "#F8D030";
                break;
            case "psychic":
                ans = "#F85888";
                break;
            case "ice":
                ans = "#98D8D8";
                break;
            case "dragon":
                ans = "#7038F8";
                break;
            case "dark":
                ans = "#705848";
                break;
            case "fairy":
                ans = "#EE99AC";
                break;
            default:
                ans = "#000000";
                break;
        }
        return ans;
    }


}


