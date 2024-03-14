/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.reflection.java;

import org.hibernate.annotations.common.reflection.XPackage;

/**
 * @author Paolo Perrotta
 * @author Davide Marchignoli
 */
final class JavaXPackage extends JavaXAnnotatedElement implements XPackage {

	public JavaXPackage(Package pkg, JavaReflectionManager factory) {
		super( pkg, factory );
	}

	public String getName() {
		return ( (Package) toAnnotatedElement() ).getName();
	}
}
