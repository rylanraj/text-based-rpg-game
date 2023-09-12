package com.rylanraj.gameproject;

public class Juice extends Item{
    // Juices are consumable items that increase energy
    public Juice(){
        super("Juice", 5, "An energizing drink that renews your energy by 8 points");
    }
    public void onConsumption(Player player){
        player.setEnergy(player.getEnergy() + 8);
    }
}
