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
import org.hibernate.annotations.common.reflection.XAnnotatedElement;

/**
 * @author Paolo Perrotta
 * @author Davide Marchignoli
 */
abstract class JavaXAnnotatedElement implements XAnnotatedElement {

	private final JavaReflectionManager factory;

	private final AnnotatedElement annotatedElement;

	public JavaXAnnotatedElement(AnnotatedElement annotatedElement, JavaReflectionManager factory) {
        this.factory = factory;
		this.annotatedElement = annotatedElement;
	}

	protected JavaReflectionManager getFactory() {
		return factory;
	}

	private AnnotationReader getAnnotationReader() {
        return factory.buildAnnotationReader(annotatedElement);
	}

	public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
		return getAnnotationReader().getAnnotation( annotationType );
	}

	public <T extends Annotation> boolean isAnnotationPresent(Class<T> annotationType) {
		return getAnnotationReader().isAnnotationPresent( annotationType );
	}

	public Annotation[] getAnnotations() {
		return getAnnotationReader().getAnnotations();
	}

	AnnotatedElement toAnnotatedElement() {
		return annotatedElement;
	}

	@Override
	public boolean equals(Object obj) {
		if ( ! ( obj instanceof JavaXAnnotatedElement ) ) return false;
		JavaXAnnotatedElement other = (JavaXAnnotatedElement) obj;
		//FIXME yuk this defeat the type environment
		return annotatedElement.equals( other.toAnnotatedElement() );
	}

	@Override
	public int hashCode() {
		return annotatedElement.hashCode();
	}

	@Override
	public String toString() {
		return annotatedElement.toString();
	}
}
