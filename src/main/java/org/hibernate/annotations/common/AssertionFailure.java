/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common;

import org.hibernate.annotations.common.util.impl.Log;
import org.hibernate.annotations.common.util.impl.LoggerFactory;


/**
 * Indicates failure of an assertion: a possible bug in Hibernate.
 *
 * @author Gavin King
 * @auhor Emmanuel Bernard
 * @deprecated This is an old class which will be removed; this project no longer uses it. No specific alternative is recommended.
 */
@Deprecated
public class AssertionFailure extends RuntimeException {

	private static final Log log = LoggerFactory.make( AssertionFailure.class.getName() );

	public AssertionFailure(String s) {
		super(s);
		log.assertionFailure( this );
	}

	public AssertionFailure(String s, Throwable t) {
		super(s, t);
		log.assertionFailure( this );
	}
}
