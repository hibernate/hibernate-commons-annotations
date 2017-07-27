/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.test.reflection.java.generics.deep;

import org.hibernate.annotations.common.test.reflection.java.generics.deep.Dummy;
import org.hibernate.annotations.common.test.reflection.java.generics.deep.GenericSuperclass1;

public class GenericSuperclass2<T extends Dummy> extends GenericSuperclass1<T> {
}
