package com.brold.healthTalk.feed.tag.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "tag_definitions")
public class Tag {
    @Id
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    protected Tag() {}

    public Tag(Integer id, String name) {
        this.id   = id;
        this.name = name;
    }

    public Integer getId() { return id; }
    public String  getName() { return name; }
}
