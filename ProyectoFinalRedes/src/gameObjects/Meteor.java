package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import math.Vector2D;
import states.GameState;

public class Meteor extends MovingObject{
	
	private Size size;

	public Meteor(Vector2D pos, Vector2D vel, double maxVel, BufferedImage textura, GameState gameState, Size size) {
		super(pos, vel, maxVel, textura, gameState);
		this.size=size;
		this.vel=vel.scale(maxVel);
	}
	
	@Override
	public void destruccion() {
		gameState.mitosis(this);
		gameState.addScore(Constants.METEOR_SCORE,pos);
		super.destruccion();
	}
	
	@Override
	public void update() {
		pos=pos.add(vel);
		if(pos.getX()>Constants.ANCHO)
			pos.setX(-anchoT);
		if(pos.getY()>Constants.ALTURA)
			pos.setY(-alturaT);
		
		if(pos.getX()<-anchoT)
			pos.setX(Constants.ANCHO);
		if(pos.getY()<-alturaT)
			pos.setY (Constants.ALTURA);
		
		angle += Constants.DELTAANGLE/2;
		
		
		
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		at = AffineTransform.getTranslateInstance(pos.getX(), pos.getY());
		at.rotate(angle,anchoT/2,alturaT/2);
		g2d.drawImage(textura, at, null);
		
	}
	
	public Size getSize() {
		return size;
	}
}
