package com.company;

import java.util.List;
import java.util.Set;

public class Photo {
    public int getId() {
        return id;
    }
    public Set<String> getTags() {
        return tags;
    }
    private int id;
    private Set<String> tags;
    private int tagsNumber;
    public Photo(int id, Set<String> tags){
        this.id = id;
        this.tags = tags;
        this.tagsNumber = tags.size();
    }
    public int getTagsSize(){
        return tags.size();
    }
}
