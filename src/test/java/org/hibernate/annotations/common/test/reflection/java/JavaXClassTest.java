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

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.common.reflection.ReflectionManager;
import org.hibernate.annotations.common.reflection.XAnnotatedElement;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.annotations.common.reflection.java.JavaReflectionManager;
import org.hibernate.annotations.common.test.reflection.java.generics.Dad;
import org.hibernate.annotations.common.test.reflection.java.generics.Grandpa;
import org.hibernate.annotations.common.test.reflection.java.generics.Language;
import org.hibernate.annotations.common.test.reflection.java.generics.Son;

/**
 * @author Paolo Perrotta
 */
public class JavaXClassTest extends XAnnotatedElementTestCase {
	ReflectionManager factory = new JavaReflectionManager();

	XClass fatherAsSeenFromSon = factory.toXClass( Son.class ).getSuperclass();
	XClass grandpa = factory.toXClass( Grandpa.class );

	public void testHasAPointOfViewClass() {
		// Since Dad is an Entity, getting it through Son.getSuperclass() gives
		// us a view of properties from Dad with Son as a point of view.
		XClass sameView = factory.toXClass( Son.class ).getSuperclass();
		XClass differentView = factory.toXClass( Dad.class );
		assertSame( "Should be the same instance: same owner", sameView, fatherAsSeenFromSon );
		assertNotSame( "Should be a different instance: different owner", differentView, fatherAsSeenFromSon );
		assertEquals( ".equals() should show equality", sameView, differentView );
	}

	public void testHasAName() {
		assertSame( "org.hibernate.annotations.common.test.reflection.java.generics.Dad", fatherAsSeenFromSon.getName() );
	}

	public void testHasASuperclass() {
		assertEquals( grandpa, fatherAsSeenFromSon.getSuperclass() );
	}

	public void testSuperSuperClass() {
		assertEquals( factory.toXClass( Object.class ), grandpa.getSuperclass() );
		assertEquals( null, grandpa.getSuperclass().getSuperclass() );
	}

	public void testHasInterfaces() {
		XClass[] interfaces = fatherAsSeenFromSon.getSuperclass().getInterfaces();
		assertEquals( 2, interfaces.length );
		assertTrue( factory.equals( interfaces[0], Serializable.class ) );
		assertTrue( factory.equals( interfaces[1], Language.class ) );
	}

	public void testCanBeAssignableFromAnotherXClass() {
		assertFalse( fatherAsSeenFromSon.isAssignableFrom( grandpa ) );
		assertTrue( grandpa.isAssignableFrom( fatherAsSeenFromSon ) );
	}

	public void testExtractsPublicFieldsAsProperties() {
		List<XProperty> fieldProperties = fatherAsSeenFromSon.getDeclaredProperties( "field" );
		assertEquals( 1, fieldProperties.size() );
	}

	public void testExtractsPublicMethodsAsProperties() {
		List<XProperty> methodProperties = fatherAsSeenFromSon.getDeclaredProperties( "property" );
		assertEquals( 9, methodProperties.size() );
	}

	public void testCanBeAbstract() {
		assertFalse( fatherAsSeenFromSon.isAbstract() );
		assertTrue( factory.toXClass( Grandpa.class ).isAbstract() );
	}

	public void testCanBeAPrimitive() {
		assertFalse( fatherAsSeenFromSon.isPrimitive() );
		assertTrue( factory.toXClass( int.class ).isPrimitive() );
	}

	public void testCanBeAnEnum() {
		assertFalse( fatherAsSeenFromSon.isEnum() );
		assertTrue( factory.toXClass( Sex.class ).isEnum() );
	}

	@Override
	protected XAnnotatedElement getConcreteInstance() {
		return factory.toXClass( Dad.class );
	}

}
