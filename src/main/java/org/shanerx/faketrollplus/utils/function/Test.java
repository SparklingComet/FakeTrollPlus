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
	
	/**
	 * Inverts the check, so that {@code true} is returned when the check was <strong>positive</strong>.
	 * @return a new {@link Test}.
	 */
	default public Test invert() {
		return () -> !test();
	}
	
	/**
	 * Returns a new consumer that defines a new implementation of @li {@link #test()}.
	 * The implementation includes calling {@link #test()} as defined in the class which 
	 * this method is executed upon and calling the same method on the argument.
	 * {@code true} is returned <strong>if any only if</strong> both checks return {@code true}.
	 * @param test an implementation of {@link Test}.
	 * @return a new implementation of {@link Test}, as described above.
	 */
	default public Test and(Test test) {
		return () -> test() && test.test();
	}
	
	/**
	 * Returns a new consumer that defines a new implementation of @li {@link #test()}.
	 * The implementation includes calling {@link #test()} as defined in the class which 
	 * this method is executed upon and calling the same method on the argument.
	 * {@code true} is returned <strong>if any only if</strong> at least one of the checks return {@code true}.
	 * @param test an implementation of {@link Test}.
	 * @return a new implementation of {@link Test}, as described above.
	 */
	default public Test or(Test test) {
		return () -> test() || test.test();
	}
	
	/**
	 * Returns a new consumer that defines a new implementation of @li {@link #test()}.
	 * The implementation includes calling {@link #test()} as defined in the class which 
	 * this method is executed upon and calling the same method on the argument.
	 * {@code true} is returned <strong>if any only if</strong> the checks return <strong>the same value</strong>.
	 * @param test an implementation of {@link Test}.
	 * @return a new implementation of {@link Test}, as described above.
	 */
	default public Test resultEquals(Test test) {
		return () -> test() == test.test();
	}
	
}