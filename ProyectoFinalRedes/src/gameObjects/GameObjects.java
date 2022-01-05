package gameObjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import math.Vector2D;

public abstract class GameObjects {
	protected BufferedImage textura;
	protected Vector2D pos;
	
	public GameObjects(Vector2D pos, BufferedImage textura){
		this.pos=pos;
		this.textura=textura;
	}
	
	public abstract void update();
	
	public abstract void  draw (Graphics g);

	public Vector2D getPos() {
		return pos;
	}

	public void setPos(Vector2D pos) {
		this.pos = pos;
	}
	
	
}
