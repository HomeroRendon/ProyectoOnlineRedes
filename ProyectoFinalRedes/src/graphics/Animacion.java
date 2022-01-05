package graphics;

import java.awt.image.BufferedImage;

import math.Vector2D;

public class Animacion {
	private BufferedImage[] frame;
	private int latencia;
	private int index;
	private Vector2D pos;
	private long time, lastTime;
	private boolean running;
	
	public Animacion (BufferedImage[] frames, int latencia, Vector2D pos) {
		this.frame=frames;
		this.latencia=latencia;
		this.pos=pos; 
		index=0;
		running=true;
		time=0;
		lastTime = System.currentTimeMillis();
	}
	
	public void update() {
		time+=System.currentTimeMillis()-lastTime;
		lastTime = System.currentTimeMillis();
		if(time > latencia) {
			time=0;
			index++;
			if(index>=frame.length) {
				running=false;
			}
		}
		
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public Vector2D getPos() {
		return pos;
	}
	
	public BufferedImage getCurrentFrame() {
		return frame[index];
	}
	
}
