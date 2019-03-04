package com.company;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static sun.swing.MenuItemLayoutHelper.max;

public class Main {
    public static int AMOUNT = 100;
    /**
     * Creates the vertical photos and puts them in verticalPhotos list.
     * Creates the horizontal photos and put them in slides inside slides
     * @param fileName
     * @param verticalPhotos
     */
    public void readInput(String fileName, List<Photo> verticalPhotos, List<Slide> horizontalSlides){
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
                Photo currentPhoto = new Photo(currentId, tags);
                if(splited[0].equals("H")){
                    horizontalSlides.add(new Slide(currentPhoto));
                }else{
                    verticalPhotos.add(currentPhoto);
                }
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
     * For sorting vertical photos list
     */
    class PhotoComparer implements Comparator<Photo>{
        @Override
        public int compare(Photo o1,Photo o2) {
            return o1.getTagsSize() - o2.getTagsSize();
        }
    }

    public void addSlidesFromVerticalPhotos(List<Slide> verticalSlides, List<Photo> verticalPhotos){
        //Sort vertical photos by tags
        Collections.sort(verticalPhotos, new PhotoComparer());
        //Join the photos with the less number of tags with the ones with the most number of tags
        int size = verticalPhotos.size();
        for(int i = 0; i<size/2;i++){
            verticalSlides.add(new Slide(verticalPhotos.get(i),verticalPhotos.get(size-1-i)));
        }
    }

    /**
     * We put first all vertical slides, then the horizontal ones.
     * @param finalList
     * @param list1
     * @param list2
     */
    public void mixSlides(List<Slide> finalList, List<Slide> list1, List<Slide> list2){
        for(Slide slide : list1){
            finalList.add(slide);
        }
        for(Slide slide : list2){
            finalList.add(slide);
        }
    }


    /**
     * For sorting vertical slides list
     */
    class SlideComparer implements Comparator<Slide>{
        @Override
        public int compare(Slide o1,Slide o2) {
            return o1.getTagsSize() - o2.getTagsSize();
        }
    }
    public void greedyApproach(List<Slide> finalList, List<Slide> slides){
        //Order the slides list:
        Collections.sort(slides, new SlideComparer());
        //Pick first slide
        Slide currentSlide = slides.remove(0);
        finalList.add(currentSlide);
        while(!slides.isEmpty()){
            //Compare with the last AMOUNT elements from the list. When there are less than AMOUNT elements, we keep substracting
            int size = slides.size();
            int toCompare = AMOUNT;
            if(toCompare > size) toCompare = size;
            int bestSlideScore = -1;
            int bestSlideIndex = -1;
            for(int i = size-1;i>=size-toCompare;i--){
                if(currentSlide.giveScore(slides.get(i)) > bestSlideScore){
                    bestSlideIndex = i;
                    bestSlideScore = currentSlide.giveScore(slides.get(i));
                }
            }
            //Select the best option as next slide
            currentSlide = slides.remove(bestSlideIndex);
            finalList.add(currentSlide);
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
        final String[] fileNames = new String[]{"a_example","b_lovely_landscapes","c_memorable_moments","d_pet_pictures", "e_shiny_selfies"};
        Main main = new Main();
        for (int i = 0;i<fileNames.length;i++) {
            String fileName = fileNames[i];
            long startTime = System.nanoTime();

            //Load the photos: Verticals as Photo and Horizontals as Slides
            List<Photo> verticalPhotos = new ArrayList<>();
            List<Slide> horizontalSlides = new ArrayList<>();
            main.readInput(System.getProperty("user.dir")+"/"+fileName+".txt", verticalPhotos, horizontalSlides);

            //Create slides from vertical photos:
            List<Slide> verticalSlides = new ArrayList<>();
            main.addSlidesFromVerticalPhotos(verticalSlides, verticalPhotos);


            //Mix the vertical slides with the horizontal ones in one list
            List<Slide> slides = new ArrayList<>();
            main.mixSlides(slides, verticalSlides, horizontalSlides);

            //Greedy approach:
            List<Slide> solutionSlides = new ArrayList<>();
            main.greedyApproach(solutionSlides, slides);

            //Calculate elapsed time:
            long endTime = System.nanoTime();
            long durationInNano = (endTime - startTime);  //Total execution time in nano seconds
            long durationInSeconds = TimeUnit.NANOSECONDS.toSeconds(durationInNano);
            totalTimes[i] = durationInSeconds;
            totalTime += durationInSeconds;
            System.out.println(durationInSeconds);
            //Output solution:
            main.outputSolution(solutionSlides, System.getProperty("user.dir")+"/"+fileName+"_sol_" + AMOUNT +"_"+durationInSeconds +".txt");
        }
        System.out.println("Total time: " + totalTime);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(System.getProperty("user.dir")+"/TimesLog/Times_with_AMOUNT="+AMOUNT+".txt", "UTF-8");
            writer.println("AMOUNT = " + AMOUNT);
            for(int i = 0;i<fileNames.length;i++){
                writer.println(fileNames[i] + ": " + totalTimes[i] + "s");
            }
            writer.println("Total time: " + totalTime +"s");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


}
