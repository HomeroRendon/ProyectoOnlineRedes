/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import gameObjects.Constants;
import graphics.Assets;
import input.KeyBoard;
import input.MouseInput;
import states.GameState;
import states.MenuState;
import states.State;
/**
 *
 * @author home_
 */
public class ProyectoFinalPOO extends JFrame implements Runnable{
    /**
     * @param args the command line arguments
     */
    
    private Canvas canvas;
    private Thread thread;
    
    private boolean running = false;
    private BufferStrategy bs;
    private Graphics g;
    
    //private final int FPS = 60;
    private int AVERAGEFPS = 60;
    private double TARGETTIME = 1000000000/AVERAGEFPS;
    private double delta = 0;
    
    
    private GameState gameState;
    private KeyBoard keyBoard;
    private MouseInput mouseInput;
    
    public ProyectoFinalPOO(){
        setTitle("Larry La Langosta Intergaláctica");
        setSize(Constants.ANCHO,Constants.ALTURA);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        
        canvas = new Canvas();
        keyBoard = new KeyBoard();
        mouseInput = new MouseInput();
        
        canvas.setPreferredSize(new Dimension(Constants.ANCHO,Constants.ALTURA));
        canvas.setMinimumSize(new Dimension(Constants.ANCHO,Constants.ALTURA));
        canvas.setMaximumSize(new Dimension(Constants.ANCHO,Constants.ALTURA));
        canvas.setFocusable(true);
        
        
        
        add(canvas);
        canvas.addKeyListener(keyBoard);
        canvas.addMouseListener(mouseInput);
		canvas.addMouseMotionListener(mouseInput);
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        new ProyectoFinalPOO().start();
    }

    private void update(){
    	keyBoard.update();
    	State.getCurrentState().update();
    }
    
    private void draw(){
        bs = canvas.getBufferStrategy();
        
        if(bs==null){
            canvas.createBufferStrategy(3);
            return;
        }
        g=bs.getDrawGraphics();
        
        g.setColor(Color.black);
        g.fillRect(0, 0, Constants.ANCHO, Constants.ALTURA);
        //g.drawImage(Assets.player, 100, 100, null);
        
        State.getCurrentState().draw(g);
        //g.drawString(""+AVERAGEFPS, 10, 20);
        
        g.dispose();
        bs.show();
    }
    
    private void init() {
    	Assets.init();
    	State.changeState(new MenuState());
    }
    
    @Override
    public void run() {
        
        long now = 0;
        long lastTime = System.nanoTime();
        int frames = 0;
        long time = 0;
        
        init();
        
        while (running){
            now = System.nanoTime();
            delta += (now - lastTime)/TARGETTIME;
            time+=(now - lastTime);
            
            lastTime = now;
            
            
            if(delta>=1){
                update();
                draw();
                delta --;
                frames++;
                
            }
            if(time>=1000000000){
                AVERAGEFPS = frames;
                frames = 0;
                time=0;
            }
            
        }
        stop();
    }
    
    private void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
        
    }
    private void stop(){
        try {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            thread.join();
            running=false;
        } catch (InterruptedException ex) {
        	ex.printStackTrace();
        }
        
    }
    
}
