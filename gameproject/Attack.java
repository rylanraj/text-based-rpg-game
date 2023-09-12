package com.rylanraj.gameproject;

public class Attack extends Move {
    // Attacks are moves that do damage
    int damage;
    public Attack(String moveName, int energyUsed, int damage) {
        super(moveName, energyUsed);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

}
