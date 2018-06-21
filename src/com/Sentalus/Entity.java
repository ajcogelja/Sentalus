/*
Name: Alex Cogelja
Date: 4/5/2018
Purpose: A general entity class with common traits of entities
 */

package com.Sentalus;

import javafx.scene.image.Image;

import java.io.Serializable;

public class Entity implements Serializable{

    //sprite which all entities will have
    public Image sprite;

    //Name of entity
    public String name;

    //health value
    public int health;

    //default constructor
    public Entity(){
        sprite = null;
        name = "Unnamed";
        health = 100;
    }

    public Entity(int health){
        this.health = health;
        name = "Unnamed";
        sprite = null;
    }

    public Entity(int health, String name){
        this.health = health;
        this.name = name;
        sprite = null;
    }

    public Entity(int health, String name, Image sprite){
        this.health = health;
        this.name = name;
        this.sprite = sprite;

    }

}
