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
        // customers = cdata.getcustomersData();//
        // orders = odata.getordersData();//
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
    
    System.out.println("ــــــــــــــــــــــ");
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

   
   
   
}
