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
 */
public interface MetadataProvider {

	/**
	 * provide default metadata
	 */
	Map<Object, Object> getDefaults();

	/**
	 * provide metadata for a gien annotated element
	 */
	AnnotationReader getAnnotationReader(AnnotatedElement annotatedElement);
}
