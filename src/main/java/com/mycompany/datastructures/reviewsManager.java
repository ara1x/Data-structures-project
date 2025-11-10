package com.mycompany.datastructures;

import java.io.File;
import java.util.Scanner;

public class reviewsManager {

    public static Scanner input = new Scanner(System.in);
    public static LinkedList<Review> reviews = new LinkedList<Review> ();

    //==============================================================
    public LinkedList<Review> getAllReviews() {
        return reviews;
    }

    //==============================================================
    public reviewsManager(String fileName) {
        try {
            File file = new File(fileName);
            Scanner read = new Scanner(file);
            String line = read.nextLine();

           
            

              while(read.hasNext())
                {
                    line = read.nextLine();
                    String [] data = line.split(","); 
                    int rID = Integer.parseInt(data[0]);
                    int pID = Integer.parseInt(data[1]);
                    int cID = Integer.parseInt(data[2]);
                    int rating =  Integer.parseInt(data[3]);
                    String comment =  data[4];

                
                Review newReview = new Review(rID, pID, cID, rating, comment);
                reviews.insert(newReview);
            }
            read.close();

        } catch (Exception ex) {
            System.out.println("Error loading reviews: " + ex.getMessage());
        }
    }

    //==============================================================
    public Review addNewReview(int customerNum, int productNum) {
        System.out.println("Enter a new Review ID:");
        int newID = input.nextInt();

        while (checkReviewID(newID)) {
            System.out.println("This ID already exists. Try another one:");
            newID = input.nextInt();
        }

        System.out.println("Rate the product (1 to 5):");
        int rate = input.nextInt(); 

        System.out.println("Write your comment:");
        input.nextLine(); 
        String comment = input.nextLine();

        
        Review r = new Review(newID, productNum, customerNum, rate, comment);

        reviews.findFirst();
        reviews.insert(r);

        System.out.println("Review added successfully!");
        return r;
    }

    //==============================================================
    public void updateReview() {
        if (reviews.empty()) {
            System.out.println("No reviews available to edit.");
            return;
        }

        System.out.println("Enter the Review ID you want to modify:");
        int targetID = input.nextInt();

        while (!checkReviewID(targetID)) {
            System.out.println("Review not found! Enter a valid ID:");
            targetID = input.nextInt();
        }

        reviews.findFirst();
        while (!reviews.last()) {
            if (reviews.retrieve().getReviewId() == targetID) break;
            reviews.findNext();
        }

        if (reviews.retrieve().getReviewId() == targetID) {
            Review chosen = reviews.retrieve();
            reviews.remove();

            System.out.println("Choose what you want to update:");
            System.out.println("1. Change Rating");
            System.out.println("2. Edit Comment");
            int option = input.nextInt();

            switch (option) {
                case 1: {
                    System.out.println("Enter a new rating (1 to 5):");
                    int newRating = input.nextInt();   
                    chosen.setRating(newRating);       
                } break;

                case 2: {
                    System.out.println("Enter your new comment:");
                    input.nextLine();
                    String newComment = input.nextLine();
                    chosen.setComment(newComment);
                } break;

                default:
                    System.out.println("Invalid choice! No changes applied.");
            }

            reviews.insert(chosen);
            System.out.println("Review (" + chosen.getReviewId() + ") updated successfully:");
            System.out.println(chosen);
        }
    }

    //==============================================================
    public boolean checkReviewID(int id) {
        if (!reviews.empty()) {
            reviews.findFirst();
            for (int i = 0; i < reviews.size(); i++) {
                if (reviews.retrieve().getReviewId() == id)
                    return true;
                reviews.findNext();
            }
        }
        return false;
    }
}