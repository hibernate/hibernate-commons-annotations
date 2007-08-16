package org.hibernate.annotations.common.test.reflection.java.generics.deep;

import javax.persistence.MappedSuperclass;
import javax.persistence.Id;

/**
 *
 */
@MappedSuperclass
public class Dummy {
    @Id
    protected Long id;

    private String name;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
