/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
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
	private final Map<String, Object> elements = new HashMap<String, Object>();

	public AnnotationDescriptor(Class<? extends Annotation> annotationType) {
		type = annotationType;
	}

	public void setValue(String elementName, Object value) {
		elements.put( elementName, value );
	}

	public Object valueOf(String elementName) {
		return elements.get( elementName );
	}

	public boolean containsElement(String elementName) {
		return elements.containsKey( elementName );
	}

	public int numberOfElements() {
		return elements.size();
	}

	public Class<? extends Annotation> type() {
		return type;
	}
}
