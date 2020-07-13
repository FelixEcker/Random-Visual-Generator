package de.felixeckert.jtggi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.List;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import de.felixeckert.terraingen.Generator;

public class Render {
	boolean printCoordinates;
	Main main;
	
	int x = 0;
	int y = 0;
	
	int width = 128;
	int height = 128;
	
	int tileWidth = 16;
	int tileHeight = 16;
	
	List tiles = new List();
	HashMap<Integer, Color> colorMap;

	public Render(Main main, boolean printCoordinates) {
		System.out.println("Initialising Renderer and Terraingen...");
		this.main = main;
		this.printCoordinates = printCoordinates;
		
		this.width = (int) main.getMapSize().getWidth();
		this.height = (int) main.getMapSize().getHeight();
		
		this.tileWidth = (int) main.getTileSize().getWidth();
		this.tileHeight = (int) main.getTileSize().getHeight();
		
		System.out.println("Generating terrain...");
		tiles = new Generator().generateTypeA(width, height, false);
		this.x = 0;
		this.y = 0;
		this.colorMap = new HashMap<>();
		this.colorMap.put(0, Color.green);
		this.colorMap.put(1, Color.magenta);
		this.colorMap.put(2, Color.yellow);
		this.colorMap.put(3, Color.orange);
		this.colorMap.put(4, Color.cyan);
		System.out.println("Tile Count: "+tiles.getItemCount());
		System.out.println("Starting render!");
	}
	
	public void render(Graphics g) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int currentTileNum = i+j;
				/*if (i == 0) {
					currentTileNum = i + (j+16);
				} else if (j == 0) {
					currentTileNum = (i+16) + j;
				} else if (i == 0 && j == 0) {
					currentTileNum = i + j;
				} else {
					currentTileNum = (i+16) + (j+16); 
				}*/
				if (this.tiles.getItem(currentTileNum).matches("#")) {
					g.setColor(Color.GRAY);
				} else if (this.tiles.getItem(currentTileNum).matches("~")) {
					g.setColor(Color.BLUE);
				} else {
					g.setColor(colorMap.get(ThreadLocalRandom.current().nextInt(5)));
				}
				
				g.fillRect(x, y, tileWidth, tileHeight);

				if (printCoordinates)
					System.out.println("X: "+x+" Y: "+y);
				
				g.setFont(new Font("Trebuchet MS", Font.ITALIC, 20));
				g.clearRect(1, 1, 
						(int) g.getFontMetrics().getStringBounds("X: "+x+" Y: "+y, g).getWidth(),
						g.getFontMetrics().getHeight()+5);
				g.setColor(Color.yellow);
				g.drawString("X: "+x+" Y: "+y, 1, 1+g.getFontMetrics().getHeight());
				
				if (x < tileWidth*width) {
					this.x += tileWidth;
				} else {
					this.x = 0;
					this.y += tileHeight;
				}
				
				if (y > tileHeight*height) {
					this.y = 0;
				}
			}
		}
	}
}
