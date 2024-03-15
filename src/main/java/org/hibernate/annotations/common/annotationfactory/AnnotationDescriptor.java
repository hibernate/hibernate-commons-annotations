/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.annotationfactory;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates the data you need to create an annotation. In
 * particular, it stores the type of an <code>Annotation</code> instance
 * and the values of its elements.
 * The "elements" we're talking about are the annotation attributes,
 * not its targets (the term "element" is used ambiguously
 * in Java's annotations documentation).
 *
 * @author Paolo Perrotta
 * @author Davide Marchignoli
 */
public final class AnnotationDescriptor {

	private final Class<? extends Annotation> type;
	private Map<String, Object> elements;

	public AnnotationDescriptor(Class<? extends Annotation> annotationType) {
		type = annotationType;
	}

	public void setValue(String elementName, Object value) {
		if ( elements == null ) {
			elements = new HashMap<>( 4 ); //likely to be small
		}
		elements.put( elementName, value );
	}

	public Object valueOf(String elementName) {
		return elements == null ? null : elements.get( elementName );
	}

	public boolean containsElement(String elementName) {
		return elements == null ? false : elements.containsKey( elementName );
	}

	public int numberOfElements() {
		return elements == null ? 0 : elements.size();
	}

	public Class<? extends Annotation> type() {
		return type;
	}
}
