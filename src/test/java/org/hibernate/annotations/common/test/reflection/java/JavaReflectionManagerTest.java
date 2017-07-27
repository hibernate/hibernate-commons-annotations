/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
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
