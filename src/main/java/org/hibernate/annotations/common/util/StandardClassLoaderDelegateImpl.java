/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.util;

import org.jboss.logging.Logger;

import org.hibernate.annotations.common.reflection.ClassLoaderDelegate;
import org.hibernate.annotations.common.reflection.ClassLoadingException;
import org.hibernate.annotations.common.util.impl.LoggerFactory;

/**
 * @author Steve Ebersole
 */
public class StandardClassLoaderDelegateImpl implements ClassLoaderDelegate {
	/**
	 * Singleton access
	 */
	public static final StandardClassLoaderDelegateImpl INSTANCE = new StandardClassLoaderDelegateImpl();

	private static final Logger log = LoggerFactory.logger( StandardClassLoaderDelegateImpl.class );

	@Override
	@SuppressWarnings("unchecked")
	public <T> Class<T> classForName(String className) throws ClassLoadingException {
		try {
			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
			if ( contextClassLoader != null ) {
				return (Class<T>) Class.forName( className, true, contextClassLoader );
			}
		}
		catch ( Throwable ignore ) {
			log.debugf( "Unable to locate Class [%s] using TCCL, falling back to HCANN ClassLoader", className );
		}

		try {
			return (Class<T>) Class.forName( className, true, getClass().getClassLoader() );
		}
		catch (ClassNotFoundException e) {
			throw new ClassLoadingException( "Unable to load Class [" + className + "]", e );
		}
	}
}
