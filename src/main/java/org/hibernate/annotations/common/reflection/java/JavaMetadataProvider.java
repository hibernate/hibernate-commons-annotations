/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.reflection.java;

import java.util.Map;
import java.util.Collections;
import java.lang.reflect.AnnotatedElement;

import org.hibernate.annotations.common.reflection.MetadataProvider;
import org.hibernate.annotations.common.reflection.AnnotationReader;

/**
 * @author Emmanuel Bernard
*/
public final class JavaMetadataProvider implements MetadataProvider {

	public Map<Object, Object> getDefaults() {
		return Collections.emptyMap();
	}

	public AnnotationReader getAnnotationReader(AnnotatedElement annotatedElement) {
		return new JavaAnnotationReader(annotatedElement);
	}

	@Override
	public void reset() {
		//no-op
	}

}
