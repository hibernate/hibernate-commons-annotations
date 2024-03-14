/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.reflection;

/**
 * Filter properties
 *
 * @author Emmanuel Bernard
 */
public interface Filter {
	boolean returnStatic();

	boolean returnTransient();
}
