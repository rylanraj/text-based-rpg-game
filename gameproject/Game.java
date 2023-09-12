package com.rylanraj.gameproject;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    Scanner scan = new Scanner(System.in);
    Player player;
    Enemy currentEnemy;
    int shopBalance = 10;

    boolean existingGame = false;
    ArrayList<Item> inventory = new ArrayList<>();

    // On instantiation, the game just goes to the main menu
    public Game(){
        mainMenu();
    }

    void mainMenu(){
        // The main menu has a third option, but it only appears if a game exists already
        System.out.println("Welcome to the main menu of Text RPG, would you like to:");
        System.out.println("1. Start a new game");
        System.out.println("2. Exit game");
        if (existingGame){
            System.out.println("3. Resume Game");
        }
        // NOTE: All try catch blocks will be used for catching accidental string/int inputs and for basic recursion
        // I will also use default in switch cases to do this as well
        try{
            int input = scan.nextInt();
            scan.nextLine();
            switch (input) {
                case 1 -> startGame();
                case 2 -> System.out.println("Thank you for playing");
                case 3 ->{
                    // Check again if there's a game
                    if (existingGame){
                        resumeGame();
                    }
                    // If not then start a new game to avoid glitching
                    else{
                        startGame();
                    }
                }
                default -> {
                    System.out.println("INVALID SELECTION");
                    mainMenu();
                }
            }
        }
        catch(Exception e){
            System.out.println("YOUR INPUT CAUSE AN EXCEPTION");
            scan.nextLine();
            mainMenu();
        }
    }
    void startGame(){
        // Reset existing values
        existingGame = false;
        // The player is created
        System.out.println("Create your own player");
        createPlayer();
        // The game can officially start, so take the player to the gamMenu
        System.out.println("Game started");
        gameMenu();

    }
    void resumeGame(){
        System.out.println("Resuming game...");
        gameMenu();
    }
    void createPlayer(){
        System.out.println("Enter the name of your player");
        String name = scan.nextLine();
        player = new Player(name, new Punch(), new Kick(), 1, 20, 10, 1, 3, 3 ,1, 0, 20, 10, 1, 3, 3);
        System.out.println("Successfully created " + getPlayer().getName());
        System.out.println("Game is now starting...");
    }
    // Create goblin relative to the player's skill
    public Enemy createGoblin(){
        return new Enemy("Goblin", new Scratch(), new Headbutt(), getPlayer().getMAX_HEALTH() / 2, getPlayer().getMAX_ENERGY(), Math.round(getPlayer().getMAX_DEFENSE()/ 2 + getPlayer().getLevel()), getPlayer().getMAX_SPEED() - 1, 1, getPlayer().getLevel(),
                getPlayer().getMAX_HEALTH() / 2, getPlayer().getMAX_ENERGY(), Math.round(getPlayer().getMAX_DEFENSE()/ 2 + getPlayer().getLevel()), getPlayer().getMAX_SPEED() - 1, getPlayer().getLuck());
    }
    public Enemy createZombie(){
        return new Enemy("Zombie", new Chomp(), new Headbutt(), getPlayer().getHealth(), getPlayer().getMAX_ENERGY(), getPlayer().getMAX_DEFENSE(), getPlayer().getMAX_SPEED() + 1, 0, getPlayer().getLevel(),
                getPlayer().getMAX_HEALTH(), getPlayer().getMAX_ENERGY(), getPlayer().getMAX_DEFENSE(), getPlayer().getMAX_SPEED() + 1, getPlayer().getLuck());
    }
    void gameMenu(){
        System.out.println("Welcome to the game, please select one of  the options below");
        System.out.println("1. Enter Shop");
        System.out.println("2. Start new battle");
        System.out.println("3. See your attributes");
        System.out.println("4. See your inventory");
        System.out.println("5. Exit to main menu");

        try{
            int selection = scan.nextInt();
            switch (selection) {
                case 1 -> shop();
                case 2 -> newBattle();
                case 3 -> seeAttributes();
                case 4 -> seeInventory();
                case 5 -> {
                    existingGame = true;
                    mainMenu();
                }
                default -> {
                    System.out.println("INVALID SELECTION");
                    gameMenu();
                }
            }
        }
        catch (Exception e){
            System.out.println("YOUR INPUT CAUSE AN EXCEPTION");
            scan.nextLine();
            gameMenu();
        }
    }
    // The player can buy items in the shop
    void shop(){
        System.out.println("What would you like to buy? (Balance: $" + getShopBalance() + ")");
        System.out.println("1. Apples ($" + new Apple().getItemPrice() + ")");
        System.out.println("2. Juice ($" + new Juice().getItemPrice() + ")");
        System.out.println("3. View item description");
        System.out.println("4. Exit shop");
        try{
            int selection = scan.nextInt();
            switch (selection){
                case 1:
                    buyItems(new Apple());
                    break;
                case 2:
                    buyItems(new Juice());
                    break;
                case 3:
                    seeItemDescription();
                    shop();
                    break;
                case 4:
                    gameMenu();
                    break;
                default:
                    System.out.println("INVALID SELECTION");
                    shop();
            }
        }
        catch (Exception e){
            System.out.println("YOUR INPUT CAUSE AN EXCEPTION");
            scan.nextLine();
            shop();
        }
    }
    // Start new battle, the enemy can change based on the player's level
    void newBattle(){
        System.out.println("New battle started");
        Enemy enemyToBattle = createGoblin();
        if (getPlayer().getLevel() >= 3){
            enemyToBattle = createZombie();
        }
        currentEnemy = enemyToBattle;
        getPlayer().battle(enemyToBattle, scan, this);
    }
    // See what the player's attributes are
    void seeAttributes(){
        getPlayer().seeEntityInfo();
        gameMenu();
    }
    // See what the player has in their inventory, such as items and cash.
    void seeInventory(){
        System.out.println("YOUR INVENTORY:");
        System.out.println("CASH: " + getShopBalance());
        System.out.println("APPLES: " + getInventoryItemTotal(new Apple()));
        System.out.println("JUICES: " + getInventoryItemTotal(new Juice()));
        System.out.println("Would you like to:");
        System.out.println("1. Use item in inventory");
        System.out.println("2. See item description");
        System.out.println("3. Exit Inventory");

        try{
            int selection = scan.nextInt();
            switch (selection){
                case 1:
                    useItemMenu();
                    break;
                case 2:
                    seeItemDescription();
                    seeInventory();
                    break;
                case 3:
                    gameMenu();
                    break;
                default:
                    System.out.println("INVALID SELECTION");
                    seeInventory();
            }
        }
        catch (Exception e){
            System.out.println("YOUR INPUT CAUSE AN EXCEPTION");
            scan.nextLine();
            seeInventory();
        }
    }
    // See the description of the items in the game
    void seeItemDescription(){
        System.out.println("APPLE: " + new Apple().getItemDescription());
        System.out.println("JUICE: " + new Juice().getItemDescription());
    }
    // Use items that are usable such as apples and juices
    void useItemMenu(){
        System.out.println("USABLE ITEMS:");
        showUsableItems();
        System.out.println("WHAT WOULD YOU LIKE TO USE?");
        System.out.println("1. APPLE");
        System.out.println("2. JUICE");
        System.out.println("3. EXIT USE ITEM MENU");
        try{
            int selection = scan.nextInt();
            switch (selection) {
                case 1 -> {
                    // Use the apple
                    useUsableItem(new Apple());
                    // If the player is not in battle, take them back to the item menu, if they are in battle, end their turn, so they can't use infinite items
                    if(!getPlayer().isInBattle()){
                        useItemMenu();
                    }
                    if (getPlayer().isInBattle()){
                        getPlayer().enemyHealthChecker(getCurrentEnemy(), this, scan);
                    }
                }
                case 2 -> {
                    // Use the juice
                    useUsableItem(new Juice());
                    // If the player is not in battle, take them back to the item menu, if they are in battle, end their turn, so they can't use infinite items
                    if(!getPlayer().isInBattle()){
                        useItemMenu();
                    }
                    if (getPlayer().isInBattle()){
                        getPlayer().enemyHealthChecker(getCurrentEnemy(), this, scan);
                    }
                }
                case 3 -> {
                    // If the player is not in battle, take them back to their inventory, if they are in battle, take them back to the battle menu
                    if (!getPlayer().isInBattle()) {
                        seeInventory();
                    }
                    if(getPlayer().isInBattle()){
                        getPlayer().battleMenu(scan, this, getCurrentEnemy());
                    }
                }
                default -> {
                    System.out.println("INVALID SELECTION");
                    useItemMenu();
                }

            }
        }
        catch (Exception e){
            System.out.println("YOUR INPUT CAUSE AN EXCEPTION");
            scan.nextLine();
            useItemMenu();
        }
    }
    // Show the player what items they can use
    void showUsableItems(){
        System.out.println("APPLES: " + getInventoryItemTotal(new Apple()));
        System.out.println("JUICES: " + getInventoryItemTotal(new Juice()));
    }
    // Use a usable item (such as an apple or juice)
    void useUsableItem(Item item){
        for (Item i : getInventory()){
            // If the item is in the inventory and there is at least one
            if(i.getClass() == item.getClass() && getInventoryItemTotal(item) > 0){
                // remove the item
                getInventory().remove(i);
                // Turn it into the item of use
                i = item;
                // Do whatever it's supposed to do on consumption
                i.onConsumption(player);
                // Check if the player's stats are still in bounds
                getPlayer().checkEntityHealth();
                getPlayer().checkEntityEnergy();
                // Trim inventory to its new size
                getInventory().trimToSize();
                // Let the player know that they used an item
                System.out.println("YOU USED A " + item.getItemName());
                break;
            }
        }
        // If they do not have any of the item, tell them
        if (getInventoryItemTotal(item) == 0) {
            System.out.println("YOU DO NOT HAVE ANY " + item.getItemName());
        }

    }
    // Buy an item in teh shop
    void buyItems(Item item){
        try{
            System.out.println("How many " + item.getItemName() +  "s would you like to buy?");
            int amountOfItem = scan.nextInt();
            int cost = item.getItemPrice() * amountOfItem;
            System.out.println("The cost for " + amountOfItem + " " + item.getItemName() + "s is $" + cost);
            System.out.println("1. Confirm Purchase");
            System.out.println("2. Cancel Purchase");
            int selection = scan.nextInt();
            switch (selection) {
                case 1 -> {
                    // If the user has enough money
                    if (getShopBalance() >= cost) {
                        // Put their bought items in their inventory
                        for (int i = 0; i < amountOfItem; i++) {
                            Item boughtItem = item.duplicateItem();
                            getInventory().add(boughtItem);
                        }
                        // Deduct the cost of the items from their shop balance
                        setShopBalance(getShopBalance() - cost);
                        System.out.println("You now have " + getInventoryItemTotal(item) + " " + item.getItemName() + "(s)");
                    }
                    // Else they must have insufficient funds
                    else {
                        System.out.println("Insufficient Funds, you have $" + getShopBalance() + " , the cost of the item(s) is $" + cost);
                    }
                    shop();
                }
                case 2 -> shop();
                default -> {
                    System.out.println("INVALID SELECTION");
                    buyItems(item);
                }
            }
        }
        catch (Exception e){
            System.out.println("YOUR INPUT CAUSE AN EXCEPTION");
            scan.nextLine();
            buyItems(item);
        }
    }
    // GETTERS AND SETTERS
    // Get the total amount of items for any type of item
    private int getInventoryItemTotal(Item item){
        int itemsInInventory = 0;
        for (Item i : getInventory()){
            if (i.getClass() == item.getClass()){
                itemsInInventory++;
            }
        }
        return itemsInInventory;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public Enemy getCurrentEnemy() {
        return currentEnemy;
    }
    public Player getPlayer(){
        return player;
    }

    public int getShopBalance() {
        return shopBalance;
    }

    public void setShopBalance(int shopBalance) {
        this.shopBalance = shopBalance;
    }
}
