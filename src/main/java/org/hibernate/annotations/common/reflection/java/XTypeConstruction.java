package org.hibernate.annotations.common.reflection.java;

import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironment;

@FunctionalInterface
interface XTypeConstruction<K,V> {

	V createInstance(K typeKey, TypeEnvironment context);

}
