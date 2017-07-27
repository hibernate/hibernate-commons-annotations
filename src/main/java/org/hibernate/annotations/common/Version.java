/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common;

/**
 * @author Emmanuel Bernard
 */
public class Version {
	public static String getVersionString() {
		return "[WORKING]";
	}

	public static void touch() {
	}

	public static void main(String[] args) {
		System.out.println( "Hibernate Commons Annotations {" + getVersionString() + "}" );
	}
}
