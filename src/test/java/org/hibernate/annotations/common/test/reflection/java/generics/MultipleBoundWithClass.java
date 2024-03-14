/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.test.reflection.java.generics;


public class MultipleBoundWithClass<T extends String & MultipleBoundWithClass.Bound> {
	T genericType;

	public T getGenericType(){
		return genericType;
	}

	public interface Bound{

	}
}
