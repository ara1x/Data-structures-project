/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.datastructures;

import java.util.Scanner;

public class Main {
    
    private static Scanner input = new Scanner(System.in);
    private static ordersManager ordersMgr;
    private static productsManager productsMgr;
    private static reviewsManager reviewsMgr;
    private static CustomerManger customersMgr;
    
    public static void main(String[] args) {
        
        System.out.println("========================================");
        System.out.println("  E-Commerce Management System");
        System.out.println("========================================\n");
        
        // Load data from CSV files
        loadData();
        
        // Main menu loop
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = input.nextInt();
            
            switch (choice) {
                case 1:
                    productMenu();
                    break;
                case 2:
                    customerMenu();
                    break;
                case 3:
                    orderMenu();
                    break;
                case 4:
                    reviewMenu();
                    break;
                case 5:
                    queriesMenu();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
    
    // ===== DATA LOADING =====
    private static void loadData() {
        System.out.println("Loading data from CSV files...");
        
        try {
            productsMgr = new productsManager("products.csv");
            System.out.println("✓ Products loaded successfully");
        } catch (Exception e) {
            System.out.println("✗ Error loading products: " + e.getMessage());
        }
        
        try {
            customersMgr = new CustomerManger("customers.csv");
            System.out.println("✓ Customers loaded successfully");
        } catch (Exception e) {
            System.out.println("✗ Error loading customers: " + e.getMessage());
        }
        
        try {
            ordersMgr = new ordersManager("orders.csv");
            System.out.println("✓ Orders loaded successfully");
        } catch (Exception e) {
            System.out.println("✗ Error loading orders: " + e.getMessage());
        }
        
        try {
            reviewsMgr = new reviewsManager("reviews.csv");
            System.out.println("✓ Reviews loaded successfully");
        } catch (Exception e) {
            System.out.println("✗ Error loading reviews: " + e.getMessage());
        }
        
        System.out.println("\nData loading complete!\n");
    }
    
    // ===== MAIN MENU =====
    private static void displayMainMenu() {
        System.out.println("\n========================================");
        System.out.println("           MAIN MENU");
        System.out.println("========================================");
        System.out.println("1. Product Management");
        System.out.println("2. Customer Management");
        System.out.println("3. Order Management");
        System.out.println("4. Review Management");
        System.out.println("5. Queries & Reports");
        System.out.println("0. Exit");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");
    }
    
    // ===== PRODUCT MENU =====
    private static void productMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- PRODUCT MANAGEMENT ---");
            System.out.println("1. Add New Product");
            System.out.println("2. Search Product by ID");
            System.out.println("3. Update Product");
            System.out.println("4. Remove Product");
            System.out.println("5. Show Out-of-Stock Products");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = input.nextInt();
            
            switch (choice) {
                case 1:
                    productsMgr.addProduct();
                    break;
                case 2:
                    Product p = productsMgr.searchProducID();
                    if (p != null)
                        System.out.println(p);
                    break;
                case 3:
                    productsMgr.updateProduct();
                    break;
                case 4:
                    productsMgr.removeProduct();
                    break;
                case 5:
                    productsMgr.Out_Of_Stock_Products();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    // ===== CUSTOMER MENU =====
    private static void customerMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- CUSTOMER MANAGEMENT ---");
            System.out.println("1. Register New Customer");
            System.out.println("2. View Customer Order History");
            System.out.println("3. Get Customer by ID");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = input.nextInt();
            
            switch (choice) {
                case 1:
                    customersMgr.RegisterNewCustomer();
                    break;
                case 2:
                    customersMgr.Ohistory();
                    break;
                case 3:
                    Customers c = customersMgr.getCustomersId();
                    if (c != null)
                        System.out.println("Customer: " + c.getName() + " - " + c.getEmail());
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    // ===== ORDER MENU =====
    private static void orderMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- ORDER MANAGEMENT ---");
            System.out.println("1. Search Order by ID");
            System.out.println("2. Cancel Order");
            System.out.println("3. Update Order Status");
            System.out.println("4. Orders Between Two Dates");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = input.nextInt();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter Order ID: ");
                    int oid = input.nextInt();
                    Order order = ordersMgr.searchOrderID(oid);
                    if (order != null)
                        System.out.println(order);
                    break;
                    
                case 2:
                    System.out.print("Enter Order ID to cancel: ");
                    int cancelId = input.nextInt();
                    int result = ordersMgr.cancelOrder(cancelId);
                    if (result == 1)
                        System.out.println("Order cancelled successfully!");
                    else if (result == 2)
                        System.out.println("Order was already cancelled.");
                    else
                        System.out.println("Order not found.");
                    break;
                    
                case 3:
                    System.out.print("Enter Order ID to update: ");
                    int updateId = input.nextInt();
                    ordersMgr.UpdateOrder(updateId);
                    break;
                    
                case 4:
                    System.out.print("Enter start date (dd/MM/yyyy): ");
                    String date1 = input.next();
                    System.out.print("Enter end date (dd/MM/yyyy): ");
                    String date2 = input.next();
                    ordersMgr.BetweenTwoDates(date1, date2);
                    break;
                    
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    // ===== REVIEW MENU =====
    private static void reviewMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- REVIEW MANAGEMENT ---");
            System.out.println("1. Add New Review");
            System.out.println("2. Update Review");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = input.nextInt();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter Customer ID: ");
                    int custId = input.nextInt();
                    System.out.print("Enter Product ID: ");
                    int prodId = input.nextInt();
                    reviewsMgr.addNewReview(custId, prodId);
                    break;
                    
                case 2:
                    reviewsMgr.updateReview();
                    break;
                    
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    // ===== QUERIES MENU =====
    private static void queriesMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- QUERIES & REPORTS ---");
            System.out.println("1. Top 3 Products by Rating");
            System.out.println("2. Reviews by Customer");
            System.out.println("3. Common Products Between Two Customers");
            System.out.println("4. Average Rating for Product");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = input.nextInt();
            
            switch (choice) {
                case 1:
                    getTop3Products();
                    break;
                    
                case 2:
                    System.out.print("Enter Customer ID: ");
                    int custId = input.nextInt();
                    getReviewsByCustomer(custId);
                    break;
                    
                case 3:
                    System.out.print("Enter First Customer ID: ");
                    int cust1 = input.nextInt();
                    System.out.print("Enter Second Customer ID: ");
                    int cust2 = input.nextInt();
                    getCommonProducts(cust1, cust2);
                    break;
                    
                case 4:
                    System.out.print("Enter Product ID: ");
                    int prodId = input.nextInt();
                    getAverageRating(prodId);
                    break;
                    
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    // ===== QUERY IMPLEMENTATIONS =====
    
    /**
     * Get top 3 products by average rating
     * Time Complexity: O(n*m) where n=products, m=reviews per product
     */
    private static void getTop3Products() {
        System.out.println("\n--- TOP 3 PRODUCTS BY RATING ---");
        
        LinkedList<Product> products = productsMgr.getProducts();
        if (products.empty()) {
            System.out.println("No products available.");
            return;
        }
        
        // Use Priority Queue to get top 3
        LinkedPQ<Product> pq = new LinkedPQ<>();
        
        products.findFirst();
        for (int i = 0; i < products.size(); i++) {
            Product p = products.retrieve();
            float avgRating = calculateAverageRating(p);
            pq.enqueue(p, avgRating);
            products.findNext();
        }
        
        // Get top 3
        System.out.println("\nTop 3 Products:");
        for (int i = 0; i < 3 && pq.length() > 0; i++) {
            PQElement<Product> element = pq.serve();
            System.out.println((i+1) + ". " + element.data.getName() + 
                             " (Avg Rating: " + String.format("%.2f", element.priority) + ")");
        }
    }
    
    /**
     * Calculate average rating for a product
     * Time Complexity: O(n) where n=number of reviews
     */
    private static float calculateAverageRating(Product p) {
        LinkedList<Integer> reviews = p.getReviews();
        if (reviews.empty())
            return 0.0f;
        
        int sum = 0;
        int count = 0;
        
        reviews.findFirst();
        for (int i = 0; i < reviews.size(); i++) {
            sum += reviews.retrieve();
            count++;
            reviews.findNext();
        }
        
        return count > 0 ? (float)sum / count : 0.0f;
    }
    
    /**
     * Get average rating for a specific product
     * Time Complexity: O(n)
     */
    private static void getAverageRating(int productId) {
        Product p = productsMgr.getProducts(productId);
        if (p == null) {
            System.out.println("Product not found!");
            return;
        }
        
        float avg = calculateAverageRating(p);
        System.out.println("Average Rating for " + p.getName() + ": " + 
                         String.format("%.2f", avg) + " / 5.0");
    }
    
    /**
     * Get all reviews by a specific customer
     * Time Complexity: O(n) where n=total reviews
     */
    private static void getReviewsByCustomer(int customerId) {
        System.out.println("\n--- Reviews by Customer " + customerId + " ---");
        
        LinkedList<Review> allReviews = reviewsMgr.getAllReviews();
        if (allReviews.empty()) {
            System.out.println("No reviews available.");
            return;
        }
        
        boolean found = false;
        allReviews.findFirst();
        for (int i = 0; i < allReviews.size(); i++) {
            Review r = allReviews.retrieve();
            if (r.getCustomer() == customerId) {
                System.out.println(r);
                found = true;
            }
            allReviews.findNext();
        }
        
        if (!found)
            System.out.println("No reviews found for this customer.");
    }
    
    /**
     * Find common products reviewed by two customers with rating > 4
     * Time Complexity: O(n*m) where n=reviews of cust1, m=reviews of cust2
     */
    private static void getCommonProducts(int cust1, int cust2) {
        System.out.println("\n--- Common Products (Rating > 4) ---");
        
        LinkedList<Review> allReviews = reviewsMgr.getAllReviews();
        if (allReviews.empty()) {
            System.out.println("No reviews available.");
            return;
        }
        
        // Get products reviewed by customer 1 with rating > 4
        LinkedList<Integer> cust1Products = new LinkedList<>();
        allReviews.findFirst();
        for (int i = 0; i < allReviews.size(); i++) {
            Review r = allReviews.retrieve();
            if (r.getCustomer() == cust1 && r.getRating() > 4) {
                cust1Products.insert(r.getProduct());
            }
            allReviews.findNext();
        }
        
        if (cust1Products.empty()) {
            System.out.println("Customer " + cust1 + " has no reviews with rating > 4");
            return;
        }
        
        // Find common products with customer 2
        boolean found = false;
        cust1Products.findFirst();
        for (int i = 0; i < cust1Products.size(); i++) {
            int productId = cust1Products.retrieve();
            
            // Check if customer 2 also reviewed this product with rating > 4
            allReviews.findFirst();
            for (int j = 0; j < allReviews.size(); j++) {
                Review r = allReviews.retrieve();
                if (r.getCustomer() == cust2 && 
                    r.getProduct() == productId && 
                    r.getRating() > 4) {
                    
                    Product p = productsMgr.getProducts(productId);
                    if (p != null) {
                        System.out.println("- " + p.getName() + " (ID: " + productId + ")");
                        found = true;
                    }
                    break;
                }
                allReviews.findNext();
            }
            
            cust1Products.findNext();
        }
        
        if (!found)
            System.out.println("No common products found with rating > 4");
    }
}

/*
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
        System.out.println("Loading data...");
        products = pdata.getProducts();
        //Customers = cdata.getcustomersData();//
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

//-------------------------
   public static void main(String[] args) {
    
    loadData();
    
    int choice;
    
    
    do {
       
        System.out.println("ـــــــــــــ");
        System.out.println("1. Products");
        System.out.println("2. Customers");
        System.out.println("3. Orders");
        System.out.println("4. Reviews");
        System.out.println("5. Exit");
        System.out.println("Enter your choice:");
        choice = input.nextInt();

        
        switch (choice) {
            case 1:
                productsMenu();  
                break;
            case 2:
                CustomersMenu();  
                break;
            case 3:
                OrdersMenu();  
                break;
            case 4:
                ReviewsMenu();  
                break;
            case 5:
                break;  
            default:
                System.out.println("Bad choice, Try again");
        }
    } while (choice != 5);  
}
  }*/

