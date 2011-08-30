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

import java.lang.reflect.Type;

import junit.framework.TestCase;

import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironment;
import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironmentFactory;
import org.hibernate.annotations.common.reflection.java.generics.TypeUtils;

public class TypeUtilsTest extends TestCase {

	TypeEnvironmentFactory env = new TypeEnvironmentFactory();
	TypeEnvironment dadContext = env.getEnvironment( Dad.class );
	TypeEnvironment sonContext = env.getEnvironment( Son.class );

	public void testAClassIsAlwaysFullyResolved() throws Exception {
		assertTrue( TypeUtils.isResolved( Dad.class ) );
	}

	private Type getPropertyFromDad(String propertyName) throws NoSuchMethodException {
		return Dad.class.getMethod( propertyName, new Class[0] ).getGenericReturnType();
	}

	public void testKnowsWhetherAParametricTypeIsFullyResolved() throws Exception {
		Type simpleType = getPropertyFromDad( "returnsGeneric" );
		assertFalse( TypeUtils.isResolved( dadContext.bind( simpleType ) ) );
		assertTrue( TypeUtils.isResolved( sonContext.bind( simpleType ) ) );
	}

	public void testKnowsWhetherAnArrayTypeIsFullyResolved() throws Exception {
		Type arrayType = getPropertyFromDad( "getArrayProperty" );
		assertFalse( TypeUtils.isResolved( dadContext.bind( arrayType ) ) );
		assertTrue( TypeUtils.isResolved( sonContext.bind( arrayType ) ) );
	}
	
	public void testKnowsWhetherATypeIsSimple() throws Exception {
		assertTrue( TypeUtils.isSimple( String.class ) );
		assertFalse( TypeUtils.isSimple( new String[1].getClass() ) );
		assertFalse( TypeUtils.isSimple( getPropertyFromDad( "getNongenericCollectionProperty" ) ) );
		assertFalse( TypeUtils.isSimple( getPropertyFromDad( "getGenericCollectionProperty" ) ) );
	}
}
