/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.test.reflection.java.generics;

/**
 * @author Emmanuel Bernard
 */
public interface Language<T> {
	T getLanguage();
}
