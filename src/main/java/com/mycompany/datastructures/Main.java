/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.datastructures;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    public static Scanner input = new Scanner(System.in);

    public static productsManager pdata = new productsManager("prodcuts.csv");
    public static LinkedList<Product> products;

    public static reviewsManager rdata = new reviewsManager("reviews.csv");
    public static LinkedList<Review> reviews;

    // read data from files for all the 4 data structures
    public static void loadData() {
        System.out.println("loading data from CVSs files");
        products = pdata.getProducts();
        //customers = cdata.getcustomersData();//
        //orders = odata.getordersData();//
        reviews = rdata.getAllReviews();

        // add reviews to products
        if (!products.empty() && !reviews.empty()) {
            products.findFirst();
            for (int i = 0; i < products.size(); i++) {

                reviews.findFirst();
                for (int j = 0; j < reviews.size(); j++) {

                    if (products.retrieve().getProductId() == reviews.retrieve().getProduct()) {

                        int rid = reviews.retrieve().getReviewId();
                        products.retrieve().addReview(rid);
                    }

                    reviews.findNext();
                }

                products.findNext();
            }
        } else {
            System.out.println("No products or reviews available.");
        }

    }

    //--------------------------
    public static int mainMenu() {
        System.out.println("ـــــــــــــ");
        System.out.println("1. Products");
        System.out.println("2. Customers");
        System.out.println("3. Orders");
        System.out.println("4. Reviews");
        System.out.println("5. Exit");
        System.out.println("Enter your choice:");

        return input.nextInt();
    }
//--------------------------

    public static void productsMenu() {
        int choice;

        System.out.println("ــــــــــProducts Menu ــــــــ");
        System.out.println("1. Add a new product");
        System.out.println("2. Remove a product");
        System.out.println("3. Update product");
        System.out.println("4. Search product by ID");
        System.out.println("5. Track all out-of-stock products");
        System.out.println("6. Return to main menu");
        System.out.println("Please enter your choice:");

        choice = input.nextInt();

        switch (choice) {
            case 1:
                pdata.addProduct();
                break;
            case 2:
                pdata.removeProduct();//No product will be removed, stock will just be set to zero.
                break;
            case 3:
                pdata.updateProduct();
                break;
            case 4:
                Product pro = pdata.searchProducID();
                if (pro != null) {
                    System.out.println("Product found: " + pro);
                } else {
                    System.out.println("No product found with the given ID.");
                }
                break;
            case 5:
                pdata.Out_Of_Stock_Products();
                break;
            case 6:
                System.out.println("Returning to main menu...");
                return;
            default:

                System.out.println("Invalid choice! Please select a valid option between 1 and 6.");
        }
    }

//--------------------------
    public static void ReviewsMenu() {
        System.out.println("ــــــــــ Review Menu ــــــــ");
        System.out.println("1. Add a review");
        System.out.println("2. Edit a review (rating, comment)");
        System.out.println("3. Get the average rating for a product");
        System.out.println("4. Top 3 products by average rating");
        System.out.println("5. Common products with an average rating of 4 or more between two customers");
        System.out.println("6. Return to Main menu");
        System.out.print("Enter your choice: ");

        int choice = input.nextInt();

        switch (choice) {
            case 1:
                addReviewPrompt();
                break;
            case 2:
                rdata.updateReview();
                break;
            case 3:
                System.out.print("Enter product ID to get an average rating: ");
                int pid = input.nextInt();

                while (!pdata.checkProductID(pid)) {
                    System.out.println("Product ID is not available. Please re-enter a valid product ID:");
                    pid = input.nextInt();
                }
                float AVG = avgRating(pid);
                System.out.println("The average rating for product ID " + pid + " is: " + AVG);
                break;
            case 4:
                top3Products();
                break;
            case 5:
                System.out.println("Enter Customer 1 ID:");
                Customer cid1 = cdata.getCustomerID();
                System.out.println("Enter Customer 2 ID:");
                Customer cid2 = cdata.getCustomerID();
                commonProducts(cid1.getCustomerId(), cid2.getCustomerId());
                break;
            case 6:
                System.out.println("Returning to Main menu...");
                break;
            default:
                System.out.println("Invalid choice, please return to the main menu.");
        }
    }

