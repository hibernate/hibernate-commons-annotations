/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.test.reflection.java.generics;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class BigBlob<T, E extends Collection> {

	public E simpleGenericType() {
		return null;
	}

	public Class<?> genericClass() {
		return null;
	}

	public Class<T> genericType() {
		return null;
	}

	public Map<T, ? extends E> genericCollection() {
		return null;
	}

	public E[] array() {
		return null;
	}

	public List<? extends T>[] complexGenericArray() {
		return null;
	}
}
