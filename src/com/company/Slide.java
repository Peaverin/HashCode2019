package com.company;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.min;

public class Slide {

    private boolean isVertical;
    private int id0;
    private int id1;

    public int getId0() {
        return id0;
    }

    public int getId1() {
        return id1;
    }

    public boolean isVertical() {
        return isVertical;
    }

    private Set<String> tags;
    public Slide(Photo photo){
        isVertical = false;
        this.id0 = photo.getId();
    }
    public Slide(Photo photo1, Photo photo2){
        isVertical = true;
        this.id0 = photo1.getId();
        this.id1 = photo2.getId();
    }

}