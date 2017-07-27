/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.reflection.java;

import org.hibernate.annotations.common.reflection.XPackage;

/**
 * @author Paolo Perrotta
 * @author Davide Marchignoli
 */
class JavaXPackage extends JavaXAnnotatedElement implements XPackage {

	public JavaXPackage(Package pkg, JavaReflectionManager factory) {
		super( pkg, factory );
	}

	public String getName() {
		return ( (Package) toAnnotatedElement() ).getName();
	}
}
