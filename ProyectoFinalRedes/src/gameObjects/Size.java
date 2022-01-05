package gameObjects;

import java.awt.image.BufferedImage;

import graphics.Assets;

public enum Size {
	
	BIG(4,Assets.meds),MED(2,Assets.smalls),SMALL(2,Assets.minis),TINY(0,null);
	
	public int cantidad;
	
	public BufferedImage[] textures;
	
	private Size (int cantidad,BufferedImage[] textures) {
		this.cantidad=cantidad;
		this.textures=textures;
	}

}
