/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.test.reflection.java;

import junit.framework.TestCase;
import org.hibernate.annotations.common.reflection.XAnnotatedElement;
import org.hibernate.annotations.common.test.reflection.java.generics.TestAnnotation;

/**
 * @author Paolo Perrotta
 */
public abstract class XAnnotatedElementTestCase extends TestCase {

	public void testKnowsWhetherAnAnnotationIsPresent() {
		assertTrue( getConcreteInstance().isAnnotationPresent( TestAnnotation.class ) );
		assertFalse( getConcreteInstance().isAnnotationPresent( Override.class ) );
	}

	public void testReturnsSpecificAnnotations() {
		TestAnnotation ent = getConcreteInstance().getAnnotation( TestAnnotation.class );
		assertEquals( "xyz", ent.name() );
	}

	protected abstract XAnnotatedElement getConcreteInstance();
}
