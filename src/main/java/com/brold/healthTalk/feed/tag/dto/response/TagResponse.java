package com.brold.healthTalk.feed.tag.dto.response;

public class TagResponse {
    private Integer id;
    private String  name;

    public TagResponse(Integer id, String name) {
        this.id   = id;
        this.name = name;
    }

    public Integer getId() { return id; }
    public String  getName() { return name; }
}
