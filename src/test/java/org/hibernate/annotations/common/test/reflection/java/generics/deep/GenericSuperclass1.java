/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.test.reflection.java.generics.deep;


public class GenericSuperclass1<T extends Dummy> {
	protected Long id;

	protected T dummy;


	public Long getId() {
		return id;
	}

	public T getDummy() {
		return dummy;
	}
}
