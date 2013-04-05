package ch.bfh.simulation;

/**
 * Updateable interface to attach to objects that should be updated in the model
 * @author nino
 *
 */
public interface IUpdateable {
	
	void update(double deltaT);
	int getPriority();

}
