package ch.bfh.cli;

import ch.bfh.utils.*;

public class MapLoader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MapReader loader = new MapReader();
		loader.loadMap("trains");
	}

}
