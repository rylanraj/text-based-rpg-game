package com.rylanraj.gameproject;

import java.util.Random;
import java.util.Scanner;

public class Player extends Entity {
    // Moves
    Attack primary;
    Attack secondary;
    Move thirdMove;
    // Only the player has power on their attacks, this is to give them an advantage
    int power;
    int xpNeededToLevelUp = 50;
    int levelToUnlockThirdMove = 3;
    boolean inBattle = false;

    // Constructor
    public Player(String name, Attack primary, Attack secondary, int power, int health, int energy, int defense, int speed, int luck, int level, int exp,
                  int maxHealth, int maxEnergy, int maxDefense, int maxSpeed, int maxLuck){
        super(name, primary, secondary, health, energy, defense, speed, luck, level, exp, maxHealth, maxEnergy, maxDefense, maxSpeed, maxLuck);
        this.primary = primary;
        this.secondary = secondary;
        this.power = power;
        thirdMove = new Move("Rest", 3);

    }
    // The player can battle enemies
    public void battle(Enemy enemy, Scanner scanner, Game game){
        setInBattle(true);
        // The enemy that has the higher speed goes first
        System.out.println("YOU ARE BATTLING A LEVEL " + enemy.getLevel() + " " + enemy.getName());
        // Check which enemy has a higher speed and should go first
        speedChecker(enemy,scanner,game);

    }
    // Check speed upon new battle
    void speedChecker(Enemy enemy, Scanner scanner, Game game){
        int playerSpeed = getSpeed();
        int enemySpeed = enemy.getSpeed();
        // if the player is faster than the enemy or of equal speed
        if(playerSpeed >= enemySpeed){
            // Take the player to the battle menu to begin their turn
            battleMenu(scanner, game, enemy);
        }
        // If the player is slower than the enemy
        else {
            // The enemy gets their turn first
            System.out.println("IT'S THE ENEMIES TURN");
            enemyTurn(enemy, game.getPlayer(), scanner, game);
        }
    }
    // What the enemy does if it's their turn
    void enemyTurn(Enemy enemy, Player player, Scanner scanner, Game game){
        Random random = new Random();
        int randomNum = random.nextInt(2) + 1;
        System.out.println("THE ENEMY IS MAKING THEIR MOVE...");
        System.out.println("ENEMY ENERGY: " + enemy.getEnergy());
        // If the random number is one, use primary move, if the random number is two, use secondary move, if they do not have energy, use synthesis
        if(randomNum == 1 && enemy.getEnergy() >= enemy.getPrimary().getEnergyUsed()){
            enemyPrimaryAttack(enemy, game.getPlayer());
        }
        else if (randomNum == 2 && enemy.getEnergy() >= enemy.getSecondary().getEnergyUsed()){
            enemySecondaryAttack(enemy, game.getPlayer());
        }
        else{
            enemyEnergySynthesis(enemy);
        }
        // Check if the player still has health after the enemies turn
        playerHealthChecker(scanner, game, enemy, player);
    }
    // Restore the enemies energy
    void enemyEnergySynthesis(Enemy enemy){
        System.out.println("THE ENEMY USED ENERGY SYNTHESIS");
        enemy.setEnergy(enemy.getMAX_ENERGY());
    }
    // Battle menu for the player
    public void battleMenu(Scanner scanner, Game game, Enemy enemy){
        // Make sure we always assign the enemy to avoid null pointers
        enemy = game.getCurrentEnemy();
        System.out.println("YOUR TURN");
        System.out.println("HP: " + getHealth() + "/" + getMAX_HEALTH());
        System.out.println("ENERGY: " + getEnergy() +"/" + getMAX_ENERGY());
        System.out.println("WHAT WOULD YOU LIKE TO DO?");
        System.out.println("1. USE MOVE");
        System.out.println("2. USE ITEM");
        System.out.println("3. RETREAT");
        try{
            int selection = scanner.nextInt();
            switch (selection) {
                case 1 -> moveMenu(scanner, enemy, game);
                case 2 -> itemMenu(game);
                case 3 -> {
                    setInBattle(false);
                    game.gameMenu();
                }
                default -> {
                    System.out.println("INVALID SELECTION");
                    battleMenu(scanner,game,enemy);
                }
            }
        }
        catch (Exception e){
            System.out.println("Your input cause an exception: " + e);
            scanner.nextLine();
            battleMenu(scanner,game,enemy);
        }
    }
    // Menu for using moves
    public void moveMenu(Scanner scanner, Enemy enemy, Game game){
        System.out.println("WHAT MOVE WOULD YOU LIKE TO USE?");
        System.out.println("1. " + getPrimaryMoveName() + " (ENERGY COST: " + getPrimary().getEnergyUsed() + ")");
        System.out.println("2. " + getSecondaryMoveName() + " (ENERGY COST: " + getSecondary().getEnergyUsed() + ")");
        // The player will learn a new move at level three, so account for that in the menu
        if(!(getLevel() >= levelToUnlockThirdMove)){
            System.out.println("3. Return back to battle menu");
        }
        else{
            System.out.println("3. " + getThirdMove().getMoveName() + " (ENERGY COST: " + getThirdMove().getEnergyUsed() + ")");
            System.out.println("4. Return back to battle menu");
        }
        int selection = scanner.nextInt();
        switch (selection) {
            case 1 -> primaryAttack(scanner, enemy, game);
            case 2 -> secondaryAttack(scanner, enemy, game);
            case 3 ->{
                if(!(getLevel() >= levelToUnlockThirdMove)){
                    battleMenu(scanner, game, enemy);
                }
                else{
                    useThirdMove();
                }
            }

            case 4 -> battleMenu(scanner, game, enemy);
            default -> {
                System.out.println("INVALID SELECTION");
                moveMenu(scanner,enemy,game);
            }
        }
    }

