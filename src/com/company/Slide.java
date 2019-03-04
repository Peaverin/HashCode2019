package com.company;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> tags;
    public Slide(Photo photo){
        isVertical = false;
        this.id0 = photo.getId();
        tags = photo.getTags();
    }
    public Slide(Photo photo1, Photo photo2){
        isVertical = true;
        this.id0 = photo1.getId();
        this.id1 = photo2.getId();
        tags = new ArrayList<>(photo1.getTags());
        for(String tag : photo2.getTags()){
            if(!tags.contains(tag)){
                tags.add(tag);
            }
        }
    }
    public int getTagsSize(){
        return tags.size();
    }

    public int giveScore(Slide slide){
        //Intersection:
        int intersection = 0;
        //S2-S1
        int subs2 = getTagsSize();
        for(String tag : tags){
            if(slide.tags.contains(tag)){
                intersection++;
                subs2--;
            }

        }
        //S1-S2
        int subs1 = getTagsSize() - intersection;
        //Get the minimum
        return min(min(intersection, subs1),subs2);
    }

}
