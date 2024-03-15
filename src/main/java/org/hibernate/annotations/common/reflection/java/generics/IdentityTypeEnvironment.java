/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.reflection.java.generics;

import java.lang.reflect.Type;

/**
 * Substitutes a <code>Type</code> for itself.
 *
 * @author Davide Marchignoli
 * @author Paolo Perrotta
 */
public final class IdentityTypeEnvironment implements TypeEnvironment {

	public static final TypeEnvironment INSTANCE = new IdentityTypeEnvironment();

	private IdentityTypeEnvironment() {
	}

	public Type bind(Type type) {
		return type;
	}

    public String toString() {
        return "{}";
    }
}
