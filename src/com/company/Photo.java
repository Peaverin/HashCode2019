package com.company;

import java.util.List;

public class Photo {
    public int getId() {
        return id;
    }
    public List<String> getTags() {
        return tags;
    }
    private int id;
    private List<String> tags;
    private int tagsNumber;
    public Photo(int id, List<String> tags){
        this.id = id;
        this.tags = tags;
        this.tagsNumber = tags.size();
    }
    public int getTagsSize(){
        return tags.size();
    }
}
