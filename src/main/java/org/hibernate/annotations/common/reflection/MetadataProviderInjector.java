/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations.common.reflection;

/**
 * Offers access to and the ability to change the metadata provider
 * 
 * @author Emmanuel Bernard
 */
public interface MetadataProviderInjector {
	MetadataProvider getMetadataProvider();

	/**
	 * Defines the metadata provider for a given Reflection Manager
	 */
	void setMetadataProvider(MetadataProvider metadataProvider);
}
