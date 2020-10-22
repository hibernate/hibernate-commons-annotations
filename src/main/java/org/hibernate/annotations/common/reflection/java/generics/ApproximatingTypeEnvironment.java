/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.reflection.java.generics;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

/**
 * A <code>TypeEnvironment</code> that approximates the unresolved components of a generic simple
 * type or collection to their nearest upper binding. The returned type is always fully resolved.
 * <p/>
 * The concept of "type approximation" is not really sound in general. This class just does what we need
 * within the Hibernate Annotations environment. It's more or less a hack. The idea is that certain
 * types can provide useful information even if they're not fully resolved in the environment. This class
 * tries to turn those types into the nearest fully resolved type that still carries that information.
 * <p/>
 * For example:<br>
 * <code>T</code> becomes <code>Object</code>.<br>
 * <code>T extends Foo</code> becomes <code>Foo</code>.<br>
 * <code>List&ltT&gt</code> becomes <code>List&ltObject&gt</code>.<br>
 * <code>List&ltT extends Foo&gt</code> becomes <code>List&ltFoo&gt</code>.<br>
 * An array of <code>T extends Foo</code> becomes an array of <code>Foo</code>.<p>
 * <p/>
 * If a type variable has multiple upper bounds, it will be approximated to <code>Object</code>.
 * Lower bounds are ignored.<p>
 * <p/>
 * Wildcards are generally not approximated. <code>Class&lt?&gt</code> stays <code>Class&lt?&gt</code>.
 * A wildcard within a generic collection is approximated to its upper binding. <code>List&lt?&gt</code> becomes
 * <code>List&ltObject&gt</code><p>
 * <p/>
 * Note that <code>Class&ltT&gt</code> is <emp>not</emp> approximated <code>Class&ltObject&gt</code>.
 * That would be wrong in any situation. All parametric types that are not type variables, collections or
 * arrays are coarsely approximated to <code>Object.class</code>.
 *
 * @author Paolo Perrotta
 * @return a type where the generic arguments have been replaced by raw classes.
 */
final class ApproximatingTypeEnvironment implements TypeEnvironment {

	public Type bind(final Type type) {
		Type result = fineApproximation( type );
		assert TypeUtils.isResolved( result );
		return result;
	}

	private Type fineApproximation(final Type type) {
		return new TypeSwitch<Type>() {
			public Type caseWildcardType(WildcardType wildcardType) {
				return wildcardType;
			}

			@Override
			public Type caseClass(Class classType) {
				return classType;
			}

			@Override
			public Type caseGenericArrayType(GenericArrayType genericArrayType) {
				if ( TypeUtils.isResolved( genericArrayType ) ) {
					return genericArrayType;
				}
				Type componentType = genericArrayType.getGenericComponentType();
				Type boundComponentType = bind( componentType );
				if ( boundComponentType instanceof Class ) {
					return Array.newInstance( (Class) boundComponentType, 0 ).getClass();
				}
				// fall back to coarse approximation, because I found no standard way
				// to instance arrays of a generic type
				return Object[].class;
			}

			@Override
			public Type caseParameterizedType(ParameterizedType parameterizedType) {
				if ( TypeUtils.isResolved( parameterizedType ) ) {
					return parameterizedType;
				}

				if ( !TypeUtils.isCollection( parameterizedType ) ) {
					return Object.class; // fall back to coarse approximation
				}

				Type[] typeArguments = parameterizedType.getActualTypeArguments();
				Type[] approximatedTypeArguments = new Type[typeArguments.length];
				for ( int i = 0; i < typeArguments.length ; i++ ) {
					approximatedTypeArguments[i] = coarseApproximation( typeArguments[i] );
				}

				return TypeFactory.createParameterizedType(
						bind( parameterizedType.getRawType() ),
						approximatedTypeArguments,
						parameterizedType.getOwnerType()
				);
			}

			@Override
			public Type defaultCase(Type t) {
				return coarseApproximation( t );
			}
		}.doSwitch( type );
	}

	private Type coarseApproximation(final Type type) {
		Type result = new TypeSwitch<Type>() {
			public Type caseWildcardType(WildcardType wildcardType) {
				return approximateTo( wildcardType.getUpperBounds() );
			}

			@Override
			public Type caseGenericArrayType(GenericArrayType genericArrayType) {
				if ( TypeUtils.isResolved( genericArrayType ) ) {
					return genericArrayType;
				}
				return Object[].class;
			}

			@Override
			public Type caseParameterizedType(ParameterizedType parameterizedType) {
				if ( TypeUtils.isResolved( parameterizedType ) ) {
					return parameterizedType;
				}
				return Object.class;
			}

			@Override
			public Type caseTypeVariable(TypeVariable typeVariable) {
				return approximateTo( typeVariable.getBounds() );
			}

			private Type approximateTo(Type[] bounds) {
				if ( bounds.length != 1 ) {
					return Object.class;
				}
				return coarseApproximation( bounds[0] );
			}

			@Override
			public Type defaultCase(Type t) {
				return t;
			}
		}.doSwitch( type );
		assert TypeUtils.isResolved( result );
		return result;
	}
    
    @Override
    public String toString() {
        return "approximated_types";
    }
}
