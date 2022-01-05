/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

/**
 *
 * @author home_
 */

public class Assets {
	
	public static BufferedImage speed; 
	public static BufferedImage rashoR;
	public static BufferedImage rashoB;
	public static BufferedImage rashoG;
	public static BufferedImage player;
	
	//metioritos
	public static BufferedImage[] bigs = new BufferedImage[4];
	public static BufferedImage[] meds = new BufferedImage[2];
	public static BufferedImage[] smalls = new BufferedImage[2];
	public static BufferedImage[] minis = new BufferedImage[2];
	public static BufferedImage[] exp = new BufferedImage[9];
	
	public static BufferedImage[] numeros = new BufferedImage[11];
	
	public static BufferedImage vida;
	//public static BufferedImage bgS;
	
	public static Font font;
	
	public static Font fontm;
	public static Clip backgroundMusic, explosion, playerLoose, playerShoot, ufoShoot;
	
	public static BufferedImage blueBtn;
	public static BufferedImage greyBtn;
	
    public static void init(){
//        player = LoadResources.ImageLoader("/ships/playerShip1_blue.png");
    	//bgS = LoadResources.ImageLoader("/Snake/Assets/larry.png");
        player = LoadResources.ImageLoader("/ships/LarryNano.png");
        speed = LoadResources.ImageLoader("/efects/flamitasProNano.png");
        rashoR = LoadResources.ImageLoader("/rashosLasers/laserRed01.png");
        rashoB = LoadResources.ImageLoader("/rashosLasers/laserBlue12.png");
        rashoG = LoadResources.ImageLoader("/rashosLasers/laserGreen02.png");
        
        font = LoadResources.loadFont("/Fonts/font.ttf",42);
        fontm = LoadResources.loadFont("/Fonts/font.ttf",30);
        
        vida = LoadResources.ImageLoader("/vida/vidaLarry.png");
        
        greyBtn = LoadResources.ImageLoader("/ui/blue_button13.png");
		blueBtn = LoadResources.ImageLoader("/ui/blue_button02.png");
        
        for(int i = 0; i<bigs.length;i++)
        	bigs[i]=LoadResources.ImageLoader("/metioritos/big"+(i+1)+".png");
        
        for(int i = 0; i<meds.length;i++)
        	meds[i]=LoadResources.ImageLoader("/metioritos/med"+(i+1)+".png");
        
        for(int i = 0; i<smalls.length;i++)
        	smalls[i]=LoadResources.ImageLoader("/metioritos/small"+(i+1)+".png");
        
        for(int i = 0; i<minis.length;i++)
        	minis[i]=LoadResources.ImageLoader("/metioritos/tiny"+(i+1)+".png");
        
        for(int i = 0; i<exp.length;i++)
        	exp[i]=LoadResources.ImageLoader("/kabum/"+i+".png");
        
        for(int i = 0; i<numeros.length;i++)
        	numeros[i]=LoadResources.ImageLoader("/numeros/numeral"+i+".png");
        
        backgroundMusic = LoadResources.loadSound("/cancioncitas/bgSound.wav");
		explosion = LoadResources.loadSound("/cancioncitas/pepSound1.wav");
		playerShoot = LoadResources.loadSound("/cancioncitas/laser4.wav");
		playerLoose = LoadResources.loadSound("/cancioncitas/phaseJump1.wav");
		
    }
}
