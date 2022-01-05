package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import gameObjects.Constants;
import math.Vector2D;

public class Text {

public static void drawText(Graphics g,Vector2D position,String string, boolean center, Color color,Font font) {
	g.setColor(color);
	g.setFont(font);
	
	Vector2D pos = new Vector2D(position.getX(),position.getY());
	
	if(center) {
		FontMetrics metrics = g.getFontMetrics();
		pos.setX(pos.getX()-metrics.stringWidth(string)/2);
		pos.setY(pos.getY()-metrics.getHeight()/2);  
	}
	
	g.drawString(string, (int)pos.getX(), (int)pos.getY());
	
}
}
