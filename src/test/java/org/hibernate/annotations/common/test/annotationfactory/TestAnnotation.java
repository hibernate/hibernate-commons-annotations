/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.test.annotationfactory;

/**
 * @author Paolo Perrotta
 * @author Davide Marchignoli
 */
@interface TestAnnotation {
	String stringElement();

	String elementWithDefault() default "abc";

	boolean booleanElement();

	String someOtherElement();
}
