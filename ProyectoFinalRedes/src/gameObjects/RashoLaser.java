package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.Sonido;
import math.Vector2D;
import states.GameState;

public class RashoLaser extends MovingObject{
	private Sonido rasho;

	public RashoLaser(Vector2D pos, Vector2D vel, double maxVel,double angle, BufferedImage textura, GameState gameState) {
		super(pos, vel, maxVel, textura,gameState);
		rasho = new Sonido(Assets.playerShoot);
		this.angle=angle;
		this.vel=vel.scale(maxVel);
		//rasho.play();
	}
	
	public Vector2D getCenter() {
		 return new Vector2D(pos.getX()+anchoT/2,pos.getY()+anchoT/2);
	}
	
	@Override
	public void update() {
		pos=pos.add(vel);
		if(pos.getX()<0||pos.getX()>Constants.ANCHO||
				pos.getY()<0||pos.getY()>Constants.ALTURA) {
			destruccion();
		}
		chocasionDetection();
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		at = AffineTransform.getTranslateInstance(pos.getX(), pos.getY());
		at.rotate(angle);
		g2d.drawImage(textura, at, null);
	}
	
}
