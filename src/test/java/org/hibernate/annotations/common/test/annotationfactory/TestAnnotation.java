/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

/**
 * 
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