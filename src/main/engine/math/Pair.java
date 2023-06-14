package main.engine.math;

/**
 * Represents a 2-tuple. Immutable.
 * @author Andreas - s214971
 *
 */
public class Pair<T1, T2> {

	public final T1 first;
	public final T2 second;
	
	public Pair(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}
}
