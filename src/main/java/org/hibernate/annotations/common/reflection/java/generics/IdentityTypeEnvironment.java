/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
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
