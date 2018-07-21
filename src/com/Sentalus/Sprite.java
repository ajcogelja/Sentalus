package com.Sentalus;
import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Sprite {

    BufferedImage sheet;
    BufferedImage tile;
    WritableImage wi;
    Image fxTile;

    public Sprite(){
        try {
            sheet = ImageIO.read(new FileInputStream("Res/Realms_Tileset_32x32.png"));
            tile = sheet.getSubimage(0, 0, 32, 32);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WritableImage getfxImage(){
        wi = SwingFXUtils.toFXImage(tile, wi);
        return wi;
    }

}
