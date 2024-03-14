/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.reflection.java.generics;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * This class instances our own <code>ParameterizedTypes</code> and <code>GenericArrayTypes</code>.
 * These are not supposed to be mixed with Java's implementations - beware of
 * equality/identity problems.
 *
 * @author Paolo Perrotta
 */
class TypeFactory {

	static ParameterizedType createParameterizedType(Type rawType, Type[] substTypeArgs, Type ownerType) {
		return new ParameterizedTypeImpl( rawType, substTypeArgs, ownerType );
	}

	static Type createArrayType(Type componentType) {
		if ( componentType instanceof Class ) {
			return Array.newInstance( (Class<?>) componentType, 0 ).getClass();
		}
		return createGenericArrayType( componentType );
	}

	private static GenericArrayType createGenericArrayType(Type componentType) {
		return new GenericArrayTypeImpl( componentType );
	}

}
