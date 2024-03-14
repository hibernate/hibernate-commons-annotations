/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.reflection;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * The entry point to the reflection layer (a.k.a. the X* layer).
 *
 * @author Paolo Perrotta
 * @author Davide Marchignoli
 * @author Sanne Grinovero
 */
public interface ReflectionManager {

	public <T> XClass toXClass(Class<T> clazz);

	public Class toClass(XClass xClazz);

	public Type toType(XClass xClazz);

	public Method toMethod(XMethod method);

	public XPackage toXPackage(Package pkg);

	public <T> boolean equals(XClass class1, Class<T> class2);

    public AnnotationReader buildAnnotationReader(AnnotatedElement annotatedElement);

    public Map getDefaults();

	/**
	 * This resets any internal caches.
	 * This will free up memory in some implementations, at the cost of
	 * possibly being a bit slower if its services are needed again.
	 * <p>
	 *     Ideally the ReflectionManager should be discarded after use,
	 *     but sometimes that's not posible.
	 *     This method is intended to be used when there is reasonable
	 *     expectation for te ReflectionManager to no longer be needed,
	 *     while having the option to still use it in case the assumption
	 *     doesn't hold true.
	 * </p>
	 * <p>
	 * Careful: after invoking this method, returned X* instances will
	 * no longer honour any identity equality contract with X* instances
	 * which have been returned before resetting the cache.
	 * </p>
	 * This operation does not affect the configuration.
	 */
	default void reset() {
		//By default a no-op
	}

}
