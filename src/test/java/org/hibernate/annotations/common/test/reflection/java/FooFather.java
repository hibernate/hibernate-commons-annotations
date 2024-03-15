/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.test.reflection.java;

import java.util.List;

/**
 * @author Paolo Perrotta
 */
public abstract class FooFather<T> {

	public Integer fatherField;

	public Boolean isFatherMethod() {
		return null;
	}

	public T getParameterizedProperty() {
		return null;
	}

	public List<T> getParameterizedCollectionProperty() {
		return null;
	}
}
