/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

package org.hibernate.annotations.common.reflection.java.generics;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.Objects;

public class GenericArrayTypeImpl implements GenericArrayType {

    private final Type componentType;

    public GenericArrayTypeImpl(Type componentType) {
        this.componentType = componentType;
    }

    public Type getGenericComponentType() {
        return componentType;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GenericArrayType)) {
            return false;
        }
        GenericArrayType other = (GenericArrayType) obj;
        return Objects.equals(getGenericComponentType(), other.getGenericComponentType());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getGenericComponentType());
    }
}
