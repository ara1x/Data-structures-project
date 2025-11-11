package com.mycompany.datastructures;

import java.io.File;
import java.util.Scanner;

public class CustomerManger {

   
    public static Scanner input = new Scanner(System.in);
    public static LinkedList<Customers> customer = new LinkedList<Customers>();

    public CustomerManger(String readFile) {
        try {
            File custmerFile = new File(readFile);
            Scanner read = new Scanner(custmerFile);
            String line = read.nextLine();

            while (read.hasNext()) {
                line = read.nextLine();
                String[] cust = line.split(",");
                Customers customers = new Customers(Integer.parseInt(cust[0]), cust[1], cust[2]);
                customer.insert(customers);
            }
            read.close();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
    }

    public void RegisterNewCustomer() {
        Customers customers = new Customers();

        System.out.println("enter customer ID");
        int customerID = input.nextInt();

        if (check(customerID)) {
            customer.retrieve().setCustomerID(customerID);
        }
        while (!check(customerID)) {
            System.out.println("Invalid ID. Please enter another one");
            customerID = input.nextInt();
        }

        System.out.println("enter customer name");
       input.nextLine();
        String name=input.nextLine();
        customer.retrieve().setName(name);

        System.out.println("enter customer email");
        String email = input.nextLine();
        customer.retrieve().setEmail(email);

        customer.insert(customers);

    }

    public void Ohistory() {
        LinkedList<Integer> order = new LinkedList<Integer>();
        if (customer.empty()) {
            System.out.println("no customer yet");
            return;
        }

        System.out.println("enter customer ID");
        int customerID = input.nextInt();

        customer.findFirst();
        while (!customer.last()) {
            if (customer.retrieve().getCustomerID() == customerID) {
                order = customer.retrieve().getOrder();
                if (order.empty()) {
                    System.out.println("No orders yet");
                } else {
                    order.findFirst();
                    while (!order.last()) {
                        System.out.println(order.retrieve());
                        order.findNext();
                    }
                    System.out.println(order.retrieve());
                }
                return;
            }
            customer.findNext();
        }
        if (customer.retrieve().getCustomerID() == customerID) {
            order = customer.retrieve().getOrder();
            if (order.empty()) {
                System.out.println("No orders yet");
            } else {
                order.findFirst();
                while (!order.last()) {
                    System.out.println(order.retrieve());
                    order.findNext();
                }
                System.out.println(order.retrieve());
            }
            return;
        }

        System.out.println("Customer not found");

    }

    public boolean check(int customerID) {
        if (customer.empty()) {
            return true;
        } else {
            while (!customer.last()) {
                if (customer.retrieve().getCustomerID() != customerID) {
                    customer.findNext();
                } else {
                    return false;
                }
            }
        }
        if (customer.retrieve().getCustomerID() != customerID) {
            return true;
        } else {
            return false;
        }

    }

    public Customers getCustomersId() {

        if (customer.empty()) {
            System.out.println("");
        } else {
            System.out.println("enter customer ID");
        }
        int customerID = input.nextInt();
        customer.findFirst();
        while (!customer.last()) {
            if (customer.retrieve().getCustomerID() == customerID) {
                return customer.retrieve();
            }
            customer.findNext();
        }
        if (customer.retrieve().getCustomerID() == customerID) {
            return customer.retrieve();
        }

        System.out.println("Customer not found");
        return null;
    }

}
