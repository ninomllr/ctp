package ch.bfh.simulation.reservation;

import java.util.*;


public class SortedList<T> extends LinkedList<T> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Comparator<? super T> comparator = null;
	
	/**
	 * Constructor
	 */
	public SortedList() {
		
	}
	
	/**
	 * Constructor
	 * @param comparator
	 */
	public SortedList(Comparator<? super T> comparator) {
		this.comparator = comparator;
	}
	
	/**
	 * Constructor, the comparator will be used of the collection
	 * @param collection
	 */
	public SortedList(SortedList<? extends T> collection) {
		this.comparator = (Comparator<? super T>) collection.comparator;
		addAll(collection);
	}
	
	@Override
	/**
	 * Adds a value to the list
	 */
	public boolean add(T param) {
		int insertionPoint = Collections.binarySearch(this, param, comparator);
		super.add((insertionPoint>-1)?insertionPoint : (-insertionPoint)-1, param);
		return true;
	}
	
	@Override
	/**
	 * Adds multiple values to the list
	 */
	public boolean addAll(Collection<? extends T> collection) {
		boolean result = false;
		for (T param:collection) {
			result |=add(param);
		}
		return result;
	}
	
	/**
	 * Check if a value is in the list
	 * @param param
	 * @return
	 */
	public boolean containsElement(T param) {
		return (Collections.binarySearch(this, param, comparator) > -1);
	}
}
