/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.annotations.common.reflection.java;

import java.util.HashMap;
import java.util.Objects;
import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironment;

/**
 * We need a two-level sparse tree for best efficiency in this case;
 * the first split uses TypeEnvironment as a key, under which we store
 * an additional Map to further organize by K.
 * This used to be implemented as a HashMap with a composite key {TypeEnvironment,K},
 * but this wasn't memory efficient as all composite keys take too much space:
 * the nested structure has some overhead as well, but has been shown to cost less
 * overall.
 * In addition, we strife to not need to compute hashkeys multiple times while
 * not needing to allocate stateful lambdas; for this reason the tree nodes
 * need to store some additional references.
 *
 * @param <K>
 * @param <V>
 * @author Sanne Grinovero
 */
final class TypeEnvironmentMap<K,V> {

	//First level is optimised for fast access; it's expected to be small so a low load factor
	//should be fine in terms of memory costs.
	private HashMap<TypeEnvironment, ContextScope> rootMap;
	private final XTypeConstruction<K,V> constructionMethod;

	TypeEnvironmentMap(final XTypeConstruction<K, V> constructionMethod) {
		Objects.requireNonNull( constructionMethod );
		this.constructionMethod = constructionMethod;
	}

	private <K, V> HashMap<TypeEnvironment, ContextScope> getOrInitRootMap() {
		if ( this.rootMap == null ) {
			this.rootMap = new HashMap<>( 8, 0.5f );
		}
		return this.rootMap;
	}

	V getOrCompute(final TypeEnvironment context, final K subKey) {
		final ContextScope contextualMap = getOrInitRootMap().computeIfAbsent( context, ContextScope::new );
		return contextualMap.getOrCompute( subKey );
	}

	void clear() {
		final HashMap<TypeEnvironment, ContextScope> m = this.rootMap;
		if ( m != null ) {
			// Remove the reference to the rootMap, as very large arrays within HashMap
			// are not resized when clearing.
			this.rootMap = null;
			m.clear();
		}
	}

	private final class ContextScope extends HashMap<K,V> {

		private final TypeEnvironment context;

		private ContextScope(TypeEnvironment context) {
			//In the second level of the tree we expect many entries, but we can't have it consume too much memory:
			super( 64, 0.85f );
			this.context = context;
		}

		private V getOrCompute(K subKey) {
			return computeIfAbsent( subKey, this::buildObject );
		}

		private V buildObject(K subKey) {
			return constructionMethod.createInstance( subKey, context );
		}
	}
}
