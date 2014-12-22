/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameState;

import Entidad.Enemigo;
import Entidad.Enemigos.Fuego;
import Entidad.Enemigos.Mosseth;
import Entidad.Enemigos.SpiritFire;
import Entidad.Explosion;
import Entidad.HUD;
import Entidad.Osmy;
import Main.GamePanel;
import TileMap.Background;
import TileMap.Tile;
import TileMap.TileMap;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author MarioDiaz
 */
public class Boss1State extends GameState {
    
    //creando variables tileMap y background
    private TileMap tileMap;
    private Background bg;
    
    //Creando el objeto osmy (personaje principal)
    private Osmy osmy;
    
    //prueba de Mosseth
    private Mosseth eneMosseth;
    
    //creando el HUD
    private HUD hud;
    //Arreglo de enemigos para particulas
    private ArrayList<Enemigo> enemigos;
    
    // EVENTOS
    /*Se utilizan eventos para poder dar instrucciones al usuario al inicio
    de determinado mapa, determinar que hacer cuando se termina el juego, 
    lo que se debe hacer cuando el jugador muere. Aqui tambien se inicializan
    los efectos de trancision entre cada nivel y evento.*/
    
    //La booleana bloquear control bloquea el control del usuario sobre el pers.
    private boolean boolBloquearControl = false;
    private int iEventoConta;
    private ArrayList<Rectangle> arrTransiciones;
    private boolean boolEventoStart;
    private boolean boolEventoFinish;
    private boolean boolEventoDead;
    
    public Boss1State(GameStateManager gsm) {
        super(gsm);
        init();
    }
    
    public void init() {
        //Inicializando el tile map, cargandolo, dandole posicion, etc.
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Resources/Mapas/tilesetf.png");
        tileMap.loadMap("/Resources/Mapas/niveljefef.txt");
        tileMap.setPosition(0, 0);
        tileMap.setScroll(0.07);
        
        //Estableciendo el background
        bg = new Background("/Resources/Fondos/BGjefe.png", 1);
        bg.setVector(0, 0);

        //Poniendo a Osmy en el mapa, dandole coordenadas
        osmy = new Osmy(tileMap);
        osmy.setPosition(400,400);
        
        //Inicializando arreglo de enemigos
        enemigos = new ArrayList<Enemigo>();
        
        //Prueba de MOSSETH
        eneMosseth = new Mosseth(tileMap, osmy, enemigos);
        eneMosseth.setPosition(1000, 520);
        
        //Inicializando el HUD con los datos del personaje principal
        hud = new HUD(osmy);
        
        //Inicializando el evento de inicio
        boolEventoStart = true;
        arrTransiciones = new ArrayList<Rectangle>();
        eventoStart();
    }

    @Override
    public void draw(Graphics2D g) {
        //Dibujando el background
        bg.draw(g);
        
        //dibujando tilemap
        tileMap.draw(g);
        
        
        
        //dibujando osmy
        osmy.draw(g);
        
        eneMosseth.draw(g);
        
        //dibujando enemigos
        for(int i = 0; i < enemigos.size(); i++) {
            enemigos.get(i).draw(g);
		}

        //dibujando el HUD
        hud.draw(g);
        //dibujando las transiciones
        g.setColor(Color.BLACK);
        for(int i = 0; i < arrTransiciones.size(); i++) {
            g.fill(arrTransiciones.get(i));
        }
    }

    @Override
    public void update() {
        
        // check if player dead event should start
	if(osmy.getHealth() == 0 || osmy.getY() > tileMap.getHeight()) {
            boolEventoDead = boolBloquearControl = true;
        }
        
        //Corriendo los eventos
        if(boolEventoStart) eventoStart();
        if(boolEventoDead) eventoDead();
       
        osmy.update();//Update al personaje principal
        System.out.println(eneMosseth.getHealth());
        
        //Actualizando la posicion del mapa dependiendo la del personaje Osmy
        tileMap.setPosition(GamePanel.WIDTH / 2 - osmy.getX(),
                GamePanel.HEIGHT / 2 - osmy.getY());
        //Actualizando los efectos especiales del mapa
        tileMap.update();
        tileMap.fixBounds();
        
        //Poner el background
        bg.setPosition(tileMap.getX(), tileMap.getY());
        
        //checar si se estan atacando enemigos
        osmy.checkAttack(enemigos);
        osmy.checkAttackBoss(eneMosseth);
        
        //haciendo update de los enemigos
        for (int i=0; i < enemigos.size(); i++) {
            Enemigo e = enemigos.get(i);
            e.update();
            if(e.isDead() || e.shouldRemove()) {
                enemigos.remove(i);
                i--;
            }
        }
        eneMosseth.update();
       
    }
    
    @Override
    public void keyPressed(int k) {
        if(k == KeyEvent.VK_A && !osmy.boolEarth && !osmy.boolFire) {
            osmy.setLeft(true);
        }
        if(k == KeyEvent.VK_D && !osmy.boolEarth && !osmy.boolFire ) {
            osmy.setRight(true);
        }
        if(k == KeyEvent.VK_W) {
            osmy.setUp(true);
        }
        if(k == KeyEvent.VK_S) {
            osmy.setDown(true);
        }
        if(k == KeyEvent.VK_SPACE && !osmy.boolEarth && !osmy.boolFire) {
            osmy.setJumping(true);
        }
        if(k == KeyEvent.VK_F) {
            osmy.setFiring();
        }
    }

