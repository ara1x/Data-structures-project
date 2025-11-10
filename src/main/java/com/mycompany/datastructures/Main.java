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

    public static ordersManager odata = new ordersManager("orders.csv");
    public static LinkedList<Order> orders;


    // read data from files for all the 4 data structures
    public static void loadData() {
        System.out.println("Loading data...");
        products = pdata.getProducts();
        //Customers = cdata.getcustomersData();//
        orders = odata.getordersData();
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

    public static void OrdersMenu() {
    System.out.println("ــــــــــ Order Menu ــــــــ");
    System.out.println("(1) Search for an Order");
    System.out.println("(2) Cancel an Order");
    System.out.println("(3) Update Order Status");
    System.out.println("(4) Find Orders in Date Range");
    System.out.println("(5) Place New Order");  
System.out.println("(6) Return to Main Menu");
    System.out.print("\n→ Enter option: ");
    
    try {
        int opt = input.nextInt();
        
        // Using nested if-else (different from teammate's while loop style)
        if (opt == 1) {
            searchForOrder();
        } 
        else if (opt == 2) {
            cancelAnOrder();
        } 
        else if (opt == 3) {
            updateOrderStatus();
        } 
        else if (opt == 4) {
            findOrdersInDateRange();
        } 
        else if (opt == 5) {
    placeNewOrder();
}
        else if (opt == 6) {
            System.out.println("→ Going back to main menu...\n");
            ReviewsMenu();
        } 
        else {
            System.out.println("⚠ Invalid option! Please choose 1-5.");
        }
        
    } catch (Exception ex) {
        System.out.println("⚠ Error: Invalid input type!");
        input.nextLine(); // clear buffer
    }
}

private static void searchForOrder() {
    System.out.print("\n→ Enter Order ID: ");
    int orderId = input.nextInt();
    
    Order foundOrder = odata.searchOrderID(orderId);
    
    if (foundOrder != null) {
        System.out.println("\n Order Located:");
        System.out.println(foundOrder);
    } else {
        System.out.println("\n Order with ID " + orderId + " does not exist.");
    }
}


/**
 * Cancel existing order
 */

private static void cancelAnOrder() {
    System.out.print("\n→ Enter Order ID to cancel: ");
    int orderId = input.nextInt();
    
    if (!odata.Checkorderid(orderId)) {
        System.out.println("\n✗ Order not found.");
        return;
    }
    
    Order cancelOrder = odata.searchOrderID(orderId);
    
    if (cancelOrder.getStatus().equalsIgnoreCase("cancelled")) {
        System.out.println("\n⚠ Already cancelled.");
        return;
    }
    
    odata.cancelOrder(orderId);
    
    // Update customer
    customers.findFirst();
    for (int i = 0; i < customers.size(); i++) {
        if (customers.retrieve().getCustomerID() == cancelOrder.getCustomerRef()) {
            Customers c = customers.retrieve();
            customers.remove();
            c.removeOrder(cancelOrder.getoId());
            customers.insert(c);
            break;
        }
        customers.findNext();
    }
    
    // Update stock
    cancelOrder.getProducts().findFirst();
    for (int x = 0; x < cancelOrder.getProducts().size(); x++) {
        int pid = cancelOrder.getProducts().retrieve();
        
        products.findFirst();
        for (int j = 0; j < products.size(); j++) {
            if (products.retrieve().getProductId() == pid) {
                products.retrieve().addStock(1);
                //break; why
            }
            products.findNext();
        }
        cancelOrder.getProducts().findNext();
    }
    
    System.out.println("\n✓ Order cancelled successfully.");
}

  public static void PlaceOrder()
    {
            Order new_order = new Order ();
            int total_price = 0;
            
            System.out.println("Enter order ID: ");
            int oid = input.nextInt();
            while ( odata.Checkorderid(oid))
            {
                System.out.println("Re-enter order id, is available , try again");
                oid = input.nextInt();
            }
            new_order.setOrderId(oid);
            
            System.out.println("Enter customer ID:");
            int cid = input.nextInt();
            while(! cdata.checkCustomerID(cid))
            {
                System.out.println("Re-enter customer ID, is not available , try again");
                cid = input.nextInt();
            }
            new_order.setcustomerRef(cid);
            
            char answer = 'y';
            while (answer == 'y' || answer == 'Y')
            {
                System.out.println("Enter product ID:");
                int pid = input.nextInt();
               
                boolean found = false;
                
                products.findFirst();
                for ( int i = 0 ;  i < products.size() ;i++)
                {
                    if (products.retrieve().getProductId() == pid)
                    {
                        if (products.retrieve().getStock() == 0)
                            System.out.println("product out stock , try another time");
                        else if (products.retrieve().getStock() == -1)
                            System.out.println("Archived product, cant be use");
                        else
                        {
                            Product pp = products.retrieve();
                            products.remove();
                            pp.setStock(pp.getStock()-1);
                            products.insert(pp);
                            System.out.println("product added to order");
                            found = true;
                            
                            new_order.addProduct(pp.getProductId());
                            total_price += pp.getPrice();
                        }
                        break;
                    }
                    products.findNext();
                }

                if (!found)
                        System.out.println("  No such product id");
                    
                
                System.out.println("Do you want to continue adding product? (Y/N)");
                answer = input.next().charAt(0);
            }
            
            new_order.setTotal_price(total_price);
            
            System.out.println("Enter first date (dd/mm/yyyy)");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate Ldate = LocalDate.parse(input.next(), formatter);
            new_order.setDate(Ldate);
     
            System.out.println("Enter new status  (pending, shipped, delivered, cancelled)....");
            new_order.setStatus(input.next());
            
            orders.insert(new_order);
            
            // add order to customer list
            customers.findFirst();
            for(int i = 0 ; i < customers.size(); i++)
            {
                if (customers.retrieve().getCustomerId() == new_order.getCustomerRef())
                {
                    Customer cust = customers.retrieve();
                    customers.remove();
                    cust.addOrder(oid);
                    customers.insert(cust);
                    break;
                }
                customers.findNext();
            }   
            
            System.out.println("Order had been added ");
            System.out.println(orders.retrieve());
    }
    
/**
 * Update order status
 */
private static void updateOrderStatus() {
    System.out.print("\n→ Enter Order ID: ");
    int orderId = input.nextInt();
    
    boolean updated = odata.UpdateOrder(orderId);
    
    if (updated) {
        System.out.println("\n Order status modified successfully.");
    } else {
        System.out.println("\n Unable to modify order status.");
    }
}


/**
 * Find orders within date range
 */
private static void findOrdersInDateRange() {
    System.out.println("\n Date Format: dd/MM/yyyy");
    
    System.out.print(" Enter start date: ");
    String startDate = input.next();
    
    System.out.print(" Enter end date: ");
    String endDate = input.next();
    
    System.out.println("\n" + "─".repeat(50));
    System.out.println("Orders from " + startDate + " to " + endDate + ":");
    System.out.println("─".repeat(50));
    
    LinkedList<Order> filteredOrders = odata.BetweenTwoDates(startDate, endDate);
    
    if (filteredOrders.empty()) {
        System.out.println("No orders found in this date range.");
    }
    
    System.out.println("─".repeat(50));
}

public static void placeNewOrder() {
    Order orderObj = new Order();
    int priceTotal = 0;
    
    System.out.print("\n→ Enter Order ID: ");
    int orderID = input.nextInt();
    while (odata.Checkorderid(orderID)) {
        System.out.print("⚠ ID exists. Try another: ");
        orderID = input.nextInt();
    }
    orderObj.setOrderId(orderID);
    
    System.out.print("→ Enter Customer ID: ");
    int custID = input.nextInt();
    while (!cdata.check(custID)) {
        System.out.print("⚠ Customer not found. Re-enter: ");
        custID = input.nextInt();
    }
    orderObj.setcustomerRef(custID);
    
    char addMore = 'y';
    while (addMore == 'y' || addMore == 'Y') {
        System.out.print("→ Product ID: ");
        int prodID = input.nextInt();
        
        boolean located = false;
        
        products.findFirst();
        int i = 0;
        while (i < products.size()) {
            if (products.retrieve().getProductId() == prodID) {
                Product prod = products.retrieve();
                
                if (prod.getStock() == 0) {
                    System.out.println("✗ Out of stock.");
                } else if (prod.getStock() == -1) {
                    System.out.println("✗ Archived product.");
                } else {
                    products.remove();
                    prod.setStock(prod.getStock() - 1);
                    products.insert(prod);
                    
                    orderObj.addProduct(prod.getProductId());
                    priceTotal += prod.getPrice();
                    
                    System.out.println("✓ Added to order.");
                    located = true;
                }
                break;
            }
            products.findNext();
            i++;
        }
        
        if (!located && prod.getStock() != 0 && prod.getStock() != -1)
            System.out.println("✗ Product not found.");
        
        System.out.print("→ Add another product? (Y/N): ");
        addMore = input.next().charAt(0);
    }
    
    orderObj.setTotal_price(priceTotal);
    
    System.out.print("→ Order Date (dd/MM/yyyy): ");
    String dateInput = input.next();
    orderObj.setDate(dateInput);
    
    System.out.print("→ Status (pending/shipped/delivered/cancelled): ");
    orderObj.setStatus(input.next());
    
    orders.insert(orderObj);
    
    // Add to customer
    customers.findFirst();
    int j = 0;
    while (j < customers.size()) {
        if (customers.retrieve().getCustomerID() == orderObj.getCustomerRef()) {
            Customers c = customers.retrieve();
            customers.remove();
            c.PlaceNew(orderID);
            customers.insert(c);
            break;
        }
        customers.findNext();
        j++;
    }
    
    System.out.println("\n✓ Order created successfully!");
    System.out.println(orders.retrieve());
}
// 
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
  }

