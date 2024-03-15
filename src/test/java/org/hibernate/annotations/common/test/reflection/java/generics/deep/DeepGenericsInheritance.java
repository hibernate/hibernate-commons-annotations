/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.test.reflection.java.generics.deep;

import junit.framework.TestCase;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.java.JavaReflectionManager;


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
