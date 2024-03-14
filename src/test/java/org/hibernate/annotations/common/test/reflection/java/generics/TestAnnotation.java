/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.test.reflection.java.generics;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Davide Marchignoli
 * @author Paolo Perrotta
 */
@Target({TYPE, METHOD, FIELD})
@Retention(RUNTIME)
public @interface TestAnnotation {
	String name() default "abc";
}
