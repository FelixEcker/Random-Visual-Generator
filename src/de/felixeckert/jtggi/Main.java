package de.felixeckert.jtggi;


import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.io.Console;
import java.io.IOException;

import javax.swing.JOptionPane;

import de.felixeckert.jtggi.graphics.HexagonGraphicsEngine;

public class Main implements Runnable {
	public static Thread t;
	private HexagonGraphicsEngine hge;
	private static Dimension mapSize = new Dimension();
	private static Dimension tileSize = new Dimension();
	
	public static void main(String[] args) {
		Console console = System.console();
        
		if (console == null) {
			JOptionPane.showMessageDialog(null, "This program Utilises a Terrain Generator written by me to generate\n"
					+ "a kindof noise map, with which it generates \"interesting\" visuals\n"
					+ "You can find the Terrain Generator here: https://github.com/FelixEcker/Java-Terrain-Generator", "Interesting Visuals using the Java Terrain Generator by Felix Eckert", JOptionPane.PLAIN_MESSAGE);
		}
		
		System.out.println("Interesting Visuals using the Java Terrain Generator by Felix Eckert");
		System.out.println("This program Utilises a Terrain Generator written by me to generate");
		System.out.println("a kindof noise map, with which it generates \"interesting\" visuals");
		System.out.println("You can find the Terrain Generator here: "
				+ "\nhttps://github.com/FelixEcker/Java-Terrain-Generator");
		System.out.println("===================================");
		System.out.println("Starting main thread...");
		
		if (args.length > 0) {
			mapSize.setSize(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			tileSize.setSize(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
		} else {
			mapSize.setSize(64, 64);
			tileSize.setSize(16, 16);
		}
		
		t = new Thread(new Main(), "test");
		t.run();
	}

	@Override
	public void run() {
		hge = new HexagonGraphicsEngine(new int[]{1920, 1080}, "JTGGI", new Render(this, false));
		
		while (hge.getGameWindow().isVisible()) {
			hge.getRenderer().render();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public HexagonGraphicsEngine getHGE() { return this.hge; }
	public static Dimension getMapSize() { return mapSize; }
	public static Dimension getTileSize() { return tileSize;}
}
