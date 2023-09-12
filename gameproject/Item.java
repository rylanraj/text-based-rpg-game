package com.rylanraj.gameproject;

// An item will always be abstract since it needs to do something on consumption
public abstract class Item {
    String itemName;
    String itemDescription;
    int itemPrice;
    public Item(String itemName, int itemPrice, String itemDescription){
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
    }

    public abstract void onConsumption(Player player);

    public String getItemName() {
        return itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }
    public String getItemDescription() {
        return itemDescription;
    }

    Item duplicateItem(){
        Item item = this;
        return item;
    }

}
