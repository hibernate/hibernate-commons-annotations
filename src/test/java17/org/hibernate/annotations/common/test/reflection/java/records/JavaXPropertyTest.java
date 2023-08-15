/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.test.reflection.java.records;

import junit.framework.TestCase;
import org.hibernate.annotations.common.reflection.ReflectionManager;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.annotations.common.reflection.java.JavaReflectionManager;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Christian Beikov
 */
public class JavaXPropertyTest extends TestCase {

	private ReflectionManager factory = new JavaReflectionManager();

	private XClass recordXclass = factory.toXClass( TestRecord.class );

	public void testFollowsJavaBeansConventionsForPropertyNames() throws Exception {
		List<String> properties = new LinkedList<String>();
		properties.add( "id" );
		properties.add( "name" );
		List<XProperty> methodProperties = recordXclass.getDeclaredProperties( XClass.ACCESS_RECORD );
		assertEquals( properties.size(), methodProperties.size() );
		for ( XProperty member : methodProperties ) {
			assertTrue( properties.contains( member.getName() ) );
		}
	}

	public static record TestRecord(Long id, String name){}
}
