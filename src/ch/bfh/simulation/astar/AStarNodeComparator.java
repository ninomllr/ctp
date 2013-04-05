package ch.bfh.simulation.astar;

import java.util.Comparator;

/**
 * Comparator for priority queue
 * @author gigi
 *
 */
public class AStarNodeComparator implements Comparator<AStarNode> {

	/**
	 * Compare key value between two nodes
	 */
    public int compare(AStarNode first, AStarNode second) {
        if(first.getF() < second.getF()){
            return -1;
        }else if(first.getF() > second.getF()){
            return 1;
        }else{
            return 0;
        }
    }
}
