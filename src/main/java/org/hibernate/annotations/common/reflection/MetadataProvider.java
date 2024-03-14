/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.reflection;

import java.lang.reflect.AnnotatedElement;
import java.util.Map;

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
