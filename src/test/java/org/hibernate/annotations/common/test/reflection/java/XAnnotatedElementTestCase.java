/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
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
