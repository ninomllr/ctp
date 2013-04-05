package ch.bfh.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author gigi
 * 
 */
public class TaskReader {

	public ScenarioInfo getVehicles(String listname) {
		try {
			Gson gson = new Gson();

			if(Settings.DEBUG_MODE_ENABLED)System.out.println("Read Vehicles");
			if(Settings.DEBUG_MODE_ENABLED)System.out.println("--------------------------------------------------");

			BufferedReader brVehicles = new BufferedReader(new FileReader(
					Settings.DIR_TASKS + "/" + listname + "."
							+ Settings.EXT_TASKS));

			ScenarioInfo scenario = gson.fromJson(brVehicles,
					ScenarioInfo.class);

			return scenario;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}
