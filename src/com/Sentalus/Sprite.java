package com.Sentalus;
import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Sprite {

    final int WIDTH = 64;
    final int HEIGHT = 64;

    BufferedImage sheet;
    BufferedImage grass;
    BufferedImage mountain;
    WritableImage grassWI;
    WritableImage mountainWI;
    Image fxTile;

    public Sprite(){
        try {
            sheet = ImageIO.read(new FileInputStream("Res/Realms_Tileset_32x32.png"));
            grass = sheet.getSubimage(0, 0, WIDTH, HEIGHT);
            mountain = sheet.getSubimage(64, 64, 64, 64);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WritableImage getGrassImage(){
        grassWI = SwingFXUtils.toFXImage(grass, grassWI);
        return grassWI;
    }

    public WritableImage getMountainImage(){
        mountainWI = SwingFXUtils.toFXImage(mountain, mountainWI);
        return mountainWI;
    }

}
