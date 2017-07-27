/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.test.reflection.java;

import java.util.List;

/**
 * @author Paolo Perrotta
 */
public abstract class FooFather<T> {

	public Integer fatherField;

	public Boolean isFatherMethod() {
		return null;
	}

	public T getParameterizedProperty() {
		return null;
	}

	public List<T> getParameterizedCollectionProperty() {
		return null;
	}
}
