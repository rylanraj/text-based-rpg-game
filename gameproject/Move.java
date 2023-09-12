package com.rylanraj.gameproject;

public class Move {
    String moveName;
    int energyUsed;
    public Move(String moveName, int energyUsed){
        this.moveName = moveName;
        this.energyUsed = energyUsed;
    }
    public String getMoveName() {
        return moveName;
    }

    public void setMoveName(String moveName) {
        this.moveName = moveName;
    }
    public int getEnergyUsed() {
        return energyUsed;
    }
    public void setEnergyUsed(int energyUsed) {
        this.energyUsed = energyUsed;
    }
}
