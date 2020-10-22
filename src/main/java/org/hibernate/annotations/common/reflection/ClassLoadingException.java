/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.reflection;

/**
 * @author Steve Ebersole
 * @deprecated This will be removed with no replacement: it will no longer be needed.
 */
@Deprecated
public class ClassLoadingException extends RuntimeException {
	public ClassLoadingException(String message) {
		super( message );
	}

	public ClassLoadingException(String message, Throwable cause) {
		super( message, cause );
	}
}