//--------------------------
    public static void addReviewPrompt() {
        System.out.println("Enter customer ID:");
        int cID = input.nextInt();
        while (!cdata.checkCustomerID(cID)) {
            System.out.println("Customer ID not available. Please enter again:");
            cID = input.nextInt();
        }

        System.out.println("Enter product ID:");
        int pID = input.nextInt();
        while (!pdata.checkProductID(pID)) {
            System.out.println("Product ID not available. Please enter again:");
            pID = input.nextInt();
        }

        Review review = rdata.addNewReview(cID, pID);
        System.out.println("Review (ID: " + review.getReviewId() + ") added successfully for Product " + review.getProduct()
                + " by Customer " + review.getCustomer() + " with Rating: " + review.getRating() + " and Comment: " + review.getComment());
    }

    public static float avgRating(int pid) {
        int counter = 0;
        float rate = 0;

        reviews.findFirst();
        while (!reviews.last()) {
            if (reviews.retrieve().getProduct() == pid) {
                counter++;
                rate += reviews.retrieve().getRating();
            }
            reviews.findNext();
        }

        if (reviews.retrieve().getProduct() == pid) {
            counter++;
            rate += reviews.retrieve().getRating();
        }

        if (counter == 0) {
            System.out.println("No reviews found for Product ID: " + pid);
            return 0;
        }
        return (rate / counter);
    }

    public static void top3Products() {
        LinkedPQ<Product> top3 = new LinkedPQ<Product>();

        if (!products.empty()) {
            products.findFirst();
            for (int i = 0; i < products.size(); i++) {
                Product p = products.retrieve();
                float avgRating = avgRating(p.getProductId());
                top3.enqueue(p, avgRating);
                products.findNext();
            }
        }

        System.out.println("Top 3 products by average rating:");
        for (int j = 1; j <= 3 && top3.length() > 0; j++) {
            PQElement<Product> top = top3.serve();
            System.out.println("Product " + j + " - ID: " + top.data.getProductId() + " | " + top.data.getName()
                    + " | Avg Rating: " + top.priority);
        }
    }

    public static void commonProducts(int cid1, int cid2) {
        LinkedList<Integer> pcustomer1 = new LinkedList<Integer>();
        LinkedList<Integer> pcustomer2 = new LinkedList<Integer>();

        reviews.findFirst();
        while (!reviews.last()) {
            if (reviews.retrieve().getCustomer() == cid1) {
                pcustomer1.insert(reviews.retrieve().getProduct());
            }
            if (reviews.retrieve().getCustomer() == cid2) {
                pcustomer2.insert(reviews.retrieve().getProduct());
            }
            reviews.findNext();
        }

        System.out.println("Customer " + cid1 + " reviewed products: ");
        pcustomer1.print();
        System.out.println("Customer " + cid2 + " reviewed products: ");
        pcustomer2.print();

        LinkedPQ<Product> commonProducts = new LinkedPQ<Product>();

        pcustomer1.findFirst();
        for (int m = 0; m < pcustomer1.size(); m++) {
            int productId = pcustomer1.retrieve();
            pcustomer2.findFirst();
            for (int n = 0; n < pcustomer2.size(); n++) {
                if (productId == pcustomer2.retrieve()) {
                    float avgRating = avgRating(productId);
                    if (avgRating >= 4) {
                        Product p = pdata.getProducts(productId);
                        commonProducts.enqueue(p, avgRating);
                    }
                }
                pcustomer2.findNext();
            }
            pcustomer1.findNext();
        }

        if (commonProducts.length() > 0) {
            System.out.println("Common products with rating > 4:");
            while (commonProducts.length() > 0) {
                PQElement<Product> product = commonProducts.serve();
                System.out.println("Product ID: " + product.data.getProductId() + " | " + product.data.getName()
                        + " | Avg Rating: " + product.priority);
            }
        } else {
            System.out.println("No common products with a rating greater than 4 between the two customers.");
        }
    }

}
