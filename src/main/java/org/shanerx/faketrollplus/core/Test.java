package org.shanerx.faketrollplus.core;

/**
 * Used for internal argument validation purposes.
 * Same concept as {@link java.util.function.Predicate}, 
 * but does not take a parameter.
 * 
 * Another difference is that {@link #test()} returns 
 * {@code false} when the result is <strong>positive</strong>.
 */
@FunctionalInterface
public interface Test {

	/**
	 * Runs the check on the command arguments.
	 * @return <strong>false</strong> if successful
	 */
	boolean test();
	
}