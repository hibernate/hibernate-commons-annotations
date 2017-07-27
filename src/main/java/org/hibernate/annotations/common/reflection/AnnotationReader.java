/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.reflection;

import java.lang.annotation.Annotation;

/**
 * @author Paolo Perrotta
 */
public interface AnnotationReader {

    public <T extends Annotation> T getAnnotation(Class<T> annotationType);

    public <T extends Annotation> boolean isAnnotationPresent(Class<T> annotationType);

    public Annotation[] getAnnotations();
}