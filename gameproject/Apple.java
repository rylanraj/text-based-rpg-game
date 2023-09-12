package com.rylanraj.gameproject;

public class Apple extends Item {
    // Apples are consumable items that heals the player
    public Apple(){
        super("Apple",5, "A nutritious fruit that heals your health by 10HP");

    }
    public void onConsumption(Player player){
        player.setHealth(player.getHealth() + 10);
    }
}
