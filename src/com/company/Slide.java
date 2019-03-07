package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Math.min;

public class Slide {
    public boolean isVertical() {
        return isVertical;
    }

    private boolean isVertical;
    private int id0;

    public int getId0() {
        return id0;
    }

    public int getId1() {
        return id1;
    }

    private int id1;
    private Set<String> tags;
    public Slide(Photo photo){
        isVertical = false;
        this.id0 = photo.getId();
        tags = photo.getTags();
    }
    public Slide(Photo photo1, Photo photo2){
        isVertical = true;
        this.id0 = photo1.getId();
        this.id1 = photo2.getId();
        tags = new HashSet<>(photo1.getTags());
        tags.addAll(photo2.getTags());
    }
    public int getTagsSize(){
        return tags.size();
    }
    public Set<String>getTags(){
        return tags;
    }
}