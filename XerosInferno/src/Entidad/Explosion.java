/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidad;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


/**
 *
 * @author MarioDiaz
 */
public class Explosion {
    
    private int iX;
    private int iY;
    private int iXmap;
    private int iYmap;
    private int iWidth;
    private int iHeight;
    
    private Animation animation;
    private BufferedImage[] sprites;
    
    private boolean boolRemove;
    
    public Explosion(int x, int y) {
        this.iX = x;
        this.iY = y;
        
        iWidth = 30;
        iHeight = 30;
        
        try {
            BufferedImage spritesheet = ImageIO.read(
                getClass().getResourceAsStream("/ResourcesTest/explosion.gif"));
            
            sprites = new BufferedImage[6];
            
            for(int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i * iWidth,
                        0, iWidth, iHeight);
            }
            
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(70);
    }
    
    public void update() {
        animation.update();
        if(animation.hasPlayedOnce()) {
            boolRemove = true;
        }
    }
    
    public boolean shouldRemove() { return boolRemove; }
    
    public void setMapPosition(int x, int y) {
        iXmap = x;
        iYmap = y;
    }
    
    public void draw(Graphics2D g) {
        g.drawImage(animation.getImage(),
                iX + iXmap - iWidth / 2,
                iY + iYmap - iHeight / 2, null);
    }
    
}
