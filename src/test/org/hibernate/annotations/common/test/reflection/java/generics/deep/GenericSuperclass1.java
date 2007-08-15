package org.hibernate.annotations.common.test.reflection.java.generics.deep;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Id;

import org.hibernate.annotations.common.test.reflection.java.generics.deep.Dummy;

/**
 *
 */
@MappedSuperclass
public class GenericSuperclass1<T extends Dummy> {
	@Id
	protected Long id;

	@OneToOne
	protected T dummy;


	public Long getId() {
		return id;
	}

	public T getDummy() {
		return dummy;
	}
}
