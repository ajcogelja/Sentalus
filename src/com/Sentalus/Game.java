/*
Name: Alex Cogelja
Date: 4/4/2018
Purpose: The game which puts together the map, players, and connects to the server
 */
package com.Sentalus;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Game extends Application {

    //player list
    ArrayList<Player> players = new ArrayList<>();
    HashMap<String, double[]> playerData;

    //fpsCounter
    private float fpsCounter = 0;
    private Text text = new Text(Float.toString(fpsCounter));

    //movespeed variable
    private double charMoveSpeed = Player.getMoveSpeed();

    //save timer
    private AnimationTimer autoSave;

    //movement booleans
    private boolean left, right, up, down = false;

    //other display booleans
    private boolean showMenu = false;

    //getting background data and stuff
    //private ProfileReader pr = new ProfileReader();

    //javafx assets handles
    private Scene scene;
    private Pane pane;
    private Stage stage = new Stage();

    //images for the assets
    private Image character;
    private Image map;

    //username for player on this client
    String username;
    private double timeVal = 0;

    //The player!
    Player user;
    //The map!
    Map currentMap;
    //test map object

    //Menu Buttons
    Button exit;
    Button closeMenu;
    private ImageView menuBackground;

    //writing to files
    File profile;
    FileOutputStream fos;
    DataOutputStream dos;

    //Player Group
    Rectangle health;
    Rectangle damage;

    ColorAdjust time;

    private byte testMap[][] = Maps.getOverworld();

    public synchronized void addObjects(Node node) {
        pane.getChildren().add(node);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //load map and character
        try {
            character = new Image(new FileInputStream("Res/testGif.gif"));
            //objTest = new Image(new FileInputStream("Res/testObstacle.png"));
            //map = pr.getMap();
            map = new Image(new FileInputStream("Res/testMap2.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //fps counter
        text.setText(username);
        text.setFont(new Font(20));
        text.setFill(Color.BLUE);
        text.setX(10);
        text.setY(30);
        //fps counter

        pane = new Pane();
        scene = new Scene(pane, 1000, 700, Color.MAGENTA);
        //resize and handle assets
        user = new Player(100, username, "Res/testGif.gif");
        //currentMap = new Map("Test Map", 0, pr.getMap());
        currentMap = new Map("Test Map", 0, null);
        for (int x = 0; x < testMap[0].length; x++) {
            for (int y = 0; y < testMap.length; y++) {
                try {
                    if (testMap[y][x] == 1) {
                        MapObject obj = new MapObject(new Image(new FileInputStream("Res/testObstacle.png")), true);
                        obj.setObstaclePos(/*pr.getMapX()*/ + (x * 128), /*pr.getMapY()*/ + (y * 128));
                        currentMap.addObject(obj);
                    }
                    if (testMap[y][x] == -1) {
                        MapObject obj = new MapObject(new Image(new FileInputStream("Res/grass.png")), false);
                        obj.setObstaclePos(/*pr.getMapX()*/ + (128 * x), /*pr.getMapY()*/ + (y * 128));
                        currentMap.addObject(obj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        user.setXPos(130);//pr.getPlayerXLoc());
        user.setYPos(130);//pr.getPlayerYLoc());
        addObjects(user.getSprite());
        currentMap.setX(0);//pr.getMapX());
        currentMap.setY(0);//pr.getMapY());
        pane.getChildren().add(currentMap.getImageView());
        for (int i = 0; i < currentMap.getMapObjects().size(); i++) {
            addObjects(currentMap.getMapObjects().get(i).getObstacle());
        }
        //HEALTH BAR
        health = user.getHealthBar();
        health.setFill(Color.LIME);
        health.setHeight(16);
        health.setWidth(128 * (user.getHealth() / 100));
        //health.setX(user.getXPos());
        //health.setY(user.getYPos() - 25);
        //END OF HEALTH BAR
        //DAMAGE BAR
        damage = user.getDamageBar();
        damage.setFill(Color.RED);
        damage.setHeight(health.getHeight());
        damage.setWidth(health.getWidth());
        //damage.setY(health.getY());
        //damage.setX(health.getX());
        //END OF DAMAGE BAR
        pane.getChildren().add(text);
        stage.setTitle("Sentalus");
        stage.setResizable(false);
        //stage.setFullScreen(true);
        stage.setScene(scene);
        primaryStage.show();

    }

    public void progressTime(){
        time.setBrightness(.3 + (.3 * Math.sin(timeVal/50)));
        for (MapObject m: currentMap.getMapObjects()) {
            m.getObstacle().setEffect(time);
        }
        timeVal++;
    }

    public Game(String username) {
            this.username = username;
        try {
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        time = new ColorAdjust();
        time.setBrightness(1);

        //initialize menu buttons
        exit = new Button("Exit Game");
        exit.setLayoutX(200);
        exit.setLayoutY(100);
        exit.setVisible(false);
        try{
            menuBackground = new ImageView(new Image(new FileInputStream("Res/Menu Banner.png")));
        }
        catch (Exception e){

        }
        menuBackground.setX(200);
        menuBackground.setY(60);
        menuBackground.setVisible(false);
        addObjects(menuBackground);
        addObjects(exit);

        //launch server
        GameClient gameClient = new GameClient("localhost", 1900);
        gameClient.begin();

        scene.setOnKeyPressed(event -> {

            switch (event.getCode()) {
                case W:
                case UP:
                    //down = false;
                    up = true;
                    break;
                case S:
                case DOWN:
                    down = true;
                    //up = false;
                    break;
                case A:
                case LEFT:
                    left = true;
                    //right = false;
                    break;
                case D:
                case RIGHT:
                    //left = false;
                    right = true;
                    break;
                case TAB:
                    showMenu = true;
                    break;

            }
        });
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W:
                case UP:
                    up = false;
                    break;
                case S:
                case DOWN:
                    down = false;
                    break;
                case A:
                case LEFT:
                    left = false;
                    break;
                case D:
                case RIGHT:
                    right = false;
                    break;
            }
        });
        //save loop for file writing

        profile = new File("Res/User" + "Profile.dat");
        autoSave = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {

                if (now - lastUpdate >= 40000000){
                    progressTime();
                }

                if (now - lastUpdate >= 500000000) {
                    for (Player p : players) {
                        System.out.println(p.getName());
                    }
                    try {
                        fos = new FileOutputStream(profile, false);
                        dos = new DataOutputStream(fos);
                        fos.flush();
                        dos.flush();
                        dos.writeInt(0);
                        dos.writeDouble(user.getXPos());
                        dos.writeDouble(user.getYPos());
                        dos.writeDouble(currentMap.getXPos());
                        dos.writeDouble(currentMap.getYPos());
                        fos.close();
                        dos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    lastUpdate = now;
                }

            }
        };

        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                health.setWidth(128 * ((float) user.getHealth() / (float) user.getMaxHealth()));
                user.setMapLocationX(currentMap);
                user.setMapLocationY(currentMap);


                if (up) {
                    if (currentMap.checkCollisionUp(user.getYPos(), user.getXPos(), user.getXPos() + user.getWidth(), charMoveSpeed)) {
                        if (user.getYPos() >= 60) {
                            user.moveY(-1 * charMoveSpeed);
                        } else if (currentMap.getYPos() <= -10) {
                            currentMap.moveObjectsY(charMoveSpeed);
                        }
                    }
                }
                if (down) {
                    if (currentMap.checkCollisionDown(user.getYPos() + user.getHeight(), user.getXPos(), user.getXPos() + user.getWidth(), charMoveSpeed)) {
                        if (user.getYPos() + user.getHeight() <= 540) {
                            user.moveY(charMoveSpeed);

                        } else if (currentMap.getYPos() - stage.getHeight() >= -1 * map.getHeight()) {
                            currentMap.moveObjectsY(-1 * charMoveSpeed);

                        }
                    }
                }
                if (left) {
                    if (currentMap.checkCollisionLeft(user.getXPos(), user.getYPos() + user.getHeight(), user.getYPos(), charMoveSpeed)) {
                        if (user.getXPos() >= 60) {
                            user.moveX(-1 * charMoveSpeed);

                        } else if (currentMap.getXPos() <= -10) {
                            currentMap.moveObjectsX(charMoveSpeed);
                        }
                    }
                }
                if (right) {
                    if (currentMap.checkCollisionRight(user.getXPos() + user.getWidth(), user.getYPos() + user.getHeight(), user.getYPos(), charMoveSpeed)) {
                        if (user.getXPos() + user.getWidth() <= 540) {
                            user.moveX(charMoveSpeed);
                        } else if (currentMap.getXPos() - stage.getWidth() >= -1 * map.getWidth()) {
                            currentMap.moveObjectsX(-1 * charMoveSpeed);
                        }
                    }
                }
                if (showMenu){
                    exit.setVisible(true);
                    closeMenu.setVisible(true);
                    menuBackground.setVisible(true);
                }
                lastUpdate = now;
                //System.out.println(user.getName() + " X: " + user.getXPos() + " Y: " + user.getYPos());
            }
        };

        gameLoop.start();
        autoSave.start();
    }

    //Server Stuff from here on down
    final class GameClient{

        //Server Stuff
        private Socket socket;
        private ObjectInputStream fromServer;
        private ObjectOutputStream toServer;

        private String server;
        private final int port;

        private GameClient(String server, int port){
            this.server = server;
            this.port = port;

        }

        private boolean begin(){
            try{
                socket = new Socket(server, port);
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                fromServer = new ObjectInputStream(socket.getInputStream());
                toServer = new ObjectOutputStream(socket.getOutputStream());
            }catch (Exception e){
                e.printStackTrace();
            }
            Runnable run = new GameListener();
            Thread thread = new Thread(run);
            thread.start();
            try{
                toServer.writeObject(username);
            /*    //toServer.writeObject(user);
                //System.out.println(user.getName() + " was written to the server");
                toServer.writeObject(username);
                FileInputStream fis;
                fis = new FileInputStream("Res/testGif.gif");
                int c;
                while((c = fis.read()) > -1){
                    toServer.write(c);
                }
                */
            }catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }


        private final class GameListener implements Runnable{ //server basically. Gets data from server

            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(1);
                        user.setMapLocationX(currentMap);
                        user.setMapLocationY(currentMap);
                        toServer.writeObject(user);
                        toServer.writeObject(username);
                        toServer.writeObject(user.getMapLocationX());
                        toServer.writeObject(user.getMapLocationY());
                        System.out.println(user.getName() + " sent");
                        Player fetchedUser = (Player) fromServer.readObject();
                        double fetchedX = (double) fromServer.readObject();
                        double fetchedY = (double) fromServer.readObject();
                        System.out.println("Player: " + fetchedUser.getName() + " received from server");
                        if (fetchedUser.getName().equals(username)){
                            System.out.println("fetched user = player");
                        } else {
                            boolean exists = false;
                            for (Player p : players) {
                                if (p.getName().equals(fetchedUser.getName())){
                                    exists = true;
                                    System.out.println("fetched user exists in players list");
                                    p.setXPos(fetchedX + currentMap.getXPos());
                                    p.setYPos(fetchedY + currentMap.getYPos());
                                    break;
                                }
                            }
                            if (!exists){
                                fetchedUser.setSprite(new ImageView(character));
                                players.add(fetchedUser);
                                System.out.println("fetched user added to list");
                                Platform.runLater(() -> addObjects(fetchedUser.getSprite()));
                            }
                        }

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

    }


}
