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
package org.hibernate.annotations.common.reflection;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * The entry point to the reflection layer (a.k.a. the X* layer).
 *
 * @author Paolo Perrotta
 * @author Davide Marchignoli
 */
public interface ReflectionManager {
	/**
	 * Allows injection of a ClassLoaderDelegate into the ReflectionManager
	 *
	 * @param delegate The ClassLoaderDelegate to use
	 */
	public void injectClassLoaderDelegate(ClassLoaderDelegate delegate);

	/**
	 * Access to the ClassLoaderDelegate currently associated with this ReflectionManager
	 *
	 * @return The current ClassLoaderDelegate
	 */
	public ClassLoaderDelegate getClassLoaderDelegate();

	public <T> XClass toXClass(Class<T> clazz);

	public Class toClass(XClass xClazz);

	public Method toMethod(XMethod method);

	/**
	 * Deprecated; do not use.
	 *
	 * @deprecated Instead use:<ul>
	 *     <li>
	 *         {@link #toXClass(Class)} after resolving the Class yourself
	 *     </li>
	 *     <li>
	 *         {@link #classForName(String)} after having injected the proper ClassLoaderDelegate to use via
	 *         {@link #injectClassLoaderDelegate}
	 *     </li>
	 * </ul>
	 *
	 * @param name
	 * @param caller
	 * @param <T>
	 * @return
	 * @throws ClassNotFoundException
	 */
	@Deprecated
	public <T> XClass classForName(String name, Class<T> caller) throws ClassNotFoundException;

	/**
	 * Given the name of a Class, retrieve the XClass representation.
	 * <p/>
	 * Uses ClassLoaderDelegate (via {@link #getClassLoaderDelegate()}) to resolve the Class reference
	 *
	 * @param name The name of the Class to load (as an XClass)
	 *
	 * @return The XClass instance
	 *
	 * @throws ClassLoadingException Indicates a problem resolving the Class; see {@link ClassLoaderDelegate#classForName}
	 */
	public XClass classForName(String name) throws ClassLoadingException;

	public XPackage packageForName(String packageName) throws ClassNotFoundException;

	public <T> boolean equals(XClass class1, Class<T> class2);

    public AnnotationReader buildAnnotationReader(AnnotatedElement annotatedElement);

    public Map getDefaults();
}
