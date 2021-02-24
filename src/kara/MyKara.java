package kara;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import kara.gamegrid.Kara;
import kara.gamegrid.KaraWorld;


/**
 * MyKara is a subclass of Kara. Therefore, it inherits all methods of Kara.
 * MyKara ist eine Unterklasse von Kara. Sie erbt damit alle Methoden der Klasse Kara.
 * 
 * Actions:     move(), turnLeft(), turnRight(), putLeaf(), removeLeaf()
 * Sensors:     onLeaf(), treeFront(), treeLeft(), treeRight(), mushroomFront()
 */
public class MyKara extends Kara {
	private final static int totalFrames = 6572;
	private final static int width  = 48;
	private final static int height = 36;
	
	private static byte[] fileArray;
	private static boolean[] pixelArray = new boolean[totalFrames * width * height];
	
	public void act() {
		
		for (int frame = 0; frame < totalFrames; ++frame) {
			for (int iy = 0; iy < height; ++iy) {
				for (int ix = 0; ix < width; ++ix) {
					if (pixelArray[frame * width * height + width * iy + ix])
						this.black();
					else
						this.white();
					this.move();
				}
				this.nextLine();
			}
		}
	}
	
	private void black() {
		if (!this.onLeaf())
			this.putLeaf();
	}
	
	private void white() {
		if (this.onLeaf())
			this.removeLeaf();
	}

	private void nextLine() {
		this.turnRight();
		this.move();
		this.turnLeft();
	}
	
	public static void main(String[] args) {
		Path file = Paths.get("./48x36.bin");
		try {
			fileArray = Files.readAllBytes(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < totalFrames * width * height / 8; ++i) {
			pixelArray[8*i + 0] = (fileArray[i] & 0b10000000) > 0;
			pixelArray[8*i + 1] = (fileArray[i] & 0b01000000) > 0;
			pixelArray[8*i + 2] = (fileArray[i] & 0b00100000) > 0;
			pixelArray[8*i + 3] = (fileArray[i] & 0b00010000) > 0;
			pixelArray[8*i + 4] = (fileArray[i] & 0b00001000) > 0;
			pixelArray[8*i + 5] = (fileArray[i] & 0b00000100) > 0;
			pixelArray[8*i + 6] = (fileArray[i] & 0b00000010) > 0;
			pixelArray[8*i + 7] = (fileArray[i] & 0b00000001) > 0;
		}
		
		KaraWorld world = new KaraWorld("WorldSetup.txt", MyKara.class);
		world.show();
	}
}
