package ch.bfh.utils;

import java.util.List;

public class ScenarioInfo {
	
	private int vehicles;
	private List<TaskListInfo> tasklist;
	
	public int getVehicles ()
	{
		return vehicles;
	}
	
	public void setVehicles(int vehicles){
		this.vehicles = vehicles;
	}

	public List<TaskListInfo> getTasklist() {
		return tasklist;
	}

	public void setTasklist(List<TaskListInfo> tasklist) {
		this.tasklist = tasklist;
	}

}
