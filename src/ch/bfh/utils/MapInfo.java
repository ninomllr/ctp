package ch.bfh.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * MapInfo configuration object
 * @author nino
 *
 */
public class MapInfo {
	private JsonObject mapnodeinfo;
	private JsonObject mapedgeinfo;
	@Override
	/**
	 * Return printable string
	 */
	public String toString() {
		return "Mapnodeinfo:";
	}
}
