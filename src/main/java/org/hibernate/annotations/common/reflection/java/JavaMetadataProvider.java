/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.reflection.java;

import java.lang.reflect.AnnotatedElement;
import java.util.Collections;
import java.util.Map;
import org.hibernate.annotations.common.reflection.AnnotationReader;
import org.hibernate.annotations.common.reflection.MetadataProvider;

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
