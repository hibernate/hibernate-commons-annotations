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

package org.hibernate.annotations.common.test.reflection.java;

import junit.framework.TestCase;
import org.hibernate.annotations.common.reflection.ReflectionManager;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.java.JavaReflectionManager;

/**
 * @author Paolo Perrotta
 */
public class JavaReflectionManagerTest extends TestCase {

	private ReflectionManager rm = new JavaReflectionManager();

	public void testReturnsAnXClassThatWrapsTheGivenClass() {
		XClass xc = rm.toXClass( Integer.class );
		assertEquals( "java.lang.Integer", xc.getName() );
	}

	public void testReturnsSameXClassForSameClass() {
		XClass xc1 = rm.toXClass( void.class );
		XClass xc2 = rm.toXClass( void.class );
		assertSame( xc2, xc1 );
	}

	public void testReturnsNullForANullClass() {
		assertNull( rm.toXClass( null ) );
	}

	public void testComparesXClassesWithClasses() {
		XClass xc = rm.toXClass( Integer.class );
		assertTrue( rm.equals( xc, Integer.class ) );
	}

	public void testSupportsNullsInComparisons() {
		XClass xc = rm.toXClass( Integer.class );
		assertFalse( rm.equals( null, Number.class ) );
		assertFalse( rm.equals( xc, null ) );
		assertTrue( rm.equals( null, null ) );
	}
}
