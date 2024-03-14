/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.test.reflection.java.generics;

import java.util.Set;

/**
 * @author Davide Marchignoli
 * @author Paolo Perrotta
 */
public class Neighbour<T> {

	public Set<T> embeddedProperty() {
		return null;
	}
}
