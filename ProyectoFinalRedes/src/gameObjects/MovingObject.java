package gameObjects;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.Assets;
import graphics.Sonido;
import math.Vector2D;
import states.GameState;

public class MovingObject extends GameObjects{
	
	protected Vector2D vel;
	protected AffineTransform at;
	protected double angle;
	protected double maxVel;
	protected int anchoT;
	protected int alturaT;
	protected GameState gameState;
	
	private Sonido explosion;

	public MovingObject(Vector2D pos, Vector2D vel, double maxVel, BufferedImage textura, GameState gameState) {
		super(pos, textura);
		this.vel = vel;
		angle=0;
		this.maxVel=maxVel;
		this.gameState=gameState;
		anchoT=textura.getWidth();
		alturaT=textura.getHeight();
		explosion = new Sonido(Assets.explosion);
		// TODO Auto-generated constructor stub
	}
	
	protected void chocasionDetection() {
		ArrayList<MovingObject> movingObjects = gameState.getMovingObjects();
		
		for(int i = 0; i<movingObjects.size();i++) {
			MovingObject m = movingObjects.get(i);
			if(m.equals(this))
				continue;
			
			double distance = m.getCenter().resta(getCenter()).getMagnitude();
			if (distance < m.anchoT/2+anchoT/2 && movingObjects.contains(this))
				colision(m,this);
				
		}
	}
	
	private void colision(MovingObject a,MovingObject b) {
		
		if(a instanceof Player && ((Player)a).isSpawn()) {
			return;
		}
		if(b instanceof Player && ((Player)b).isSpawn()) {
			return;
		}
		
		if(!(a instanceof Meteor && b instanceof Meteor))
			gameState.playExplosion(getCenter());
			a.destruccion();
			b.destruccion();
	}
	
	protected void destruccion() {
		gameState.getMovingObjects().remove(this);
		if(!(this instanceof RashoLaser))
			explosion.play();
	}
	
	protected Vector2D getCenter() {
		 return new Vector2D(pos.getX()+anchoT/2,pos.getY()+alturaT/2);
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
