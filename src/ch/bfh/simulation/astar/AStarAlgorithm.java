package ch.bfh.simulation.astar;

import java.util.*;

import ch.bfh.simulation.Vehicle;
import ch.bfh.simulation.graph.MapGraph;
import ch.bfh.simulation.graph.MapNode;

/**
 * Implementation of the A* algorithm
 * @author gigi
 *
 */
public class AStarAlgorithm {

		/**
		 * Search shortest path from source to target with a* algorith0m
		 * @param graph
		 * @param source
		 * @param target
		 * @return
		 */
	   public static List<MapNode> search(Vehicle vehicle, MapGraph graph, MapNode source, MapNode target, Boolean ignoreBlocks, double speed) {
		   
		    Map<String, AStarNode> openSet = new HashMap<String, AStarNode>();
	        PriorityQueue<AStarNode> pQueue = new PriorityQueue<AStarNode>(20, new AStarNodeComparator());
	        Map<String, AStarNode> closeSet = new HashMap<String, AStarNode>();
	        
	        // start node
	        AStarNode start = new AStarNode(source, 0, graph.calcManhattanDistance(source, target));
	        
	        // put start node into visited nodes list
	        openSet.put(source.getId(), start);
	        
	        // add start node as path to prio queue
	        pQueue.add(start);

	        AStarNode goal = null;
	        
	        // get list
	        while(openSet.size() > 0){
	        	
	        	// get node with best path so far
	            AStarNode x = pQueue.poll();
	            
	            // remove from visited
	            openSet.remove(x.getId());
	            
	            // at the destination
	            if(x.getId().equals(target.getId())){
	                //found
	                goal = x;
	                
	                // check if goal has only one neighbour
	                goal = checkAdjacents(graph, goal, x);
	                
	                break;
	            }else{
	            	
	                closeSet.put(x.getId(), x);
	                
	                Set<MapNode> neighbors = graph.getNeighbours(x.getId());
	                
	                // go trough neighbors
	                for (MapNode neighbor : neighbors) {
	                    findNextNode(graph, target, openSet, pQueue, closeSet,
								x, neighbor);
	                }
	            }
	        }

	        //after found the target, start to construct the path 
	        return generatePath(goal);
	    }

		/**
		 * Find the next node by checking all the adjacent nodes
		 * @param graph
		 * @param target
		 * @param openSet
		 * @param pQueue
		 * @param closeSet
		 * @param x
		 * @param neighbor
		 */
		private static void findNextNode(MapGraph graph, MapNode target,
				Map<String, AStarNode> openSet,
				PriorityQueue<AStarNode> pQueue,
				Map<String, AStarNode> closeSet, AStarNode x, MapNode neighbor) {
			AStarNode visited = closeSet.get(neighbor.getId());
			
			if (visited == null) {
				double g = x.getG() + graph.getEdge(x.getNode(), neighbor).getCost();
			    AStarNode n = openSet.get(neighbor.getId());

			    if (n == null) {
			        //not in the open set
			        n = new AStarNode(neighbor, g, graph.calcManhattanDistance(neighbor, target));
			        
			        n.setCameFrom(x);
			        openSet.put(neighbor.getId(), n);
			        pQueue.add(n);
			    } else if (g < n.getG()) {
			        //Have a better route to the current node, change its parent
			    	
			    	n.setCameFrom(x);
			        n.setG(g);
			        n.setH(graph.calcManhattanDistance(neighbor, target));
			    }
			}
		}

		/**
		 * Check adjacents if the current node only has one neighbour
		 * @param graph
		 * @param goal
		 * @param x
		 * @return
		 */
		private static AStarNode checkAdjacents(MapGraph graph, AStarNode goal,
				AStarNode x) {
			if (MapGraph.getInstance().getNeighbours(goal.getNode().getId()).size()==1) {
				if (goal.getCameFrom()!=null) {
			    	AStarNode node = new AStarNode(goal.getCameFrom().getNode(), 0, 0);
			    	double g = goal.getG() + graph.getEdge(x.getNode(), node.getNode()).getCost();
			    	node.setCameFrom(goal);
			        node.setG(g);
			        goal = node;
				}
			}
			return goal;
		}
	   
	   /**
	    * Converts AStarNodes to a normal MapNode route
	    * @param node
	    * @return
	    */
	   private static List<MapNode> generatePath(AStarNode node) {
		   if(node != null){
	            Stack<MapNode> stack = new Stack<MapNode>();
	            List<MapNode> list = new ArrayList<MapNode>();
	            stack.push(node.getNode());
	            AStarNode parent = node.getCameFrom();
	            while(parent != null){
	                stack.push(parent.getNode());
	                parent = parent.getCameFrom();
	            }

	            while(stack.size() > 0){
	                list.add(stack.pop());
	            }
	            return list;
	        }
	        
	        return null; 
	   }
	}