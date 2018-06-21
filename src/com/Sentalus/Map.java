/*
Name: Alex Cogelja
Date: 4/6/2018
Purpose: The tile map
 */

package com.Sentalus;

import javafx.scene.image.*;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private int mapNum;
    private ArrayList<MapObject> mapObjects = new ArrayList<>();
    private String mapName;
    private Image mapBackground;
    private double xPos, yPos = 0;
    private double xOffset, yOffset = 0;
    private ImageView mapSprite;

    //constructor should take the name and starting location maybe?
    public Map(String mapName, int mapNum, Image mapBackground){
        this.mapName = mapName;
        this.mapBackground = mapBackground;
        mapSprite = new ImageView(mapBackground);
    }

    public void addObjects(List<MapObject> mapObjects){
        this.mapObjects.addAll(mapObjects);
    }

    public void addObject(MapObject mapObject){
        mapObjects.add(mapObject);
    }

    public List<MapObject> getMapObjects() {
        return mapObjects;
    }

    public boolean checkCollisionLeft(double xPos, double lowerY, double upperY, double deltaX){ //works wonderfully
        for (MapObject m: mapObjects) {
            if(m != null && m.isSolid()) {
                if ((xPos - deltaX <= m.getRightBound() && xPos - deltaX >= m.getLeftBound() && lowerY <= m.getBottomBound() && lowerY >= m.getTopBound())
                        || (xPos - deltaX <= m.getRightBound() && xPos - deltaX >= m.getLeftBound() && upperY >= m.getTopBound() && upperY <= m.getBottomBound())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkCollisionUp(double yPos, double lowerX, double upperX, double deltaY){ //works kinda
        for (MapObject m: mapObjects) {
            if(m != null && m.isSolid()){
                if ((yPos - deltaY <= m.getBottomBound() && yPos - deltaY >= m.getTopBound() && lowerX <= m.getRightBound() && lowerX >= m.getLeftBound())
                        || (yPos - deltaY <= m.getBottomBound() && yPos - deltaY >= m.getTopBound() && upperX >= m.getLeftBound() && upperX <= m.getRightBound())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkCollisionRight(double xPos, double lowerY, double upperY, double deltaX){ //works wonderfully
        for (MapObject m: mapObjects) {
            if(m != null && m.isSolid()) {
                if ((xPos + deltaX >= m.getLeftBound() && xPos + deltaX <= m.getRightBound() && lowerY <= m.getBottomBound() && lowerY >= m.getTopBound())
                        || (xPos + deltaX <= m.getRightBound() && xPos + deltaX >= m.getLeftBound() && upperY >= m.getTopBound() && upperY <= m.getBottomBound())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkCollisionDown(double yPos, double lowerX, double upperX, double deltaY){ //works kinda
        for (MapObject m: mapObjects) {
            if(m != null && m.isSolid()) {
                if ((yPos + deltaY >= m.getTopBound() && yPos + deltaY <= m.getBottomBound() && lowerX <= m.getRightBound() && lowerX >= m.getLeftBound())
                        || (yPos + deltaY >= m.getTopBound() && yPos + deltaY <= m.getBottomBound() && upperX >= m.getLeftBound() && upperX <= m.getRightBound())) {
                    return false;
                }
            }
        }
        return true;
    }


    public void moveObjectsX(double deltaX){
        this.xPos = getXPos() + deltaX;
        xOffset = this.xPos;
        mapSprite.setX(getXPos());
        for (MapObject m: mapObjects) {
            if(m != null) {
                m.moveX(deltaX);
            }
        }
    }

    public void moveObjectsY(double deltaY){
        this.yPos = getYPos() + deltaY;
        yOffset = this.yPos;
        mapSprite.setY(getYPos() + deltaY);
        for (MapObject m : mapObjects) {
            if(m != null) {
                m.moveY(deltaY);
            }
        }
    }

    public ImageView getImageView(){
        return this.mapSprite;
    }

    public void setX(double xPos){
        xOffset = xPos;
        this.xPos = xPos;
        mapSprite.setX(xPos);
    }
    public void setY(double yPos){
        yOffset = yPos;
        this.yPos = yPos;
        mapSprite.setY(yPos);
    }

    public double getXPos(){
        return this.xPos;
    }
    public double getYPos(){
        return this.yPos;
    }

    public double getxOffset() {
        return xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }
}
