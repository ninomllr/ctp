package ch.bfh.utils;

/**
 * Call by Reference parameter
 * @author nino
 *
 * @param <T>
 */
public class Ref<T> 
{
	
	private T _value;
	
	/**
	 * Constructor
	 * @param value
	 */
	public Ref(T value) {
		this._value = value;
	}
	
	/**
	 * Gets the by reference value
	 * @return
	 */
	public T getValue() {
		return this._value;
	}
	
	/**
	 * Sets the by reference value
	 * @param value
	 */
	public void setValue(T value) {
		this._value = value;
	}
}
