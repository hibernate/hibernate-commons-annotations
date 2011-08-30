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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironmentFactory;

/**
 * @author Davide Marchignoli
 * @author Paolo Perrotta
 */
public class TypeEnvironmentFactoryTest extends TestCase {

	public void testBindsGenericsToSuperclassEnvironment() throws SecurityException, NoSuchMethodException {
		TypeEnvironmentFactory env = new TypeEnvironmentFactory();
		Type type = Grandpa.class.getMethod( "returnsGeneric", new Class[0] ).getGenericReturnType();

		Type asSeenFromGrandpa = env.getEnvironment( Grandpa.class ).bind( type );
		assertTrue( asSeenFromGrandpa instanceof TypeVariable );
		assertEquals( "T", asSeenFromGrandpa.toString() );

		Type asSeenFromDad = env.getEnvironment( Dad.class ).bind( type );
		assertTrue( asSeenFromDad instanceof ParameterizedType );
		assertEquals( "java.util.List<T>", asSeenFromDad.toString() );

		ParameterizedType asSeenFromSon = (ParameterizedType) env.getEnvironment( Son.class ).bind( type );
		assertType_isCollectionOfClass_withElementsOfClass( asSeenFromSon, List.class, String.class );
	}

	public void testBindsGenericsToOwnerEnvironment() throws SecurityException, NoSuchMethodException {
		TypeEnvironmentFactory env = new TypeEnvironmentFactory();

		Type friendType = Dad.class.getMethod( "getFriend", new Class[0] ).getGenericReturnType();
		ParameterizedType friendTypeAsSeenFromDad = (ParameterizedType) env.getEnvironment( Dad.class ).bind(
				friendType
		);

		Class friendClass = (Class) friendTypeAsSeenFromDad.getRawType();
		Type returnType = friendClass.getMethod( "embeddedProperty", new Class[0] ).getGenericReturnType();

		ParameterizedType boundType = (ParameterizedType) env.getEnvironment( friendTypeAsSeenFromDad ).bind(
				returnType
		);
		assertType_isCollectionOfClass_withElementsOfClass( boundType, Set.class, Integer.class );
	}

	private void assertType_isCollectionOfClass_withElementsOfClass(
			ParameterizedType t, Class collectionClass,
			Class elementClass
	) {
		assertEquals( collectionClass, t.getRawType() );
		assertEquals( 1, t.getActualTypeArguments().length );
		assertEquals( elementClass, t.getActualTypeArguments()[0] );
	}
}
