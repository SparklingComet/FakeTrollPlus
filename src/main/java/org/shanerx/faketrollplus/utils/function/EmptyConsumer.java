package org.shanerx.faketrollplus.utils.function;

/**
 * Functional interface for utility purposes.
 * <br>
 * Requires {@code JDK 1.8}.
 */
@FunctionalInterface
public interface EmptyConsumer {
	
	/**
	 * <p>Applies the function.
	 * <p>It does not take any arguments nor returns anything.
	 */
	void apply();
	
	/**
	 * Returns an {@link EmptyConsumer} that calls {@code this.{@link #apply()}} first and the implementation of the same method, as specified in the actual parameter next.
	 * @param consumer any {@link EmptyConsumer}.
	 * @return a new consumer.
	 */
	default public EmptyConsumer andThen(EmptyConsumer consumer) {
		return () -> {
			apply();
			consumer.apply();
		};
	}
	
	/**
	 * Returns an {@link EmptyConsumer} that calls the implementation of the method {@link #apply()}, as specified in the actual parameter, and {@code this.{@link #apply()}} afterwards. 
	 * @param consumer any {@link EmptyConsumer}.
	 * @return a new consumer.
	 */
	default public EmptyConsumer after(EmptyConsumer consumer) {
		return () -> {
			consumer.apply();
			apply();
		};
	}
	
}