/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.reflection;

/**
 * Delegate for interacting with {@link ClassLoader} methods.
 *
 * @author Steve Ebersole
 * @deprecated This will be removed with no replacement: it will no longer be needed.
 */
@Deprecated
public interface ClassLoaderDelegate {
	/**
	 * Locate a class by name.
	 *
	 * @param className The name of the class to locate
	 * @param <T> The returned class type.
	 *
	 * @return The class reference
	 */
	public <T> Class<T> classForName(String className) throws ClassLoadingException;

}
