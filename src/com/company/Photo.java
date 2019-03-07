package com.company;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.min;

public class Photo {
    public boolean isVertical() {
        return isVertical;
    }

    private boolean isVertical;
    private int id;
    private Set<String> tags;
    private int tagsNumber;

    public int getId() {
        return id;
    }

    public Photo(boolean isVertical, int id, Set<String> tags) {
        this.isVertical = isVertical;
        this.id = id;
        this.tags = tags;
        this.tagsNumber = tags.size();
    }

    public int getTagsSize() {
        return tags.size();
    }

    public Set<String> getTags() {
        return this.tags;
    }

}