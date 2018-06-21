/*
Name: Alex Cogelja
Date: 4/4/2018
Purpose: The main client UI when launching the game
 */

package com.Sentalus;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;
import java.io.FileInputStream;
import java.io.IOException;

public class Client extends Application{

    //load background and test files
    FileInputStream fis;
    //creating the Scene that will be rendered and hold the UI
    public Scene scene;

    TextField username;

    Image logo;
    Image image;
    ImageView ivtest = new ImageView();

    //Main method which launches client through javaFX
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        //load the image
        try{
            //ignore this, icon is fucked
//            logo = new Image("C:\\Users\\op3er\\Desktop\\college\\cs 180 gld\\Sentalus\\Res\\TempIcon.png");

        }catch(Exception e){
            e.printStackTrace();
        }

        username = new TextField("Enter Your Username Here");
        username.setPrefColumnCount(20);
        username.setLayoutX(150);
        username.setLayoutY(280);


        //Creating the buttons we need
        Button start = new Button("Start");
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //useless for now because the game doesnt exist
                new Game(username.getText());
                primaryStage.close();
            }
        });
        start.setLayoutX(150);
        start.setLayoutY(450);
        start.setMinWidth(100);
        start.setMinHeight(75);

        Button exit = new Button("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });
        exit.setLayoutX(350);
        exit.setLayoutY(450);
        exit.setMinWidth(100);
        exit.setMinHeight(75);

        //creating the pane of the scene
        Pane pane = new Pane();

        //actually creating the scene now
        scene = new Scene(pane, 600, 600);

        //initializing the stage
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.setIconified(false);
        primaryStage.setTitle("Sentalus Game Client");

        //draw the logo in the middle
        //graphicsContext.drawImage(logo, 100, 100, 100, 100);

        //Test drawing images
        try{
            fis = new FileInputStream("Res/Untitled.png");
            image = new Image(fis);
            logo = new Image(new FileInputStream("Res/TempIcon.png"));
        }catch (IOException e){

        }

        //setting the icon - Ignore as well because its fucked
        primaryStage.getIcons().add(logo);

        ivtest.setImage(image);
        ivtest.setFitWidth(pane.getWidth());
        ivtest.setFitHeight(pane.getHeight());
        ivtest.setSmooth(true);
        ivtest.setCache(true);
        pane.getChildren().add(ivtest);

        //modify canvas properties before adding it
        scene.setFill(Color.MAGENTA); //deliberately obnoxious

        //add canvas to the pane
        //pane.getChildren().add(canvas);

        //add the buttons on top of the canvas
        pane.getChildren().add(start);
        pane.getChildren().add(exit);
        pane.getChildren().add(username);

        //Set the scene... be a showstopper!
        primaryStage.setScene(scene);

        //showing the stage
        primaryStage.show();

    }
}
