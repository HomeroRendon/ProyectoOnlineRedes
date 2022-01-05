package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.Sonido;
import input.KeyBoard;
//import main.ProyectoFinalPOO;
import math.Vector2D;
import states.GameState;

public class Player extends MovingObject{
	
	private Vector2D heading;
	private Vector2D acel;
	private boolean acelerating = false;
	private Cronos fireRate;
	//private GameState gameState;
	private boolean aparicion,visible;
	//private long time,lastTime;
	
	private Cronos tiempoRespawn, flickerTime;
	
	private Sonido shoot, loose;

	public Player(Vector2D pos, Vector2D vel,double maxVel , BufferedImage textura,GameState gameState) {
		super(pos, vel,maxVel, textura, gameState);
		heading = new Vector2D(0,1);
		acel = new Vector2D();
		fireRate=new Cronos();
		tiempoRespawn=new Cronos();
		flickerTime = new Cronos();
		shoot = new Sonido(Assets.playerShoot);
		loose = new Sonido(Assets.playerLoose);
	}

	@Override
	public void update() {
		if (!tiempoRespawn.isRunning()) {
			aparicion=false;
			visible = true;
		}
		
		if(aparicion) {
			if(!flickerTime.isRunning()) {
				flickerTime.run(Constants.FLICKER_TIME);
				visible=!visible;
			}
		}
		
		if(KeyBoard.SHOOT && !fireRate.isRunning() && !aparicion) {
			gameState.getMovingObjects().add(0,new RashoLaser(
					getCenter().add(heading.scale(anchoT/2+40)),
					heading,
					Constants.LASER_VEL,
					angle,
					Assets.rashoR,
					gameState));
			fireRate.run(Constants.FIRERATE);
			shoot.play();
		}
		
		if(shoot.getFramePosition() > 11000) {
			shoot.stop();
		}
		
		if(KeyBoard.UP) {
			acel = heading.scale(Constants.ACC);
			acelerating = true; 
			
		}else {
			if(vel.getMagnitude()!=0)
				acel = vel.scale(-1).unit().scale(Constants.ACC/2);
			acelerating=false;
		}
		
		
		vel = vel.add(acel);
		vel = vel.limit(Constants.PLAYER_MAX_VEL);
		if (KeyBoard.RIGHT)
			angle += Constants.DELTAANGLE;
		if (KeyBoard.LEFT)
			angle -= Constants.DELTAANGLE;
		
		heading = heading.setDirection(angle - Math.PI/2);
		pos = pos.add(vel);
		if(pos.getX()>Constants.ANCHO)
			pos.setX(0);
		if(pos.getY()>Constants.ALTURA)
			pos.setY(0);
		
		if(pos.getX()<0)
			pos.setX(Constants.ANCHO);
		if(pos.getY()<0)
			pos.setY (Constants.ALTURA);
		
		fireRate.update();
		tiempoRespawn.update();
		flickerTime.update();
		chocasionDetection();
	}
	
	public void centrar() {
		angle=0;
		vel = new Vector2D();
		pos=new Vector2D(Constants.ANCHO/2-100,Constants.ALTURA/2-100);
	}
	
	public boolean isSpawn() {
		return aparicion;
	}
	
	@Override
	public void destruccion() {
		aparicion=true;
		tiempoRespawn.run(Constants.TIEMPO_RSPWN);
		centrar();
		loose.play();
		gameState.mainusLaif();
	}
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		//g.drawImage(textura, (int)pos.getX(), (int)pos.getY(), null);
		
		if(!visible)
			return;
		
		Graphics2D g2d = (Graphics2D)g;
		
		at = AffineTransform.getTranslateInstance(pos.getX(), pos.getY());
		at.rotate(angle, anchoT/2,alturaT/2);
		
		AffineTransform at1 = AffineTransform.getTranslateInstance(pos.getX()+20, pos.getY()+100);
		at1.rotate(angle,anchoT/2,alturaT/2-100);
		
		if(acelerating)
		g2d.drawImage(Assets.speed, at1, null);
		
		g2d.drawImage(textura, at, null);
	}
	
}

