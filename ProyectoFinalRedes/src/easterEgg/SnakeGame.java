package easterEgg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import gameObjects.Constants;
import states.GameState;
import states.MenuState;
import states.State;


public class SnakeGame extends State {
    
    JFrame Window;
    JPanel PanelGame;
    JLabel Background,Score,GameOver;
    
    
    // Snake Atributos
    ArrayList<JLabel> Snake;
    int x,y,des = 20;
    Timer time;
    int ban = 0;
    Rectangle serp,comi;
    int loose;
    int contador;
    private int prevScore;
    private int prevHord;
    public int regresaJ;
    
    //Comida
    JLabel comida;
    //pos comida.
    int cx;
    int cy;
    
    public SnakeGame(int prevScore,int prevHord,int  regresaJ){
        this.prevScore=prevScore;
        this.regresaJ=regresaJ;
        
        // ----- Window ----- 
        
        //Titulo de la Ventana
        Window = new JFrame("Snake Game");
        //Tamaño del Canvas
        Window.setSize(600,600);
        //Ventana en el centro de la resolución de la pc.
        Window.setLocationRelativeTo(null);
        //Componenetes para la ventana.
        Window.setLayout(null);
        // Boton x para cerrar el juego.
        Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //funcion para no maximizar la ventana.
        Window.setResizable(false);
        
        // ----   PANEL  ---- 
        
        PanelGame = new JPanel();
        //Tamaño + posicion de la ventana.
        PanelGame.setSize(Window.getSize());
        //Componentes para el Panel.
        PanelGame.setLayout(null);
        //Panel Visible
        PanelGame.setVisible(true);
        //Color del panel.
        //PanelGame.setBackground(Color.black);
        
        
        // Background
        Background = new JLabel();
        //Tamaño + posicion del Panel
        Background.setSize(PanelGame.getSize());
        //Fondo cargado
        Background.setIcon(new ImageIcon("res/Snake/Assets/larry.png"));
        // Fondo Visible
        Background.setVisible(true);
        //Agregar fondo al Panel
        PanelGame.add(Background,0);
        

        // -----  Snake   ------
        Snake = new ArrayList<JLabel>();
        //Variable para incluir imagenes al Snake
        JLabel aux = new JLabel();
        //Tamaño de la Cara Snake
        aux.setLocation(290,290);
        aux.setSize(20,20);
        aux.setIcon(new ImageIcon("res/Snake/Assets/Face_r.png"));
        aux.setVisible(true);
        //Agregar las propiedades a Snake
        Snake.add(aux);
        //Agregamos Snake al panel
        PanelGame.add(Snake.get(0),0);
        
        //Agregar el panel a al ventana.
        Window.add(PanelGame);
        
        
        // -----   Comida   ------
        comida = new JLabel();
        comida.setSize(20,20);
        comida.setIcon(new ImageIcon("res/Snake/Assets/Apple.png"));
        //Posicion de la comida en el Panel
        Random r = new Random();
        cx = r.nextInt(540);
        cy = r.nextInt(540);
        comida.setLocation(cx,cy);
        comida.setVisible(true);
        PanelGame.add(comida,0);
        
        //Colisiones de Imagenes. mediante Rectangle
        serp = new Rectangle(Snake.get(0).getBounds());
        comi = new Rectangle(comida.getBounds());
        
        
        // ----- SCORE ----- 
        
        Score = new JLabel("Puntuacion: "+ contador);
        Score.setBounds(10, 20, 200, 20);
        Score.setVisible(true);
        Score.setForeground(Color.red);
        PanelGame.add(Score,0);
        
        
        // ---- Label ----
        GameOver = new JLabel("GAME OVER");
        GameOver.setBounds(200, 200, 300, 300);
        GameOver.setForeground(Color.MAGENTA);
        GameOver.setVisible(false);
        PanelGame.add(GameOver,0);
        
        //Movimiento de Snake, GAME OVER y Aumento de Tamaño.
        time = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comi.setBounds(comida.getBounds());
                serp.setBounds(Snake.get(0).getBounds());
                
                //GAME OVER;
                if(Snake.get(0).getX() > 540){
                    loose = 1;
                    GameOver.setVisible(true);
                    //State.changeState(new MenuState());
                }
                
                if(Snake.get(0).getX() < 10){
                    loose = 1;
                    GameOver.setVisible(true);
                    //State.changeState(new MenuState());
                }
                
                if(Snake.get(0).getY() > 540){
                    loose = 1;
                    GameOver.setVisible(true);
                    //State.changeState(new MenuState());
                }
                
                if(Snake.get(0).getY() < 10){
                    loose = 1;
                    GameOver.setVisible(true);
                    //State.changeState(new MenuState());
                }
                
                if(loose == 1 && SnakeGame.this.regresaJ != 1){
                    GameOver.setVisible(true);
                    time.stop();
                    State.changeState(new MenuState());
                    Window.hide();
                }
                
                
                if (comi.intersects(serp)){
                    //Cuerpo + comida
                    cx = r.nextInt(540);
                    cy = r.nextInt(540);
                    comida.setLocation(cx,cy);
                    comida.repaint();
                    System.out.println("Comi");
                    JLabel aux = new JLabel();
                    aux.setLocation(200,200);
                    aux.setSize(20,20);
                    aux.setIcon(new ImageIcon("res/Snake/Assets/Body.png"));
                    aux.setVisible(true);
                    //Agregar las propiedades a Snake
                    Snake.add(aux);
                    PanelGame.add(Snake.get(Snake.size()-1),0);
                    System.out.println(""+Snake.size());
                    contador+= 5;
                    Score.setText("Puntuacion: "+ contador);
                    
                    	
                    Score.repaint();
                    if(contador==Constants.PTOREVIVE) {
                    	State.changeState(new GameState(prevScore,Constants.VIDAS_AFTERLIFE,prevHord-1));
                    	Window.hide();
                    	SnakeGame.this.regresaJ=1;
                    	time.stop();
                    }
                }   
                
                for(int i = Snake.size()-1; i>0; i--){
                    Snake.get(i).setLocation(Snake.get(i-1).getLocation());
                    Snake.get(i).repaint();
                }
                Snake.get(0).setLocation(Snake.get(0).getX()+x,Snake.get(0).getY()+y);
            }
        });
        //Asignacion de Teclas.
        Window.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            //Metodo para asignar movimiento a teclas del teclado. 
            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                //Para arriba 20 pixeles.
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    System.out.println("Arriba");
                    if(Snake.get(0).getY() > 0){
                        y = -des;
                        x = 0;
                        Snake.get(0).setIcon(new ImageIcon("res/Snake/Assets/Face_Up.png"));
                    }
                    if (ban == 0){
                        time.start();
                        ban = 1;
                    }
                }
                 if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    System.out.println("Abajo");
                    if(Snake.get(0).getY() < 600){
                        y = des;
                        x = 0;
                        Snake.get(0).setIcon(new ImageIcon("res/Snake/Assets/Face_down.png"));
                    }
                    if (ban == 0){
                        time.start();
                        ban = 1;
                    }
                }
                  if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    System.out.println("Izquierda");
                    if(Snake.get(0).getY() > 0){
                        y = 0;
                        x = -des;
                        Snake.get(0).setIcon(new ImageIcon("res/Snake/Assets/Face_L.png"));
                    }
                    if (ban == 0){
                        time.start();
                        ban = 1;
                    }
                }
                  if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    System.out.println("Derecha");
                    if(Snake.get(0).getY() < 600){
                        y = 0;
                        x = des;
                        Snake.get(0).setIcon(new ImageIcon("res/Snake/Assets/Face_r.png"));
                    }
                    
                    if (ban == 0){
                        time.start();
                        ban = 1;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        Window.setVisible(true);
    }

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
