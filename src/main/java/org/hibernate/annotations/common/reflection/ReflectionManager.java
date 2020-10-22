/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
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
	 * @deprecated This will be removed with no replacement: it will no longer be needed.
	 */
	@Deprecated
	public void injectClassLoaderDelegate(ClassLoaderDelegate delegate);

	/**
	 * Access to the ClassLoaderDelegate currently associated with this ReflectionManager
	 *
	 * @return The current ClassLoaderDelegate
	 * @deprecated This will be removed with no replacement: it will no longer be needed.
	 */
	@Deprecated
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
	 * @deprecated This will be removed with no replacement: it will no longer be needed.
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
	 * @deprecated This will be removed with no replacement: it will no longer be needed.
	 */
	@Deprecated
	public XClass classForName(String name) throws ClassLoadingException;

	/**
	 * @deprecated This will be removed with no replacement: it will no longer be needed.
	 * @param packageName
	 * @return
	 * @throws ClassNotFoundException
	 */
	@Deprecated
	public XPackage packageForName(String packageName) throws ClassNotFoundException;

	public XPackage toXPackage(Package pkg);

	public <T> boolean equals(XClass class1, Class<T> class2);

    public AnnotationReader buildAnnotationReader(AnnotatedElement annotatedElement);

    public Map getDefaults();
}