    @Override
    public void keyReleased(int k) {
        //Checar el pausado
        if(k == KeyEvent.VK_P && gsm.boolPausado) {
            gsm.setPausado(false);
        }
        else if (k == KeyEvent.VK_P && !gsm.boolPausado){
            gsm.setPausado(true);
        }
        
        //Poniendo el falso las variables de movimiento cuando no se presionan.
        if(k == KeyEvent.VK_A) {
            osmy.setLeft(false);
        }
        if(k == KeyEvent.VK_D) {
            osmy.setRight(false);
        }
        if(k == KeyEvent.VK_W) {
            osmy.setUp(false);
        }
        if(k == KeyEvent.VK_S) {
            osmy.setDown(false);
        }
        if(k == KeyEvent.VK_SPACE) {
            osmy.setJumping(false);
        }
        /*Los siguientes Ifs se encargan de las transformaciones
        se checa si la energia del jugador es necesario para la transformacion
        y si previamente la transformacion a la que se desea cambiar esta
        en falso. De esta manera se evita que el jugador pueda estar transform-
        andose de manera ilimitada en su mismo estado.*/
        //Convertirse a modo fuego
        if(k == KeyEvent.VK_RIGHT) {
            if(!osmy.boolFire && osmy.iEnergia > osmy.iTransCost){
                osmy.iEnergia -= osmy.iTransCost;
                osmy.setFireTrans();
                osmy.setFire();
                osmy.setEarthFalse();
                osmy.setWaterFalse();
            }
        }
        //convertirse en tierra
        if(k == KeyEvent.VK_DOWN) {
            if(!osmy.boolEarth && osmy.iEnergia > osmy.iTransCost) {
                osmy.iEnergia -= osmy.iTransCost;
                osmy.setEarthTrans();
                osmy.setEarth();
                osmy.setFireFalse();
                osmy.setWaterFalse();
            }
        }
        //Convertirse en agua
        if(k == KeyEvent.VK_LEFT) {
            if(!osmy.boolWater && osmy.iEnergia > osmy.iTransCost) {
                osmy.iEnergia -= osmy.iTransCost;
                osmy.setWaterTrans();
                osmy.setWater();
                osmy.setEarthFalse();
                osmy.setFireFalse();
            }
        }
        //Convertirse a modo normal
        if(k == KeyEvent.VK_UP) {
            if(osmy.boolEarth || osmy.boolFire || osmy.boolWater &&
                    osmy.iEnergia > osmy.iTransCost) {
                osmy.iEnergia -= osmy.iTransCost;
                osmy.setNormalTrans();
                osmy.setWaterFalse();
                osmy.setEarthFalse();
                osmy.setFireFalse();
            }
        }
    }
    
    /*
        MANEJO DE LOS EVENTOS
    */

    //EVENTO RESET
    private void reset() {
        osmy.reset();
        osmy.setPosition(100, 400);
        boolBloquearControl = true;
        iEventoConta = 0;
        tileMap.setVibrar(false, 0);
        boolEventoStart = true;
        eventoStart();
    }
    
    private void eventoStart() {
        iEventoConta++;
        if(iEventoConta == 1) {
            arrTransiciones.clear();
            arrTransiciones.add(new Rectangle(0, 0, GamePanel.WIDTH,
                    GamePanel.HEIGHT / 2));
            arrTransiciones.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, 
                    GamePanel.HEIGHT));
            arrTransiciones.add(new Rectangle(0, GamePanel.HEIGHT / 2, 
                    GamePanel.WIDTH, GamePanel.HEIGHT / 2));
            arrTransiciones.add(new Rectangle(GamePanel.WIDTH / 2, 0, 
                    GamePanel.WIDTH / 2, GamePanel.HEIGHT));
        }
        if(iEventoConta > 1 && iEventoConta < 60) {
            arrTransiciones.get(0).height -= 20;
            arrTransiciones.get(1).width -= 32;   
            arrTransiciones.get(2).y += 20;   
            arrTransiciones.get(3).x += 32;
        }
        //if(iEventoConta == 30) title.begin();
        if(iEventoConta == 60) {
            boolEventoStart = boolBloquearControl = false;
            iEventoConta = 0;
            //subtitle.begin();
            arrTransiciones.clear();
        }
    }
    
    private void eventoDead() {
        iEventoConta++;
        if(iEventoConta == 1) {
            osmy.setDead();
            osmy.stop();
        }
        if(iEventoConta == 60) {
            arrTransiciones.clear();
            arrTransiciones.add(new Rectangle(-1280,
                    0, GamePanel.WIDTH, GamePanel.HEIGHT));
            

        }
        else if(iEventoConta > 60) {
            //Rectangulo 0
            arrTransiciones.get(0).x += 60;

        }
        if(iEventoConta >= 80) {
            //Aqui hace falta la condiciones de las vidas
            boolEventoDead = boolBloquearControl = false;
            iEventoConta = 0;
            reset();
        }
    }
}

    

    
