package com.rylanraj.gameproject;

public class Enemy extends Entity {
    // An enemy is just an entity whose purpose is to defeat the player, thus their attributes are tweaked to do just that
    Attack primary;
    Attack secondary;
    // Primary enemy constructor
    public Enemy(String name, Attack primary, Attack secondary, int health, int energy, int defense, int speed, int luck, int level,
                 int maxHealth, int maxEnergy, int maxDefense, int maxSpeed, int maxLuck){
        super(name, primary, secondary, health, energy, defense, speed, luck, level, maxHealth, maxEnergy, maxDefense, maxSpeed, maxLuck);
        this.primary = primary;
        this.secondary = secondary;

    }
    // Override on primary and secondary moves since they're instances of attack
    @Override
    public Attack getPrimary() {
        return primary;
    }
    @Override
    public Attack getSecondary() {
        return secondary;
    }
}
