/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.reflection;

import java.util.List;

/**
 * @author Paolo Perrotta
 * @author Davide Marchignoli
 */
public interface XClass extends XAnnotatedElement {

	public static final String ACCESS_PROPERTY = "property";
	public static final String ACCESS_FIELD = "field";
	public static final String ACCESS_RECORD = "record";

	static final Filter DEFAULT_FILTER = new Filter() {

		public boolean returnStatic() {
			return false;
		}

		public boolean returnTransient() {
			return false;
		}
	};

	String getName();

	/**
	 * @see Class#getSuperclass()
	 */
	XClass getSuperclass();

	/**
	 * The containing class or package
	 */
	XAnnotatedElement getContainingElement();

	/**
	 * @see Class#getInterfaces()
	 */
	XClass[] getInterfaces();

	/**
	 * see Class#isInterface()
	 */
	boolean isInterface();

	boolean isAbstract();

	boolean isPrimitive();

	boolean isEnum();

	boolean isAssignableFrom(XClass c);

	List<XProperty> getDeclaredProperties(String accessType);

	List<XProperty> getDeclaredProperties(String accessType, Filter filter);

	/**
	 * Returns the <tt>Method</tt>s defined by this class.
	 */
	List<XMethod> getDeclaredMethods();
}
