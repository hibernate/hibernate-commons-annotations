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
