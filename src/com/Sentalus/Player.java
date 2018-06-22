package com.Sentalus;

import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.Serializable;

public class Player extends Entity implements Serializable{

    private int playerID;

    private double mapLocationX; //location = 0 + screenloc - mapoffset
    private double mapLocationY;

    //health bar
    transient private Rectangle healthBar;
    transient private Rectangle damageBar;

    private static double moveSpeed = 5;

    //health
    private int health;

    //name of the player
    private String name;

    private String imagePath;

    //appearance/Sprite
    transient private Image sprite;
    transient private ImageView character;

    //Location
    private double xPos;
    private double yPos;

    //borders
    private double width, height;

    private final int maxHealth = 100;

    //movespeed getter
    static double getMoveSpeed(){
        return moveSpeed;
    }

    //position getters
    public double getXPos() {
        return xPos;
    }
    public double getYPos() {
        return yPos;
    }

    //position setters
    public void setXPos(double xPos) {
        this.xPos = xPos;
        try {
            character.setX(this.xPos);
        } catch (Exception e){
            System.out.println("character X not moved");
        }
//        healthBar.setX(xPos);
    }
    public void setYPos(double yPos) {
        this.yPos = yPos;
        try {
            character.setY(this.yPos);
        } catch (Exception e){
            System.out.println("character Y not moved");
        }
//        healthBar.setX(yPos - 30);
    }

    public void moveY(double deltaY){
        this.yPos = this.yPos + deltaY;
        character.setY(yPos);
//        healthBar.setY(this.yPos - 30);
//        damageBar.setY(this.yPos - 30);
    }

    public void moveX(double deltaX){
        this.xPos = xPos + deltaX;
        character.setX(xPos);
//        healthBar.setX(this.xPos);
//        damageBar.setX(this.xPos);
    }

    public double getMapLocationX() {
        return mapLocationX;
    }

    public double getMapLocationY() {
        return mapLocationY;
    }

    public void setMapLocationX(Map map) {
        this.mapLocationX = -map.getxOffset() + xPos;
    }

    public void setMapLocationY(Map map){
        this.mapLocationY = -map.getyOffset() + yPos;
    }

    public double getLeftBound(){
        return this.xPos;
    }

    public double getRightBound(){
        return this.xPos + width;
    }

    public double getTopBound(){
        return this.yPos;
    }

    public double getBottomBound(){
        return this.yPos + height;
    }


    //name getter and setter
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    //health getter and setter
    public int getHealth(){
        return health;
    }
    public void setHealth(int health){
        this.health = health;
    }

    //sprite getter and setter
    public ImageView getSprite() {
        return character;
    }
    public void setSprite(ImageView character){
        this.character = character;
    }

    public void setPlayerID(int playerID){
        this.playerID = playerID;
    }

    public int getPlayerID(){
        return this.playerID;
    }

    //public void setSprite(Image sprite){
    //    this.sprite = sprite;
    //}

    public Rectangle getHealthBar() {
        return healthBar;
    }

    public Rectangle getDamageBar() {
        return damageBar;
    }

    public int getMaxHealth(){
        return maxHealth;
    }

    public double getHeight(){
        return sprite.getHeight();
    }
    public double getWidth(){
        return sprite.getHeight();
    }


    //initializing player constructor
    public Player(int health, String name, String imagePath){
        this.health = health;
        this.name = name;
        this.imagePath = imagePath;
        mapLocationX = 0;
        mapLocationY = 0;
        FileInputStream fis;
        try{
            fis = new FileInputStream(imagePath);
            this.sprite = new Image(fis);
        } catch (Exception e){
            e.printStackTrace();
        }
        character = new ImageView(this.sprite);
        healthBar = new Rectangle();
        damageBar = new Rectangle();
        //healthBar.setX(this.getXPos());
        //healthBar.setY(this.getYPos() - 25);
    }

}
