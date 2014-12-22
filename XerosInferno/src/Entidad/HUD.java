/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidad;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author MarioDiaz
 */
public class HUD {
    
    private Osmy osmy;
    private BufferedImage[] sprites;
    private Font font;
    private int iWidth;
    private int iHeight;
    private  Animation animation;
    
    public HUD(Osmy o) {
        osmy = o;
        
        iWidth = 100;
        iHeight = 100;
        
        try {
            BufferedImage spritesheet = ImageIO.read(
                getClass().getResourceAsStream("/Resources/HUD/tilehud_2.png"));
            
            sprites = new BufferedImage[4];
            for  (int i=0; i < sprites.length; i++ ) {
                sprites[i] = spritesheet.getSubimage(i * iWidth, 0,
                        iWidth, iHeight);
            }
            
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        animation = new Animation();
        animation.setFrames(sprites);
    }
    
    public void draw (Graphics2D g) {
        //Dibujando el HUD
        //Dibujando la Cara
        if(osmy.boolFiring) {
            g.drawImage(animation.getExactImage(0), 0, 0,null);
        }
        else {
            g.drawImage(animation.getExactImage(1), 0, 0,null);
        }
        //Dibujando la vida
        for (int i=0; i < osmy.iHealth; i++) {
            g.drawImage(animation.getExactImage(2), 80 + (i*45), -10, null);
        }        
        //Dibujando la mana
        for (int i=0; i < osmy.getMana(); i++) {
            g.drawImage(animation.getExactImage(3), 80 + (i*45), 35, null);
        }       
    }
}
