package org.hibernate.annotations.common.test.reflection.java;

import org.hibernate.annotations.common.reflection.ClassLoaderDelegate;
import org.hibernate.annotations.common.reflection.ClassLoadingException;
import org.hibernate.annotations.common.reflection.java.JavaReflectionManager;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ClassLoaderDelegateTest extends TestCase {

	public void testClassLoad() {
		try {
			JavaReflectionManager manager = new JavaReflectionManager();
			ClassLoaderDelegate classLoaderDelegate = manager.getClassLoaderDelegate();
			classLoaderDelegate.classForName( "NonExistClass" );
		} catch (Exception e) {
			Assert.assertSame( ClassLoadingException.class, e.getClass() );
			Assert.assertEquals( "Unable to load Class [NonExistClass]", e.getMessage() );
		}
	}
}