    private void useThirdMove() {
        // If the player has enough energy
        if(getEnergy() >= getThirdMove().getEnergyUsed()){
            // If the move is rest
            if (getThirdMove().getMoveName().equals("Rest")){
                setHealth(getHealth() + 10);
                setEnergy(getEnergy() - getThirdMove().getEnergyUsed());
                checkEntityHealth();
            }
        }
    }

    // Attacking the enemy
    void primaryAttack(Scanner scanner, Enemy enemy, Game game){
        // Get the attack
        Attack primary = getPrimary();
        // Get its damage
        int damage = primary.getDamage();
        // Calculate how much damage it would do
        int damageDone = getPower() + damage - enemy.getDefense();
        damageDoneChecker(damageDone);
        damageDone = onLuckyHitChecker(damageDone);
        // if the player's energy is greater than or equal to the attacks energy used
        if (getEnergy() >= primary.getEnergyUsed()){
            // Set that to the enemy's health
            enemy.setHealth(enemy.getHealth() - damageDone);
            // Remove energy from the player for using their attack
            removeEntityEnergyOnAttack(primary);
            System.out.println("YOU USED " + getPrimaryMoveName());
            System.out.println("IT DID " + damageDone);
            System.out.println("THE " + enemy.getName() + " has " + enemy.getHealth() + "/" + enemy.getMAX_HEALTH() + " HEALTH");
        }
        else{
            System.out.println("INSUFFICIENT ENERGY, YOU COULDN'T USE " + getPrimaryMoveName());
        }
        enemyHealthChecker(enemy, game, scanner);

    }
    void secondaryAttack(Scanner scanner, Enemy enemy, Game game){
        Attack secondary = getSecondary();
        int damage = secondary.getDamage();
        int damageDone = getPower() + damage - enemy.getDefense();
        damageDone = onLuckyHitChecker(damageDone);
        damageDoneChecker(damageDone);
        if (getEnergy() >= secondary.getEnergyUsed()){
            enemy.setHealth(enemy.getHealth() - damageDone);
            // Remove energy from the player for using their attack
            removeEntityEnergyOnAttack(secondary);
            System.out.println("YOU USED " + getSecondaryMoveName());
            System.out.println("IT DID " + damageDone);
            System.out.println("THE " + enemy.getName() + " has " + enemy.getHealth() + "/" + enemy.getMAX_HEALTH()+ " HEALTH");
        }
        else{
            System.out.println("INSUFFICIENT ENERGY, YOU COULDN'T USE " + getSecondaryMoveName());
        }
        enemyHealthChecker(enemy, game, scanner);
    }
    void enemyPrimaryAttack(Enemy enemy, Player player){
        // Get the attack
        Attack primary = enemy.getPrimary();
        // Get its damage
        int damage = primary.getDamage();
        // Calculate how much damage it would do
        int damageDone = damage - player.getDefense();
        damageDoneChecker(damageDone);
        // Alert the player
        System.out.println("THE ENEMY USED " + enemy.getPrimaryMoveName());
        // Set that to the player's health
        player.setHealth((player.getHealth() - damageDone));
        // Remove energy from the enemy for using their attack
        enemy.removeEntityEnergyOnAttack(primary);
        // Tell them their new health
        System.out.println("YOU HAVE " + getHealth() + "/" + getMAX_HEALTH() + " HEALTH");
    }
    void enemySecondaryAttack(Enemy enemy, Player player){
        // Get the attack
        Attack secondary = enemy.getSecondary();
        // Get its damage
        int damage = secondary.getDamage();
        // Calculate how much damage it would do
        int damageDone = damage - player.getDefense();
        damageDoneChecker(damageDone);
        // Alert the player
        System.out.println("THE ENEMY USED " + enemy.getSecondaryMoveName());
        // Set that to the player's health
        player.setHealth(player.getHealth() - damageDone);
        // Remove energy from the enemy for using their attack
        enemy.removeEntityEnergyOnAttack(secondary);
        // Tell them their new health
        System.out.println("YOU HAVE " + getHealth() + "/" + getMAX_HEALTH() + " HEALTH");
    }
    void enemyHealthChecker(Enemy enemy, Game game, Scanner scanner){
        // Check if the enemy still has health
        if(enemy.getHealth() > 0){
            enemyTurn(enemy, game.getPlayer(), scanner, game);
        }
        // Else the player has won the battle!
        else
        {
            setInBattle(false);
            System.out.println("YOU HAVE DEFEATED THE ENEMY");
            checkXPGain(enemy);
            checkMoneyGain(enemy, game);
            checkLevelUp();
            game.gameMenu();
        }
    }
    void playerHealthChecker(Scanner scanner, Game game, Enemy enemy, Player player){
        // if the player still has health after the attacks, let the battle continue
        if (player.getHealth() > 0){
            battleMenu(scanner, game, enemy);
        }
        // else the game is over since the player died
        else{
            System.out.println("GAME OVER");
        }

    }
    // Negative damage does not exist
    void damageDoneChecker(int damageDone){
        if (damageDone < 0){
            damageDone = 0;
        }
    }
    // Gain xp (when you defeat an enemy)
    void checkXPGain(Enemy enemy){
        int expGained = enemy.getLevel() * 25;
        System.out.println("YOU GAINED " + expGained + " EXP");
        setExp(getExp() + expGained);
    }
    // Gain money as well
    void checkMoneyGain(Enemy enemy, Game game){
        int moneyGained = enemy.getLevel() * 20;
        System.out.println("YOU GAINED $" + moneyGained);
        game.setShopBalance(game.getShopBalance() + moneyGained);
    }
    // Check if you reach the xp threshold to level up
    void checkLevelUp(){
        if(getExp() / xpNeededToLevelUp >= 1){
            int remainingExp = xpNeededToLevelUp % getExp();
            setLevel(getLevel() + 1);
            System.out.println("LEVEL UP! YOU ARE NOW LEVEL " + getLevel());
            if (getLevel() == levelToUnlockThirdMove){
                System.out.println("YOU UNLOCKED A NEW MOVE");
            }
            increaseAttributesOnLevelUp();
            seeEntityInfo();
            setExp(remainingExp);
            xpNeededToLevelUp *= 2;
        }
    }
    // Check if the player has a lucky hit
    int onLuckyHitChecker(int damageDone){
        // When a player hits the enemy
        int luck = getLuck();
        Random random = new Random();
        int randomNum = random.nextInt(50) + 1;
        for (int i = 0; i < luck; i++){
            int secondRandomNum = random.nextInt(50) + 1;
            // If the two numbers happen to be equal
            if (randomNum == secondRandomNum){
                // The attack power increases by 50%
                int newDamage = (int) Math.round(damageDone * 2);
                System.out.println("A LUCKY HIT OCCURRED");
                return newDamage;
            }
        }
        return damageDone;
    }
    // Increase attributes when the player levels up
    void increaseAttributesOnLevelUp(){
        setPower(getPower() + 1);
        setMAX_HEALTH(getMAX_HEALTH() + 2);
        setMAX_ENERGY(getMAX_ENERGY() + 1);
        setMAX_DEFENSE(getMAX_DEFENSE() + 1);
        setMAX_LUCK(getMAX_LUCK() + 1);
        setMAX_SPEED(getMAX_SPEED() + 1);
        setHealth(getHealth() + 2);
        setEnergy(getEnergy() + 1);
        setDefense(getMAX_DEFENSE());
        setSpeed(getMAX_SPEED());
        setLuck(getMAX_LUCK());
    }
    // Go to the game item menu
    public void itemMenu(Game game) {
        game.useItemMenu();
    }
    // Getters and setters
    @Override
    public Attack getPrimary() {
        return primary;
    }

    @Override
    public Attack getSecondary() {
        return secondary;
    }
    public void setSecondary(Attack secondary) {
        this.secondary = secondary;
    }

    public Move getThirdMove() {
        return thirdMove;
    }
    public boolean isInBattle() {
        return inBattle;
    }

    public void setInBattle(boolean inBattle) {
        this.inBattle = inBattle;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public void seeEntityInfo() {
        super.seeEntityInfo();
        // Only the player has power on their attacks, this is to give them an advantage
        System.out.println("POWER: " + getPower());
        if (getLevel() >= levelToUnlockThirdMove){
            System.out.println("THIRD MOVE:" + getThirdMove().getMoveName());
        }
    }
}
