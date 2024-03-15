/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.reflection.java.generics;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * Returns the type context for a given <code>Class</code> or <code>ParameterizedType</code>.
 * <p/>
 * Does not support bindings involving inner classes, nor upper/lower bounds.
 *
 * @author Davide Marchignoli
 * @author Paolo Perrotta
 */
public final class TypeEnvironmentFactory {

	private TypeEnvironmentFactory() {
		//no need to construct
	}

	/**
	 * @return Returns a type environment suitable for resolving types occurring
	 *         in subclasses of the context class.
	 */
	public static TypeEnvironment getEnvironment(Class context) {
		if ( context == null ) {
        	return IdentityTypeEnvironment.INSTANCE;
        }
        return createEnvironment( context );
	}

	public static TypeEnvironment getEnvironment(Type context) {
		if ( context == null ) {
        	return IdentityTypeEnvironment.INSTANCE;
        }
        return createEnvironment( context );
	}

	public static TypeEnvironment getEnvironment(Type t, TypeEnvironment context) {
		return CompoundTypeEnvironment.create( getEnvironment( t ), context );
	}

	public static TypeEnvironment toApproximatingEnvironment(TypeEnvironment context) {
		return CompoundTypeEnvironment.create( new ApproximatingTypeEnvironment(), context );
	}

	private static TypeEnvironment createEnvironment(Type context) {
		return new TypeSwitch<TypeEnvironment>() {
			@Override
			public TypeEnvironment caseClass(Class classType) {
				return CompoundTypeEnvironment.create(
                        createSuperTypeEnvironment( classType ),
                        getEnvironment( classType.getSuperclass() )
                        );
			}

			@Override
			public TypeEnvironment caseParameterizedType(ParameterizedType parameterizedType) {
				return createEnvironment( parameterizedType );
			}

			@Override
			public TypeEnvironment defaultCase(Type t) {
				throw new IllegalArgumentException( "Invalid type for generating environment: " + t );
			}
		}.doSwitch( context );
	}

	private static TypeEnvironment createSuperTypeEnvironment(Class clazz) {
		Class superclass = clazz.getSuperclass();
		if ( superclass == null ) {
			return IdentityTypeEnvironment.INSTANCE;
		}

		Type genericSuperclass = clazz.getGenericSuperclass();

		if ( genericSuperclass instanceof Class ) {
			return IdentityTypeEnvironment.INSTANCE;
		}

		if ( genericSuperclass instanceof ParameterizedType ) {
			Type[] formalArgs = superclass.getTypeParameters();
			Type[] actualArgs = ( (ParameterizedType) genericSuperclass ).getActualTypeArguments();
			return new SimpleTypeEnvironment( formalArgs, actualArgs );
		}

		throw new AssertionError( "Should be unreachable" );
	}

	private static TypeEnvironment createEnvironment(ParameterizedType t) {
		Type[] tactuals = t.getActualTypeArguments();
		Type rawType = t.getRawType();
		if ( rawType instanceof Class ) {
			TypeVariable[] tparms = ( (Class) rawType ).getTypeParameters();
			return new SimpleTypeEnvironment( tparms, tactuals );
		}
		return IdentityTypeEnvironment.INSTANCE;
	}

}
