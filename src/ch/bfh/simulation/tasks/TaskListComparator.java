package ch.bfh.simulation.tasks;

import java.util.Comparator;

public class TaskListComparator implements Comparator<Task> {
	
	public int compare(Task first, Task second)
	{
		if (first.getArrival() < second.getArrival()) {
			return -1;
		}
		else if(first.getArrival() > second.getArrival())
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}

}
