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

    public void greedyApproach(List<Slide> finalList, List<Photo> photoList, List<Photo> verticalList){
        Photo firstVerticalPhoto = new Photo(true,0, new HashSet<String>()); //Will never be necessary
        //Pick first slide
        Photo currentPhoto = photoList.remove(0);
        boolean lastVertical = false;
        if(currentPhoto.isVertical()){
            lastVertical = true;
        }else{
            finalList.add(new Slide(currentPhoto));
        }
        System.out.println("Start greedy:");
        int bestSlideScore = -1;
        int bestPhotoIndex = -1;

        while(!photoList.isEmpty()){
            if(lastVertical){
                //TODO: Buscar verticales (problema: cuando se acaben verticales)

            }else{
                int size = photoList.size();
                int toCompare = min(AMOUNT,size);
                bestSlideScore = -1;
                bestPhotoIndex = -1;
                for(int i = size-1;i>=size-toCompare;i--){
                    int score = currentPhoto.giveScore(photoList.get(i));
                    if(score > bestSlideScore){
                        bestPhotoIndex = i;
                        bestSlideScore = score;
                    }
                }
            }
            currentPhoto = photoList.remove(bestPhotoIndex);
            if(!currentPhoto.isVertical()){
                finalList.add(new Slide(currentPhoto));
            }else if(!lastVertical){
                //1st verticaL
                firstVerticalPhoto = currentPhoto;
                lastVertical = true;
                //TODO: eliminarla de la lista/set de verticales
            }else{
                //2nd vertical
                finalList.add(new Slide(currentPhoto,firstVerticalPhoto));
                lastVertical = false;
                //TODO: eliminarla de la lista/set de verticales
            }
            //Si llevamos una vertical, poner lastVertical() a true para solo buscar verticales

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
        final String[] fileNames = new String[]{"b_lovely_landscapes"};
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
