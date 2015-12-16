/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2008, Red Hat Middleware LLC or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Middleware LLC.
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
package org.hibernate.annotations.common.reflection.java;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironment;

/**
 * @author Paolo Perrotta
 * @author Davide Marchignoli
 */
final class JavaXProperty extends JavaXMember implements XProperty {

	private static final Object[] EMPTY_ARRAY = new Object[0];

	static JavaXProperty create(Member member, final TypeEnvironment context, final JavaReflectionManager factory) {
		final Type propType = typeOf( member, context );
		JavaXType xType = factory.toXType( context, propType );
		return new JavaXProperty( member, propType, context, factory, xType );
	}

	private JavaXProperty(Member member, Type type, TypeEnvironment env, JavaReflectionManager factory, JavaXType xType) {
		super( member, type, env, factory, xType );
		assert member instanceof Field || member instanceof Method;
	}

	public String getName() {
		String fullName = getMember().getName();
		if ( getMember() instanceof Method ) {
			if ( fullName.startsWith( "get" ) ) {
				return Introspector.decapitalize( fullName.substring( "get".length() ) );
			}
			if ( fullName.startsWith( "is" ) ) {
				return Introspector.decapitalize( fullName.substring( "is".length() ) );
			}
			throw new RuntimeException( "Method " + fullName + " is not a property getter" );
		}
		else {
			return fullName;
		}
	}

	@Override
	public Object invoke(Object target) {
		//Implementation note: only #invoke(Object target, Object... parameters)
		//existed until HCANN 5.0.0.Final, but it turned out to be a performance issue as that would cause
		//each invocation to allocate an empty array to pass as vararg.
		try {
			if ( getMember() instanceof Method ) {
				return ( (Method) getMember() ).invoke( target, EMPTY_ARRAY );
			}
			else {
				Field field = (Field) getMember();
				// This is needed because until JDK 9 the Reflection API
				// does not use the same caching as used for auto-boxing.
				// See https://bugs.openjdk.java.net/browse/JDK-5043030 for details.
				// The code below can be removed when we move to JDK 9.
				// double and float are intentionally not handled here because
				// the JLS § 5.1.7 does not define caching for boxed values of
				// this types.
				Class<?> type = field.getType();
				if ( type.isPrimitive() ) {
					if ( type == Boolean.TYPE ) {
						return Boolean.valueOf( field.getBoolean( target ) );
					} else if ( type == Byte.TYPE ) {
						return Byte.valueOf( field.getByte( target ) );
					} else if ( type == Character.TYPE ) {
						return Character.valueOf( field.getChar( target ) );
					} else if ( type == Integer.TYPE ) {
						return Integer.valueOf( field.getInt( target ) );
					} else if ( type == Long.TYPE ) {
						return Long.valueOf( field.getLong( target ) );
					} else if ( type == Short.TYPE ) {
						return Short.valueOf( field.getShort( target ) );
					}
				}
				return field.get( target );
			}
		}
		catch (NullPointerException e) {
			throw new IllegalArgumentException( "Invoking " + getName() + " on a  null object", e );
		}
		catch (IllegalArgumentException e) {
			throw new IllegalArgumentException( "Invoking " + getName() + " with wrong parameters", e );
		}
		catch (Exception e) {
			throw new IllegalStateException( "Unable to invoke " + getName(), e );
		}
	}

	@Override
	public Object invoke(Object target, Object... parameters) {
		if ( parameters.length != 0 ) {
			throw new IllegalArgumentException( "An XProperty cannot have invoke parameters" );
		}
		return invoke( target );
	}

	@Override
	public String toString() {
		return getName();
	}
}
