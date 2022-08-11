/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.reflection.java;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.common.reflection.Filter;
import org.hibernate.annotations.common.reflection.ReflectionUtil;
import org.hibernate.annotations.common.reflection.XAnnotatedElement;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XMethod;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.annotations.common.reflection.java.generics.CompoundTypeEnvironment;
import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironment;

/**
 * @author Paolo Perrotta
 * @author Davide Marchignoli
 */
final class JavaXClass extends JavaXAnnotatedElement implements XClass {

	private static final Class<?> RECORD_CLASS;
	private static final Method GET_RECORD_COMPONENTS;
	private static final Method GET_ACCESSOR;

	static {
		Class<?> recordClass = null;
		Method getRecordComponents = null;
		Method getAccessor = null;

		try {
			recordClass = Class.forName( "java.lang.Record" );
			getRecordComponents = Class.class.getMethod( "getRecordComponents" );
			final Class<?> recordComponentClass = Class.forName( "java.lang.reflect.RecordComponent" );
			getAccessor = recordComponentClass.getMethod( "getAccessor" );
		}
		catch (Exception e) {
			// Ignore
		}
		RECORD_CLASS = recordClass;
		GET_RECORD_COMPONENTS = getRecordComponents;
		GET_ACCESSOR = getAccessor;
	}

	private final TypeEnvironment context;
    private final Class clazz;

    public JavaXClass(Class clazz, TypeEnvironment env, JavaReflectionManager factory) {
		super( clazz, factory );
        this.clazz = clazz; //optimization
        this.context = env;
	}

	public String getName() {
		return toClass().getName();
	}

	public XClass getSuperclass() {
		return getFactory().toXClass( toClass().getSuperclass(),
				CompoundTypeEnvironment.create(
                        getTypeEnvironment(),
                        getFactory().getTypeEnvironment( toClass() )
				)
		);
	}

	@Override
	public XAnnotatedElement getContainingElement() {
		Class<?> enclosingClass = toClass().getEnclosingClass();
		if ( enclosingClass != null ) {
			return getFactory().toXClass( enclosingClass,
					CompoundTypeEnvironment.create(
							getTypeEnvironment(),
							getFactory().getTypeEnvironment( toClass() )
					)
			);
		}
		else {
			return getFactory().toXPackage( toClass().getPackage() );
		}
	}

	public XClass[] getInterfaces() {
		Class[] classes = toClass().getInterfaces();
		int length = classes.length;
		XClass[] xClasses = new XClass[length];
        if (length != 0) {
            TypeEnvironment environment = CompoundTypeEnvironment.create(
                    getTypeEnvironment(),
                    getFactory().getTypeEnvironment( toClass() )
                    );
            for ( int index = 0; index < length ; index++ ) {
                xClasses[index] = getFactory().toXClass( classes[index], environment );
            }
        }
        return xClasses;
	}

	public boolean isInterface() {
		return toClass().isInterface();
	}

	public boolean isAbstract() {
		return Modifier.isAbstract( toClass().getModifiers() );
	}

	public boolean isPrimitive() {
		return toClass().isPrimitive();
	}

	public boolean isEnum() {
		return toClass().isEnum();
	}

	private List<XProperty> getDeclaredFieldProperties(Filter filter) {
		Field[] declaredFields = toClass().getDeclaredFields();
		ArrayList<XProperty> result = new ArrayList<>();
		for ( Field f : declaredFields ) {
			if ( ReflectionUtil.isProperty( f, getTypeEnvironment().bind( f.getGenericType() ), filter ) ) {
				result.add( getFactory().getXProperty( f, getTypeEnvironment() ) );
			}
		}
		result.trimToSize();
		return result;
	}

	private List<XProperty> getDeclaredMethodProperties(Filter filter) {
		ArrayList<XProperty> result = new ArrayList<XProperty>();
		Method[] declaredMethods = toClass().getDeclaredMethods();
		for ( Method m : declaredMethods ) {
			if ( ReflectionUtil.isProperty( m, getTypeEnvironment().bind( m.getGenericReturnType() ), filter ) ) {
				result.add( getFactory().getXProperty( m, getTypeEnvironment() ) );
			}
		}
		result.trimToSize();
		return result;
	}

	private List<XProperty> getDeclaredComponentProperties(Filter filter) {
		ArrayList<XProperty> result = new ArrayList<XProperty>();
		Class<?> javaClass = toClass();
		if ( RECORD_CLASS == null || !RECORD_CLASS.isAssignableFrom( javaClass ) ) {
			return result;
		}
		try {
			Object[] declaredComponents = (Object[]) GET_RECORD_COMPONENTS.invoke(javaClass);
			for ( Object component : declaredComponents ) {
				Method accessor = (Method) GET_ACCESSOR.invoke(component );
				result.add( getFactory().getXProperty( accessor, getTypeEnvironment() ) );
			}
		} catch (Exception e) {
			throw new RuntimeException( "Could not access record components", e );
		}
		result.trimToSize();
		return result;
	}

	public List<XProperty> getDeclaredProperties(String accessType) {
		return getDeclaredProperties( accessType, XClass.DEFAULT_FILTER );
	}

	public List<XProperty> getDeclaredProperties(String accessType, Filter filter) {
		if ( accessType.equals( ACCESS_FIELD ) ) {
			return getDeclaredFieldProperties( filter );
		}
		if ( accessType.equals( ACCESS_PROPERTY ) ) {
			return getDeclaredMethodProperties( filter );
		}
		if ( accessType.equals( ACCESS_COMPONENT ) ) {
			return getDeclaredComponentProperties( filter );
		}
		throw new IllegalArgumentException( "Unknown access type " + accessType );
	}

	public List<XMethod> getDeclaredMethods() {
		Method[] declaredMethods = toClass().getDeclaredMethods();
		List<XMethod> result = new ArrayList<>( declaredMethods.length );
		for ( Method m : declaredMethods ) {
			result.add( getFactory().getXMethod( m, getTypeEnvironment() ) );
		}
		return result;
	}

	public Class<?> toClass() {
		return clazz;
	}

	public boolean isAssignableFrom(XClass c) {
		return toClass().isAssignableFrom( ( (JavaXClass) c ).toClass() );
	}

	boolean isArray() {
		return toClass().isArray();
	}

	TypeEnvironment getTypeEnvironment() {
		return context;
	}
    
    @Override
    public String toString() {
        return getName();
    }
}
