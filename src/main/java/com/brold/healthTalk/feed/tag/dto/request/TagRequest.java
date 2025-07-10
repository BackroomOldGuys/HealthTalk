package com.brold.healthTalk.feed.tag.dto.request;

public class TagRequest {
    private Integer tagId;

    public TagRequest() {}
    public TagRequest(Integer tagId) { this.tagId = tagId; }

    public Integer getTagId() { return tagId; }
}
