package com.Sentalus;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapObject {

    private Image obstacleImage;
    private ImageView obstacle;
    private double xPos, yPos, width, height;
    public boolean solid;

    public MapObject(Image obstacleImage, boolean solid){
        this.solid = solid;
        this.obstacleImage = obstacleImage;
        obstacle = new ImageView();
        obstacle.setImage(this.obstacleImage);
        xPos = obstacle.getX();
        yPos = obstacle.getY();
        width = obstacleImage.getWidth();
        height = obstacleImage.getHeight();
    }

    public ImageView getObstacle(){
        return obstacle;
    }

    public double getObstacleWidth(){
        return obstacleImage.getWidth();
    }

    public double getObstacleHeight(){
        return obstacleImage.getHeight();
    }

    public void setObstaclePos(double xPos, double yPos){
        this.xPos = xPos;
        this.yPos = yPos;
        obstacle.setX(this.xPos);
        obstacle.setY(this.yPos);
    }

    public void moveX(double deltaX){
        this.xPos = this.xPos + deltaX;
        obstacle.setX(this.xPos);
    }

    public void moveY(double deltaY){
        this.yPos = this.yPos + deltaY;
        obstacle.setY(this.yPos);
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

    public boolean isSolid(){
        return solid;
    }

    public double getX(){
        return obstacle.getX();
    }

    public double getY(){
        return obstacle.getY();
    }

}
