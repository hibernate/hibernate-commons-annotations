/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2011, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.annotations.common.reflection;

/**
 * A member which actually is a property (as per the JavaBean spec)
 * Note that the same underlying artefact can be represented as both
 * XProperty and XMethod
 * The underlying layer does not guaranty that xProperty == xMethod
 * if the underlying artefact is the same
 * However xProperty.equals(xMethod) is supposed to return true
 *
 * @author Paolo Perrotta
 * @author Davide Marchignoli
 * @author Emmanuel Bernard
 */
public interface XProperty extends XMember {

	/**
	 * Unqualify the getter name
	 */
	String getName();
}
