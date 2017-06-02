/*
 *     Copyright 2016-2017 ShanerX @ http://shanerx.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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