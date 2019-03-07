package com.company;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.min;

public class Main {

    public static int AMOUNT = 100;
//
    /**
     * Creates the list with all vertical and horizontal photos
     * @param fileName
     * @param photoList
     */
    public void readInput(String fileName, List<Photo> photoList, List<Photo> verticalList){
        BufferedReader br = null;
        FileReader fr = null;

        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            String sCurrentLine;
            int size = 0;
            sCurrentLine = br.readLine(); //first line is size, we don't need it
            String[] splited;
            int tagsNumber;
            Set<String> tags;
            int currentId = 0;
            while ((sCurrentLine = br.readLine()) != null) {
                splited = sCurrentLine.split("\\s+"); //split by spaces
                tagsNumber = Integer.parseInt(splited[1]);
                tags = new HashSet<>();
                for(int i = 2; i<2+tagsNumber;i++){
                    tags.add(splited[i]);
                }
                Photo currentPhoto = new Photo(splited[0].equals("V"), currentId, tags);
                if(splited[0].equals("V")){
                    //TODO: lista verticales???
                    verticalList.add(currentPhoto);
                }
                photoList.add(currentPhoto);
                currentId++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * For sorting vertical slides list
     */
    class PhotoComparer implements Comparator<Photo>{
        @Override
        public int compare(Photo o1, Photo o2) {
            return o1.getTagsSize() - o2.getTagsSize();
        }
    }

    private int giveScore(Slide slide, Photo photo){
        Set<String> intersectionSet = new HashSet<>(slide.getTags());
        intersectionSet.retainAll(photo.getTags());
        int intersection = intersectionSet.size();
        int subs1 = slide.getTagsSize() - intersection;
        int subs2 = photo.getTagsSize() - intersection;
        return min(min(intersection, subs1),subs2);
    }

    private int giveScore(Slide slide, Photo vertical1, Photo vertical2){
        Set<String> verticalTags = new HashSet<>(vertical1.getTags());
        verticalTags.addAll(vertical2.getTags());
        Set<String> intersectionSet = new HashSet<>(slide.getTags());
        intersectionSet.retainAll(verticalTags);
        int intersection = intersectionSet.size();
        int subs1 = slide.getTagsSize() - intersection;
        int subs2 = verticalTags.size() - intersection;
        return min(min(intersection, subs1),subs2);
    }

    public void greedyApproach(List<Slide> finalList, List<Photo> photoList, List<Photo> verticalList){
        Photo firstVerticalPhoto = null;
        Photo currentPhoto = photoList.remove(0);//Pick first photo
        Slide lastSlide = null;
        boolean lastVertical = false; //last photo picked is vertical?
        if(currentPhoto.isVertical()){ //check if picked photo is vertical. if it isn't we add the horizontal to the solution
            firstVerticalPhoto = currentPhoto;
            verticalList.remove(currentPhoto);
            currentPhoto = verticalList.remove(0);
            photoList.remove(currentPhoto);
            lastSlide = new Slide(firstVerticalPhoto, currentPhoto);
            finalList.add(lastSlide);
        }else{
            lastSlide = new Slide(currentPhoto);
            finalList.add(lastSlide);
        }
        System.out.println("Start greedy:");
        int size = 0;
        int toCompare = 0;
        int bestPhotoScore = -1;
        int bestPhotoIndex = -1;
        while(!photoList.isEmpty()){
            if(lastVertical){//We search for the second vertical:
                size = verticalList.size();
                toCompare = min(AMOUNT, size);
                bestPhotoScore = -1;
                bestPhotoIndex = -1;
                for(int i = size-1;i>=size-toCompare;i--){
                    int score = giveScore(lastSlide,firstVerticalPhoto,verticalList.get(i));
                    if(score > bestPhotoScore){
                        bestPhotoIndex = i;
                        bestPhotoScore = score;
                    }
                }
                currentPhoto = verticalList.remove(bestPhotoIndex);
                photoList.remove(currentPhoto);
                //2nd vertical
                lastSlide = new Slide(firstVerticalPhoto,currentPhoto);
                finalList.add(lastSlide);
                lastVertical = false;
            }else{
                size = photoList.size();
                toCompare = min(AMOUNT,size);
                bestPhotoScore = -1;
                bestPhotoIndex = -1;
                for(int i = size-1;i>=size-toCompare;i--){
                    int score = giveScore(lastSlide, photoList.get(i));
                    if(score > bestPhotoScore){
                        bestPhotoIndex = i;
                        bestPhotoScore = score;
                    }
                }
                currentPhoto = photoList.remove(bestPhotoIndex);
                if(currentPhoto.isVertical()){
                    firstVerticalPhoto = currentPhoto;
                    verticalList.remove(currentPhoto);
                    lastVertical = true;
                }else{
                    //Add to solution
                    finalList.add(new Slide(currentPhoto));
                }
            }

        }
    }

    public void outputSolution(List<Slide> solutionSlides, String fileName){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
            writer.println(solutionSlides.size());
            for(Slide slide : solutionSlides){
                if(!slide.isVertical()){
                    writer.println(slide.getId0());
                }else{
                    writer.println(slide.getId0() + " " + slide.getId1());
                }

            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        long totalTime = 0;
        long[] totalTimes = new long[5];
        int[] scores = new int[5];
        final String[] fileNames = new String[]{"c_memorable_moments"};
        Main main = new Main();
        for (int i = 0; i < fileNames.length; i++) {
            String fileName = fileNames[i];
            long startTime = System.nanoTime();

            //Load the photos
            List<Photo> photoList = new ArrayList<>();
            List<Photo> verticalList = new ArrayList<>();
            main.readInput(System.getProperty("user.dir") + "/" + fileName + ".txt", photoList, verticalList);

            //Greedy approach:
            List<Slide> solutionSlides = new ArrayList<>();
            main.greedyApproach(solutionSlides, photoList, verticalList);

            //Calculate elapsed time:
            long endTime = System.nanoTime();
            long durationInNano = (endTime - startTime);  //Total execution time in nano seconds
            long durationInMilli = TimeUnit.NANOSECONDS.toSeconds(durationInNano);
            totalTimes[i] = durationInMilli;
            totalTime += durationInMilli;

            /*
            //Calculate score:
            int score = 0;
            for (int j = 0; j < solutionSlides.size() - 1; j++) {
                score += solutionSlides.get(j).giveScore(solutionSlides.get(j + 1));
            }
            scores[i] = score;
            //Output solution:
            main.outputSolution(solutionSlides, System.getProperty("user.dir") + "/" + fileName + "_sol_" + AMOUNT + "_" + durationInMilli + "NEW_ORDER.txt");

            //Print time and score:
            System.out.println(fileName + ": " + durationInMilli + "s | Score: " + score);
            */

            //Output solution:
            main.outputSolution(solutionSlides, System.getProperty("user.dir") + "/" + fileName + "_sol_" + AMOUNT + "_" + durationInMilli + ".txt");

            //Print time and score:
            System.out.println(fileName + ": " + durationInMilli);
        }
        /*
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(System.getProperty("user.dir") + "/TimesLog/Times_with_AMOUNT=" + AMOUNT + ".txt", "UTF-8");
            writer.println("AMOUNT = " + AMOUNT);
            int totalScore = 0;
            for (int i = 0; i < fileNames.length; i++) {
                writer.println(fileNames[i] + ": " + totalTimes[i] + "s | Score: " + scores[i]);
                totalScore += scores[i];
            }
            writer.println("Total time: " + totalTime + "s | Total Score: " + totalScore);
            writer.close();
            System.out.println("Total time: " + totalTime + "s | Total Score: " + totalScore);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        */
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(System.getProperty("user.dir") + "/TimesLog/Times_with_AMOUNT=" + AMOUNT + ".txt", "UTF-8");
            writer.println("AMOUNT = " + AMOUNT);
            for (int i = 0; i < fileNames.length; i++) {
                writer.println(fileNames[i] + ": " + totalTimes[i]);
            }
            writer.println("Total time: " + totalTime);
            writer.close();
            System.out.println("Total time: " + totalTime);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
