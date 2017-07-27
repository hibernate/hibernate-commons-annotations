/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

//$Id: $
package org.hibernate.annotations.common.test.reflection.java.generics.deep;

import junit.framework.TestCase;
import org.hibernate.annotations.common.reflection.java.JavaReflectionManager;
import org.hibernate.annotations.common.reflection.XClass;


/**
 * @author Emmanuel Bernard
 */
public class DeepGenericsInheritance extends TestCase {
	public void test2StepsGenerics() throws Exception {
		JavaReflectionManager factory = new JavaReflectionManager();
		XClass subclass2 = factory.toXClass( Subclass2.class );
		XClass dummySubclass = factory.toXClass( DummySubclass.class );
		XClass superclass = subclass2.getSuperclass();
		XClass supersuperclass = superclass.getSuperclass();
		assertTrue( supersuperclass.getDeclaredProperties( "field" ).get( 1 ).isTypeResolved() );
		assertEquals( dummySubclass, supersuperclass.getDeclaredProperties( "field" ).get( 1 ).getType() );

	}
}
