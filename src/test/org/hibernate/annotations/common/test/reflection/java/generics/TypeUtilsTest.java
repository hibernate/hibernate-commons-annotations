package org.hibernate.annotations.common.test.reflection.java.generics;

import java.lang.reflect.Type;

import junit.framework.TestCase;
import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironment;
import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironmentFactory;
import org.hibernate.annotations.common.reflection.java.generics.TypeUtils;

public class TypeUtilsTest extends TestCase {

	TypeEnvironmentFactory env = new TypeEnvironmentFactory();
	TypeEnvironment dadContext = env.getEnvironment( Dad.class );
	TypeEnvironment sonContext = env.getEnvironment( Son.class );

	public void testAClassIsAlwaysFullyResolved() throws Exception {
		assertTrue( TypeUtils.isResolved( Dad.class ) );
	}

	public void testKnowsIfAParametricTypeIsFullyResolved() throws Exception {
		Type simpleType = Dad.class.getMethod( "returnsGeneric", new Class[0] ).getGenericReturnType();
		assertFalse( TypeUtils.isResolved( dadContext.bind( simpleType ) ) );
		assertTrue( TypeUtils.isResolved( sonContext.bind( simpleType ) ) );
	}

	public void testKnowsIfAnArrayTypeIsFullyResolved() throws Exception {
		Type arrayType = Dad.class.getMethod( "getArrayProperty", new Class[0] ).getGenericReturnType();
		assertFalse( TypeUtils.isResolved( dadContext.bind( arrayType ) ) );
		assertTrue( TypeUtils.isResolved( sonContext.bind( arrayType ) ) );
	}
}
