/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidad;

import TileMap.TileMap;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author MarioDiaz
 */
public class Portal extends Objeto {
    
    private BufferedImage[] sprites;

    public Portal(TileMap tm) {
        super(tm);
        boolFacingRight = true;
        iWidth = iHeight = 40;
        iColWidth = 20;
        iColHeight = 20;
        
        try {
            BufferedImage spritesheet = ImageIO.read(
		getClass().getResourceAsStream("/ResourcesTest/Teleport.gif")
			);
            sprites = new BufferedImage[9];
            for(int i = 0; i < sprites.length; i++) {
		sprites[i] = spritesheet.getSubimage(
		i * iWidth, 0, iWidth, iHeight
				);
			}
                        animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(1);
		}
	catch(Exception e) {
			e.printStackTrace();
	}
    }
    
    public void update() {
        animation.update();
    }
    public void draw(Graphics2D g) {
        setMapPosition();
        super.draw(g);
    }
}
