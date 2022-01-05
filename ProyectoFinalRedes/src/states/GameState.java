package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import easterEgg.SnakeGame;
import gameObjects.Constants;
import gameObjects.Mensaje;
import gameObjects.Meteor;
import gameObjects.MovingObject;
import gameObjects.Player;
import gameObjects.Size;
import graphics.Animacion;
import graphics.Assets;
import graphics.Sonido;
import graphics.Text;
import math.Vector2D;

public class GameState extends State{
	
	public static final Vector2D PLAYER_START_POSITION = new Vector2D(Constants.ANCHO/2 - Assets.player.getWidth()/2,
			Constants.ALTURA/2 - Assets.player.getHeight()/2);
	
	private Player player;
	private ArrayList <MovingObject> movingObjects = new ArrayList<MovingObject>();
	private ArrayList <Animacion> explosion = new ArrayList<Animacion>();
	private ArrayList<Mensaje> messages = new ArrayList<Mensaje>();
	//private Text text
	private int score = 0;
	public int vidas;
	
	private int meteors;
	
	public int hordas;
	
	private Sonido bgSound;
	
	public GameState() {
		player = new Player(PLAYER_START_POSITION,new Vector2D(),5 ,Assets.player,this);
		movingObjects.add(player);
		vidas = Constants.VIDAS;
		hordas=1;
		meteors = 1;
		startWave();
		bgSound = new Sonido(Assets.backgroundMusic);
		bgSound.changeVolume(-5.0f);
		bgSound.loop();
		
	}
	
	public GameState(int score, int vidas, int hordas) {
		player = new Player(PLAYER_START_POSITION,new Vector2D(),5 ,Assets.player,this);
		movingObjects.add(player);
		this.score=score;
		this.vidas=vidas;
		this.hordas=hordas;
		meteors = hordas-1;
		startWave();
		bgSound = new Sonido(Assets.backgroundMusic);
		bgSound.changeVolume(-5.0f);
		bgSound.loop();
		
	}
	
	public void addScore(int val,Vector2D pos) {
		score+=val;
		messages.add(new Mensaje(pos, true, "+"+val+" prro", Color.WHITE, false, Assets.fontm, this));
	}
	
	public void mitosis(Meteor metiorito) {
		Size size = metiorito.getSize();
		
		BufferedImage[] textures = size.textures;
		
		Size newSize = null;
		
		switch(size) {
		case BIG:
			newSize=Size.MED;
			break;
		case MED:
			newSize=Size.SMALL;
			break;
		case SMALL:
			newSize=Size.TINY;
		default:
			return;
		}
		
		for(int i = 0;i<size.cantidad;i++) {
			movingObjects.add(new Meteor(
					metiorito.getPos(),
					new Vector2D(0,1).setDirection(Math.random()*Math.PI*2),
					Constants.METEOR_VEL*Math.random()+1,
					textures[(int)(Math.random()*textures.length)],
					this,
					newSize
					));
		}
	}
	
	private void startWave() {
		messages.add(new Mensaje(new Vector2D(Constants.ANCHO/2, Constants.ALTURA/2), false,
				"WAVE "+hordas, Color.WHITE, true, Assets.font, this));
		double x,y;
		for(int i = 0; i<meteors;i++) {
			x= i%2==0 ? Math.random()*Constants.ANCHO : 0;
			y= i%2==0 ? 0 : Math.random()*Constants.ALTURA;
			
			BufferedImage texture = Assets.bigs[(int)(Math.random()*Assets.bigs.length)];
			movingObjects.add(new Meteor(
					new Vector2D(x,y),
					new Vector2D(0,1).setDirection(Math.random()*Math.PI*2),
					Constants.METEOR_VEL*Math.random()+1,
					texture,
					this,
					Size.BIG
					));
			
		}
		meteors++;
		
	}
	
	public void playExplosion(Vector2D pos) {
		explosion.add(new Animacion(
				Assets.exp,
				50,
				pos.resta(new Vector2D(Assets.exp[0].getWidth()/2,Assets.exp[0].getHeight()/2))
				));
	}
	
	public void update() {
		//player.update();
		for(int i = 0; i < movingObjects.size(); i++)
			movingObjects.get(i).update();
		
		for(int i = 0; i < explosion.size(); i++){
			Animacion anim = explosion.get(i);
			anim.update();
			if(!anim.isRunning()){
				explosion.remove(i);
			}
			
		}
		
		for(int i = 0; i < movingObjects.size(); i++)
			if(movingObjects.get(i) instanceof Meteor)
				return;
		
		hordas++;
		startWave();
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d= (Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		for(int i = 0; i < messages.size(); i++)
			messages.get(i).draw(g2d);
		
		for(int i=0;i<movingObjects.size();i++)
			movingObjects.get(i).draw(g);
		
		for(int i=0;i<explosion.size();i++) {
			Animacion anim = explosion.get(i);
			g2d.drawImage(anim.getCurrentFrame(), (int)anim.getPos().getX(), (int)anim.getPos().getY(),null);
		}
		
		drawScore(g);
		drawLives(g);
		Text.drawText(g, new Vector2D (Constants.ANCHO/2-70,50), "WAVE "+hordas, false, Color.white, Assets.font);
	}

	public void drawScore(Graphics g) {
		Vector2D pos = new Vector2D(Constants.ANCHO-100,25);
		
		String puntos = Integer.toString(score);
		
		for(int i = 0; i<puntos.length();i++) {
			g.drawImage(Assets.numeros[Integer.parseInt(puntos.substring(i,i+1))], (int)pos.getX(), (int)pos.getY(), null);
			pos.setX(pos.getX()+20);
		}
		
	}
	
	private void drawLives(Graphics g){
		if(vidas < 0)
			return;
		Vector2D livePosition = new Vector2D(100, 25);
		
		g.drawImage(Assets.vida, (int)livePosition.getX(), (int)livePosition.getY()-10, null);
		
		g.drawImage(Assets.numeros[10], (int)livePosition.getX() + 60,
				(int)livePosition.getY() + 5, null);
		
		String livesToString = Integer.toString(vidas);
		
		Vector2D pos = new Vector2D(livePosition.getX(), livePosition.getY());
		
		for(int i = 0; i < livesToString.length(); i ++)
		{
			int number = Integer.parseInt(livesToString.substring(i, i+1));
			
			if(number <= 0)
				break;
			g.drawImage(Assets.numeros[number],
					(int)pos.getX() + 80, (int)pos.getY() + 5, null);
			pos.setX(pos.getX() + 20);
		}
		
	}
	public ArrayList<Mensaje> getMessages() {
		return messages;
	}
	
	public void mainusLaif() {
		vidas --;
		if(vidas<=0) {
			//Assets.init();
	    	//State.changeState(new MenuState());
			State.changeState(new SnakeGame(score,hordas,0));
			//SnakeGame obj = new SnakeGame();
		}
		}
	
	public ArrayList<MovingObject> getMovingObjects() {
		return movingObjects;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	
}
