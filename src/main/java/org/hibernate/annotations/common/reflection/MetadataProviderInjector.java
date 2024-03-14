/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Red Hat Inc. and Hibernate Authors
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
