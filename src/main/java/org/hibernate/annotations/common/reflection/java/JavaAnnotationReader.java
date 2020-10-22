/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.reflection.java;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.hibernate.annotations.common.reflection.AnnotationReader;

/**
 * Reads standard Java annotations.
 *
 * @author Paolo Perrotta
 * @author Davide Marchignoli
 */
final class JavaAnnotationReader implements AnnotationReader {

	protected final AnnotatedElement element;

	public JavaAnnotationReader(AnnotatedElement el) {
		this.element = el;
	}

	public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
		return element.getAnnotation( annotationType );
	}

	public <T extends Annotation> boolean isAnnotationPresent(Class<T> annotationType) {
		return element.isAnnotationPresent( annotationType );
	}

	public Annotation[] getAnnotations() {
		return element.getAnnotations();
	}
}
