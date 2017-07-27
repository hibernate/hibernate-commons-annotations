/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.annotationfactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * Creates live annotations (actually <code>AnnotationProxies</code>) from <code>AnnotationDescriptors</code>.
 *
 * @author Paolo Perrotta
 * @author Davide Marchignoli
 * @see AnnotationProxy
 */
public class AnnotationFactory {

	/**
	 * Creates an Annotation proxy for the given annotation descriptor.
	 * <p/>
	 * NOTE: the proxy here is generated using the ClassLoader of the Annotation type's Class.  E.g.,
	 * if asked to create an Annotation proxy for javax.persistence.Entity we would use the ClassLoader
	 * of the javax.persistence.Entity Class for generating the proxy.
	 *
	 * @param descriptor The annotation descriptor
	 *
	 * @return The annotation proxy
	 */
	public static <T extends Annotation> T create(AnnotationDescriptor descriptor) {
		return create( descriptor, descriptor.type().getClassLoader() );
	}

	/**
	 * Legacy form of {@link #create(AnnotationDescriptor) using the current Thread#getContextClassLoader
	 * for proxy generation
	 *
	 * @param descriptor The annotation descriptor
	 *
	 * @return The annotation proxy
	 */
	public static <T extends Annotation> T createUsingTccl(AnnotationDescriptor descriptor) {
		return create( descriptor, Thread.currentThread().getContextClassLoader() );
	}

	/**
	 * Overloaded form of Annotation proxy creation that accepts an explicit ClassLoader.
	 *
	 * @param descriptor The annotation descriptor
	 * @param classLoader The ClassLoader to be used in defining the proxy
	 *
	 * @return The annotation proxy
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T create(AnnotationDescriptor descriptor, ClassLoader classLoader) {
		return (T) Proxy.newProxyInstance(
				classLoader,
				new Class[] {descriptor.type()},
				new AnnotationProxy( descriptor )
		);
	}
}
