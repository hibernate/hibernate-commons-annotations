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

//$Id: $
package org.hibernate.annotations.common.test.reflection.java.generics.deep;

import java.util.List;

import junit.framework.TestCase;

import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.annotations.common.reflection.java.JavaReflectionManager;


/**
 * @author Paolo Perrotta
 */
public class ANN612IssueTest extends TestCase {


	public static class J<T> {}

	public static class C {
		public J<Object> thisOneAlwaysWorkedFine;
		public J<Object[]> thisOneUsedToCauseProblems;
	}
	
	public void testANN612IssueIsFixed() throws Exception {
		JavaReflectionManager factory = new JavaReflectionManager();
		XClass clazz = factory.toXClass( C.class );
		List<XProperty> properties = clazz.getDeclaredProperties( XClass.ACCESS_FIELD );
		for( XProperty property : properties ) 
			assertTrue( property.isTypeResolved() );
	}
}
