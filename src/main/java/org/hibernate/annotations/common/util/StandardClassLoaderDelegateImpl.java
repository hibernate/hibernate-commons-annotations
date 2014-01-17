/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2013, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
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
