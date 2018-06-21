package com.Sentalus;
import java.io.*;

import javafx.scene.image.Image;

public class ProfileReader {

    private String mapPath;
    private int mapNum;
    private double playerXLoc, playerYLoc;
    private double mapX, mapY;
    private Image map;
    private FileInputStream fis;
    private DataInputStream dis;
    private ObjectInputStream ois;
    private File userData;

    public ProfileReader(){

        try{
            userData = new File("Res/UserProfile.dat");
            if(!userData.exists()){
                userData.createNewFile();
                FileOutputStream fos = new FileOutputStream(userData);
                DataOutputStream dos = new DataOutputStream(fos);
                fos.flush();
                dos.flush();
                dos.writeInt(0);
                dos.writeDouble(0);
                dos.writeDouble(0);
                dos.writeDouble(0);
                dos.writeDouble(0);
                dos.close();
                fos.close();
            }
            fis = new FileInputStream(userData);
            dis = new DataInputStream(fis);
            //read in the data
            /* map num */ dis.readInt();//mapNum = fis.read();
            /* player location X and Y*/ playerXLoc = dis.readDouble(); playerYLoc = dis.readDouble();//xLoc = fis.read(); yLoc = fis.read();
            /* map locations */mapX = dis.readDouble(); mapY = dis.readDouble();
            fis.close();
            dis.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        //set the map path
        setMapPath(mapNum);
    }

    //needs return methods so the info after its been read can be accessed from the Game class
    public void setMapPath(int num){
        switch(num){
            case 0:
                mapPath = "Res/testMap2.png";
                break;
            default:
                System.out.println("map num = " + mapNum);
                mapPath = "MAP DOESN'T EXIST";
                break;
        }
    }

    public Image getMap() {

        try{
            map = new Image(new FileInputStream(mapPath));
        }catch (IOException e){
            System.out.println(mapPath);
            e.printStackTrace();
        }

        return map;
    }

    public double getPlayerXLoc(){
        return playerXLoc;
    }

    public double getPlayerYLoc() {
        return playerYLoc;
    }

    public double getMapX() {
        return mapX;
    }

    public double getMapY() {
        return mapY;
    }

    public int getMapNum(){
        return mapNum;
    }

}
