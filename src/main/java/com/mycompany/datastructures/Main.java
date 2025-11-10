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
    System.out.println("5. Search product by Name");
    System.out.println("6. Track all out-of-stock products");
    System.out.println("7. Return to main menu");
    System.out.println("Please enter your choice:");

    try {
        choice = input.nextInt();

         while (true) {
            if (choice == 1) {
                pdata.addProduct();
                break;
            } else if (choice == 2) {
                pdata.removeProduct(); // No product will be removed, stock will just be set to zero.
                break;
            } else if (choice == 3) {
                pdata.updateProduct();
                break;
            } else if (choice == 4) {
                Product pro = pdata.searchProducID();
                if (pro != null) {
                    System.out.println("Product found: " + pro);
                } else {
                    System.out.println("No product found with the given ID.");
                }
                break;
            } else if (choice == 5) {
               Product pro = pdata.searchProducName();
                if (pro != null) {
                    System.out.println("Product found: " + pro);
                } else {
                    System.out.println("No product found with the given Name.");
                }
                break;
            }else if (choice == 6) {
                pdata.Out_Of_Stock_Products();
                break;
            } else if (choice == 7) {
                System.out.println("Returning to main menu...");
                return;
            } else {
                System.out.println("Invalid choice! Please select a valid option between 1 and 6.");
                break;
            }
        }
    } catch (java.util.InputMismatchException e) {
        System.out.println("Invalid input! Please enter a valid number.");
        input.nextLine();  
    }
}


//--------------------------
    public static void ReviewsMenu() {
    int choice;

    
    System.out.println("ــــــــــ Review Menu ــــــــ");
    System.out.println("1. Add a new review");
    System.out.println("2. Edit an existing review");
    System.out.println("3. Get the average rating for a product");
    System.out.println("4. Top 3 products");
    System.out.println("5. Common products");
    System.out.println("6. Return to Main menu");
    System.out.print("Enter your choice: ");

    try {
        
        choice = input.nextInt();

        while (true) {
            if (choice == 1) {
                addReviewPrompt();
                break;
            } else if (choice == 2) {
                rdata.updateReview();
                break;
            } else if (choice == 3) {
                System.out.print("Enter product ID to get an average rating: ");
                int pid = input.nextInt();

                while (!pdata.checkProductID(pid)) {
                    System.out.println("The product ID is invalid. Please try again:");
                    pid = input.nextInt();
                }
                float AVG = avgRating(pid);
                System.out.println("The average rating for product ID " + pid + " is: " + AVG);
                break;
            } else if (choice == 4) {
                top3Products();
                break;
            } else if (choice == 5) {
                System.out.print("Enter the first customer's ID: ");
                Customer cid1 = cdata.getCustomerID();
                System.out.print("Enter the second customer's ID: ");
                Customer cid2 = cdata.getCustomerID();
                commonProducts(cid1.getCustomerId(), cid2.getCustomerId());
                break;
            } else if (choice == 6) {
                System.out.println("Returning to Main menu...");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
                break;
            }
        }
    } catch (java.util.InputMismatchException e) {
        System.out.println("Invalid input! Please enter a valid number.");
        input.nextLine();  // Clear the invalid input from the buffer
    }
}

//--------------------------
    public static void addReviewPrompt() {
        System.out.print("Enter the Customer ID: ");
        int customerId = input.nextInt();
        while (!cdata.checkCustomerID(customerId)) {
            System.out.println("Customer ID not available. Please enter again:");
            customerId = input.nextInt();
        }

        System.out.print("Enter the Product ID: ");
        int productId = input.nextInt();
        while (!pdata.checkProductID(productId)) {
            System.out.println("Product ID not available. Please enter again:");
            productId = input.nextInt();
        }

        Review review = rdata.addNewReview(customerId, productId);
        System.out.println("Review (ID: " + review.getReviewId() + ") added successfully for Product " + review.getProduct()
                + " by Customer " + review.getCustomer() + " with Rating: " + review.getRating() + " and Comment: " + review.getComment());
    }

    public static float avgRating(int productId) {
        int reviewCount = 0;
        float totalRating = 0;

        reviews.findFirst();
        while (!reviews.last()) {
            if (reviews.retrieve().getProduct() == productId) {
                reviewCount++;
                totalRating += reviews.retrieve().getRating();
            }
            reviews.findNext();
        }

        if (reviews.retrieve().getProduct() == productId) {
            reviewCount++;
            totalRating += reviews.retrieve().getRating();
        }

        if (reviewCount == 0) {
            System.out.println("No reviews found for Product ID: " + productId);
            return 0;
        }
        return (totalRating / reviewCount);
    }

    public static void top3Products() {
        LinkedPQ<Product> top3 = new LinkedPQ<Product>();

        if (!products.empty()) {
            products.findFirst();
            for (int i = 0; i < products.size(); i++) {
                Product product = products.retrieve();
                float avgRating = avgRating(product.getProductId());
                top3.enqueue(product, avgRating);
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
        LinkedList<Integer> customer1Products = new LinkedList<Integer>();
        LinkedList<Integer> customer2Products = new LinkedList<Integer>();
        
        if (reviews.empty()) {
        System.out.println("No reviews available.");
        return;  
    }
        
       
        reviews.findFirst();
        while (!reviews.last()) {
            if (reviews.retrieve().getCustomer() == cid1) {
                customer1Products.insert(reviews.retrieve().getProduct());
            }
            if (reviews.retrieve().getCustomer() == cid2) {
                customer2Products.insert(reviews.retrieve().getProduct());
            }
            reviews.findNext();
        }

        System.out.println("Customer " + cid1 + " reviewed products: ");
        customer1Products.print();
        System.out.println("Customer " + cid2 + " reviewed products: ");
        customer2Products.print();

        LinkedPQ<Product> commonProducts = new LinkedPQ<Product>();

        customer1Products.findFirst();
        for (int m = 0; m < customer1Products.size(); m++) {
            int productId = customer1Products.retrieve();
            
            customer2Products.findFirst();
            for (int n = 0; n < customer2Products.size(); n++) {
                if (productId == customer2Products.retrieve()) {
                    float avgRating = avgRating(productId);
                    if (avgRating >= 4) {
                        Product p = pdata.getProducts(productId);
                        commonProducts.enqueue(p, avgRating);
                    }
                }
                customer2Products.findNext();
            }
            customer1Products.findNext();
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
