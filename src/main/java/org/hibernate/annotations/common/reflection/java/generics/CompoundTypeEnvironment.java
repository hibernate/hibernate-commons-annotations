/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.reflection.java.generics;

import java.lang.reflect.Type;

/**
 * A composition of two <code>TypeEnvironment</code> functions.
 *
 * @author Davide Marchignoli
 * @author Paolo Perrotta
 */
public final class CompoundTypeEnvironment implements TypeEnvironment {

	private final TypeEnvironment f;
	private final TypeEnvironment g;
    private final int hashCode;

    public static TypeEnvironment create(TypeEnvironment f, TypeEnvironment g) {
        if ( g == IdentityTypeEnvironment.INSTANCE )
            return f;
        if ( f == IdentityTypeEnvironment.INSTANCE )
            return g;
        return new CompoundTypeEnvironment( f, g );
    }
    
    private CompoundTypeEnvironment(TypeEnvironment f, TypeEnvironment g) {
		this.f = f;
		this.g = g;
        this.hashCode = doHashCode( f, g );
    }

	public Type bind(Type type) {
		return f.bind( g.bind( type ) );
	}

	public boolean equals(Object o) {
		if ( this == o ) return true;
		if ( ! ( o instanceof CompoundTypeEnvironment ) ) return false;

		final CompoundTypeEnvironment that = (CompoundTypeEnvironment) o;

        if ( differentHashCode( that ) ) return false;

        if ( !f.equals( that.f ) ) return false;
        return g.equals( that.g );
    }

    private boolean differentHashCode(CompoundTypeEnvironment that) {
        return hashCode != that.hashCode;
    }

    private static int doHashCode(
			TypeEnvironment f,
			TypeEnvironment g) {
		int result;
		result = f.hashCode();
		result = 29 * result + g.hashCode();
		return result;
	}

    public int hashCode() {
        //cached because the inheritance can be big
        return hashCode;
    }
    
    @Override
    public String toString() {
        return f.toString() + "(" + g.toString() + ")";
    }
}
