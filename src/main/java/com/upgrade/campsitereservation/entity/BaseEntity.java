package com.upgrade.campsitereservation.entity;

import javax.persistence.*;

@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    public Integer getId() {
        return id;
    }
}
