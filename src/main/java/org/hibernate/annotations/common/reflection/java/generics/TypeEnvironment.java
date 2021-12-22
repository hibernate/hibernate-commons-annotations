/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.reflection.java.generics;

import java.lang.reflect.Type;

/**
 * A typing context that knows how to "resolve" the generic parameters of a
 * <code>Type</code>.
 * <p/>
 * For example:
 * <pre>
 * {@code
 *  class Shop&ltT&gt {
 *    List&ltT&gt getCatalog() { ... }
 *  }
 * }
 * </pre>
 * <p/>
 * <pre>
 * {@code
 * class Bakery extends Shop<Bread> { ... }
 * }
 * </pre>
 * <p/>
 * Consider the type returned by method <code>getCatalog()</code>. There are
 * two possible contexts here. In the context of <code>Shop</code>, the type
 * is <code>List<T></code>. In the context of <code>Bakery</code>, the
 * type is <code>List<Bread></code>. Each of these contexts can be
 * represented by a <code>TypeEnvironment</code>.
 *
 * @author Davide Marchignoli
 * @author Paolo Perrotta
 */
public interface TypeEnvironment {

	/**
	 * Binds as many generic components of the given type as possible in this
	 * context.
	 * <p/>
	 * Warning: if the returned <code>Type</code> is a <code>Class</code>,
	 * then it's guaranteed to be a regular Java <code>Class</code>. In all
	 * other cases, this method might return a custom implementation of some
	 * interface that extends <code>Type</code>. Be sure not to mix these
	 * objects with Java's implementations of <code>Type</code> to avoid
	 * potential identity problems.
	 * <p/>
	 * This class does not support bindings involving inner classes or
	 * upper/lower bounds.
	 *
	 * @return a type where the generic arguments have been replaced by raw
	 *         classes whenever this is possible.
	 */
	public Type bind(Type type);

}
