/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidad.Enemigos;

import Entidad.Animation;
import Entidad.Enemigo;
import TileMap.TileMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author MarioDiaz
 */
public class Fuego extends Enemigo {
    
    private BufferedImage[] sprites;

    public Fuego(TileMap tm) {
        super(tm);
        
        
        iWidth = 77;
        iHeight = 156;
        iColWidth = 50;
        iColHeight = 150;
        
        iHealth = iMaxHealth = 5;
        iDamage = 1;
        
        try{
            BufferedImage spritesheet = ImageIO.read(
                getClass().getResourceAsStream(
                        "/Resources/Enemigos/firesheeet.png"));
            
            sprites = new BufferedImage[9];
            for (int i=0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i * iWidth, 0,
                        iWidth, iHeight);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(160);
    }
    
    @Override
    public void update() {
        animation.update();
    }
    
    public void draw(Graphics2D g) {        
        setMapPosition();
        super.draw(g);
    }
    
    
    
    
    
    
}
