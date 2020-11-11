package com.udacity.jdnd.course3.critter.entities;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.io.Serializable;

//Since we dont use the User object at all in the system i choose this strategy.
//it fits good because we just use Employee or Customer inheritated objects.
@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue
    private Long id;

    @Nationalized
    private String name;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
