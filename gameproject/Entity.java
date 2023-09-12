package com.rylanraj.gameproject;

public class Entity {
    // All entities have some form of these attributes
    String name;
    Move primary;
    Move secondary;
    private int health;
    private int energy;
    private int defense;
    private int speed;
    private int luck;
    private int level;
    private int exp;
    private int MAX_HEALTH;
    private int MAX_ENERGY;
    private int MAX_DEFENSE;
    private int MAX_SPEED;
    private int MAX_LUCK;

    // Constructor for all attributes (for the player)
    public Entity(String name, Move primary, Move secondary, int health, int energy, int defense, int speed, int luck, int level, int exp,
                  int maxHealth, int maxEnergy, int maxDefense, int maxSpeed, int maxLuck){
        this.name = name;
        this.primary = primary;
        this.secondary = secondary;
        this.health = health;
        this.energy = energy;
        this.defense = defense;
        this.speed = speed;
        this.luck = luck;
        this.level = level;
        this.exp = exp;
        MAX_HEALTH = maxHealth;
        MAX_ENERGY = maxEnergy;
        MAX_DEFENSE = maxDefense;
        MAX_SPEED = maxSpeed;
        MAX_LUCK = maxLuck;
    }
    // Constructor for entities that don't use exp (enemies)
    public Entity(String name, Move primary, Move secondary, int health, int energy, int defense, int speed, int luck, int level,
                  int maxHealth, int maxEnergy, int maxDefense, int maxSpeed, int maxLuck){
        this.name = name;
        this.primary = primary;
        this.secondary = secondary;
        this.health = health;
        this.energy = energy;
        this.defense = defense;
        this.speed = speed;
        this.luck = luck;
        this.level = level;
        MAX_HEALTH = maxHealth;
        MAX_ENERGY = maxEnergy;
        MAX_DEFENSE = maxDefense;
        MAX_SPEED = maxSpeed;
        MAX_LUCK = maxLuck;
    }
    // See the stats of any entity on method call
    public void seeEntityInfo(){
        System.out.println("NAME: " + getName());
        System.out.println("PRIMARY MOVE: " + getPrimaryMoveName() + " (ENERGY COST: " + getPrimary().getEnergyUsed() + ")");
        System.out.println("SECONDARY MOVE: " + getSecondaryMoveName() + " (ENERGY COST: " + getSecondary().getEnergyUsed() + ")");
        System.out.println("HEALTH: " + getHealth() + "/" + getMAX_HEALTH());
        System.out.println("ENERGY: " + getEnergy() + "/" + getMAX_ENERGY());
        System.out.println("DEFENSE: " + getDefense());
        System.out.println("SPEED: " + getSpeed());
        System.out.println("LUCK: " + getLuck());
        System.out.println("LEVEL: " + getLevel());
        System.out.println("MAX DEFENSE: " + getMAX_DEFENSE());
        System.out.println("MAX SPEED: " + getMAX_SPEED());
        System.out.println("MAX LUCK: " + getMAX_LUCK());
    }
    void checkEntityHealth(){
        if (health > MAX_HEALTH){
            setHealth(MAX_HEALTH);
        }
    }
    void checkEntityEnergy(){
        if (energy > MAX_ENERGY){
            setEnergy(MAX_ENERGY);
        }
    }
    void removeEntityEnergyOnAttack(Attack a){
        int energyUsed = a.getEnergyUsed();
        setEnergy(getEnergy() - energyUsed);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Move getPrimary() {
        return primary;
    }

    public void setPrimary(Move primary) {
        this.primary = primary;
    }

    public Move getSecondary() {
        return secondary;
    }

    public void setSecondary(Move secondary) {
        this.secondary = secondary;
    }

    public String getPrimaryMoveName(){
        return getPrimary().getMoveName();
    }
    public String getSecondaryMoveName(){
        return getSecondary().getMoveName();
    }
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getMAX_HEALTH() {
        return MAX_HEALTH;
    }

    public void setMAX_HEALTH(int MAX_HEALTH) {
        this.MAX_HEALTH = MAX_HEALTH;
    }

    public int getMAX_ENERGY() {
        return MAX_ENERGY;
    }

    public void setMAX_ENERGY(int MAX_ENERGY) {
        this.MAX_ENERGY = MAX_ENERGY;
    }

    public int getMAX_DEFENSE() {
        return MAX_DEFENSE;
    }

    public void setMAX_DEFENSE(int MAX_DEFENSE) {
        this.MAX_DEFENSE = MAX_DEFENSE;
    }

    public int getMAX_SPEED() {
        return MAX_SPEED;
    }

    public void setMAX_SPEED(int MAX_SPEED) {
        this.MAX_SPEED = MAX_SPEED;
    }

    public int getMAX_LUCK() {
        return MAX_LUCK;
    }

    public void setMAX_LUCK(int MAX_LUCK) {
        this.MAX_LUCK = MAX_LUCK;
    }
}
