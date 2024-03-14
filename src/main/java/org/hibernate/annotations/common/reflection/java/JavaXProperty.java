/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.reflection.java;

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
	private static final Class<?> RECORD_CLASS;

	static {
		Class<?> recordClass = null;

		try {
			recordClass = Class.forName( "java.lang.Record" );
		}
		catch (Exception e) {
			// Ignore
		}
		RECORD_CLASS = recordClass;
	}

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
				return decapitalize( fullName.substring( "get".length() ) );
			}
			if ( fullName.startsWith( "is" ) ) {
				return decapitalize( fullName.substring( "is".length() ) );
			}
			// If the declaring class is a record, we return the record component name as property name
			if ( RECORD_CLASS == null || !RECORD_CLASS.isAssignableFrom( getMember().getDeclaringClass() ) ) {
				throw new RuntimeException( "Method " + fullName + " is not a property getter" );
			}
			return fullName;
		}
		else {
			return fullName;
		}
	}

	// See conventions expressed by https://docs.oracle.com/javase/7/docs/api/java/beans/Introspector.html#decapitalize(java.lang.String)
	private static String decapitalize(String name) {
		if (name != null && name.length() != 0) {
			if (name.length() > 1 && Character.isUpperCase(name.charAt(1))) {
				return name;
			} else {
				char[] chars = name.toCharArray();
				chars[0] = Character.toLowerCase(chars[0]);
				return new String(chars);
			}
		} else {
			return name;
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
				return ( (Field) getMember() ).get( target );
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
