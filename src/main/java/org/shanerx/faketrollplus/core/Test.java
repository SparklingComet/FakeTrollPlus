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