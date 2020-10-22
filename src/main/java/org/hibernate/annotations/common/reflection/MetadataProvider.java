/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.reflection;

import java.util.Map;
import java.lang.reflect.AnnotatedElement;

/**
 * Provides metadata
 *
 * @author Emmanuel Bernard
 * @author Sanne Grinovero
 */
public interface MetadataProvider {

	/**
	 * provide default metadata
	 */
	Map<Object, Object> getDefaults();

	/**
	 * provide metadata for a given annotated element
	 */
	AnnotationReader getAnnotationReader(AnnotatedElement annotatedElement);

	/**
	 * Reset any internal caches.
	 * This will free up memory in some implementations, at the cost of
	 * possibly being a bit slower if its services are needed again.
	 * Other configuration aspects are not affected.
	 */
	default void reset() {
		//By default a no-op
	}

}
