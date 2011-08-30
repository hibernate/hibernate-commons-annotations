/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2011, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */

package org.hibernate.annotations.common.test.reflection.java.generics;

import java.util.List;
import java.util.Map;

/**
 * @author Davide Marchignoli
 * @author Paolo Perrotta
 */
@TestAnnotation(name = "xyz")
public class Dad<T> extends Grandpa<List<T>, Integer> {

	static Integer staticField;

	T fieldProperty;

	public Map<Double, T> getCollectionProperty() {
		return null;
	}

	@TestAnnotation(name = "xyz")
	public Integer getMethodProperty() {
		return null;
	}

	public int getPrimitiveProperty() {
		return 0;
	}

	public boolean isPropertyStartingWithIs() {
		return false;
	}

	public int[] getPrimitiveArrayProperty() {
		return null;
	}

	public T[] getArrayProperty() {
		return null;
	}

	public List<T> getGenericCollectionProperty() {
		return null;
	}

	public List<String> getNongenericCollectionProperty() {
		return null;
	}

	public static Integer getStaticThing() {
		return null;
	}

	public String getLanguage() {
		return null;
	}
}
