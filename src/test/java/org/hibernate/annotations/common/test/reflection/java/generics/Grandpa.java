/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.test.reflection.java.generics;

import java.io.Serializable;

/**
 * @author Davide Marchignoli
 * @author Paolo Perrotta
 */
public abstract class Grandpa<T, U> implements Serializable, Language<String> {

	Integer grandpaField;

	public T returnsGeneric() {
		return null;
	}

	// generic embedded value
	public Neighbour<U> getFriend() {
		return null;
	}
}
