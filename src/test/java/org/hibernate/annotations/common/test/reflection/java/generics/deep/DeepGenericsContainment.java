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

//$Id: $
package org.hibernate.annotations.common.test.reflection.java.generics.deep;

import java.util.List;

import junit.framework.TestCase;

import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.annotations.common.reflection.java.JavaReflectionManager;


/**
 * @author Paolo Perrotta
 */
public class DeepGenericsContainment extends TestCase {

	public static class Contained<T> {
	    T generic;
	}
	
	public static class Container {
		Contained<String> contained;
	}
	
	public static class ContainerWithCollection {
		List<Contained<String>> contained;
	}

	public void test2StepsGenerics() throws Exception {
		JavaReflectionManager factory = new JavaReflectionManager();
		XClass container = factory.toXClass( Container.class );
		XProperty contained = container.getDeclaredProperties( XClass.ACCESS_FIELD ).get( 0 );
		assertTrue( contained.isTypeResolved() );
		XProperty generic = contained.getType().getDeclaredProperties( XClass.ACCESS_FIELD ).get( 0 );
		assertTrue( generic.isTypeResolved() );
	}

	public void test2StepsGenericsCollection() throws Exception {
		JavaReflectionManager factory = new JavaReflectionManager();
		XClass container = factory.toXClass( ContainerWithCollection.class );
		XProperty collection = container.getDeclaredProperties( XClass.ACCESS_FIELD ).get( 0 );
		assertTrue( collection.isTypeResolved() );
		XClass elementClass = collection.getElementClass();
		XProperty generic = elementClass.getDeclaredProperties( XClass.ACCESS_FIELD ).get( 0 );
		assertTrue( generic.isTypeResolved() );
	}
}
