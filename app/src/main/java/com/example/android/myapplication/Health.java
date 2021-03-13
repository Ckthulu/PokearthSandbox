package com.example.android.myapplication;


public class Health {

    private int currentHP;
    private int maxHP;

    public Health() {
        int DEFAULT_HP = 100;
        this.currentHP = DEFAULT_HP;
        this.maxHP = DEFAULT_HP;
    }

    public Health(int health) {
        this.currentHP = health;
        this.maxHP = health;
    }

    public void takeDamage(int damageAmount) {
        this.currentHP -= damageAmount;
        if (this.currentHP < 0)
            this.currentHP = 0;
    }

    public void healDamage(int healAmount) {
        this.currentHP += healAmount;
        if (this.currentHP > 100)
            this.currentHP = 100;
    }

    public void setMaxHP(int newMaxHP) {
        this.maxHP = newMaxHP;
    }

    public int getCurrentHP() {
        return this.currentHP;
    }

    public int getMaxHP() {
        return this.maxHP;
    }

    public boolean isFainted() {
        return this.currentHP == 0;
    }


}
