/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.util.impl;

import org.jboss.logging.BasicLogger;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;

import static org.jboss.logging.Logger.Level.ERROR;
import static org.jboss.logging.Logger.Level.INFO;

/**
 * @author Emmanuel Bernard <emmanuel@hibernate.org>
 */
@MessageLogger(projectCode = "HCANN")
public interface Log extends BasicLogger {

	@LogMessage(level = INFO)
	@Message(id = 1, value = "Hibernate Commons Annotations {%1$s}")
	void version(String version);

	@LogMessage(level = ERROR)
	@Message(id = 2, value = "An assertion failure occurred (this may indicate a bug in Hibernate)")
	void assertionFailure(@Cause Throwable t);
}
