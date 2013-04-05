package ch.bfh.simulation;

import java.util.Comparator;


public class PriorityComparator implements Comparator<IUpdateable> {
	
	/**
	 * Compares two Updatables by their priority
	 */
	public int compare(IUpdateable first, IUpdateable second) {
        if(first.getPriority() < second.getPriority()){
            return -1;
        }else if(first.getPriority() > second.getPriority()){
            return 1;
        }else{
            return 0;
        }
    }
}
