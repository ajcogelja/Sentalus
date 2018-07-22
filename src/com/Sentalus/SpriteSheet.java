package com.Sentalus;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class SpriteSheet {

    private BufferedImage sheet;

    //constructor for the
    public SpriteSheet(String url){
        try{
            sheet = ImageIO.read(new FileInputStream(url));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //to select region to make sprite from
    public WritableImage initializeSprite(int x, int y, int width, int height){
        return SwingFXUtils.toFXImage(sheet.getSubimage(x, y, width, height), null);
    }


}
