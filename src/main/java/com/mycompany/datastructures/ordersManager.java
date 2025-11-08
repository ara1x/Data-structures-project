/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.datastructures;

/**
 *
 * @author 96650
 */

import java.io.File;

import java.util.Scanner;


public class ordersManager {
    
    public static Scanner input = new Scanner (System.in);
    public static LinkedList<Order> orders = new LinkedList<Order> ();
    
//==============================================================
    public LinkedList<Order>  getordersData ( )
    {
        return orders;
    }
    
//==============================================================
    public ordersManager ( String fName)
    {
        try{
                File docsfile = new File(fName);
                Scanner reader = new Scanner (docsfile);
                String line = reader.nextLine(); //header of data
                
                while(reader.hasNext())
                {
                    line = reader.nextLine();
                    String [] data = line.split(","); 
                    int oid = Integer.parseInt(data[0]);
                    int cid = Integer.parseInt(data[1]);
                    
                    String  pp =  data[2].replaceAll("\"", "");
                    String [] p = pp.split(";");
                    Integer [] pids = new Integer [p.length];
                 
                    int i = 0;
                    while (i < pids.length) {
                        pids[i] = Integer.parseInt(p[i].trim()); //cheack
                        i++;
                    }
                    double price = Double.parseDouble(data[3]);
                    String date = data[4];
                    String status = data[5];
                            
                    Order order = new Order(oid, cid, pids, price, date, status );
                    orders.insert(order);
                    
                }
                reader.close();
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
     }

    //==============================================================
    public int cancelOrder (int oid)
    {
        orders.findFirst();
        int i = 0;
        while (i < orders.size())
        {
            if ( orders.retrieve().getoId() == oid )
            {
                if (orders.retrieve().status.compareToIgnoreCase("cancelled") == 0)
                {
                    System.out.println("Order " + orders.retrieve().getoId() + " was cancelled before");
                    return 2; // canceled before 
                }
                    
                Order ordera = orders.retrieve(); 
                ordera.status = "cancelled";
                orders.insert(ordera);
                return 1; 
            }
            else
                orders.findNext();
            
            i++;
        }
        return 0; // not found order
    }
    
    //==============================================================
    public boolean UpdateOrder( int orderID)
    {
         boolean found = false;
        orders.findFirst();
        while (!orders.last())
        {
            if (orders.retrieve().getoId()== orderID)
            {
                found = true;
                break;
            }
            orders.findNext();
        }
        if (orders.retrieve().getoId()== orderID)
            found = true;

        if (found )
        {
            if ( orders.retrieve().getStatus().compareToIgnoreCase("cancelled") == 0)
            {
                System.out.println("Cannot update status for cancelled orders");
                return false;
            }
             else
            {
                Order obj = orders.retrieve();

                System.out.println("Status of order is " + orders.retrieve().getStatus() );
                System.out.print("Please enter new status (pending/shipped/delivered/cancelled): ");
                String str = input.next();
                orders.remove();//cheack
                obj.status = str;
                orders.insert(obj);
                return true;
            }
        }
                System.out.println("Invalid order ID");
                return false;
    }
    
    //==================================================================    
    public Order searchOrderID(int orderID)
    {
        boolean found = false;

        orders.findFirst();
        while (!orders.last())
        {
            if (orders.retrieve().getoId()== orderID)
            {
                found = true;
                break;
            }
            orders.findNext();
        }
        if (orders.retrieve().getoId()== orderID)
            found = true;

        if (found )
            return orders.retrieve();

     System.out.println("Invalid order ID");
        return null;
    }

    //==================================================================    
   public LinkedList<Order> BetweenTwoDates(String date1, String date2)
{
    LinkedList<Order> ordersbetweenDates =  new LinkedList<Order>();
    
    if (! orders.empty())
    {
        orders.findFirst();
        
        for ( int i = 0 ; i < orders.size() ; i++)
        {
            String orderDate = orders.retrieve().getDate();
            
            if (isDateInRange(orderDate, date1, date2))
            {
                ordersbetweenDates.insert(orders.retrieve());
                System.out.println(orders.retrieve());
            }
           orders.findNext();
        }
    }
    return ordersbetweenDates;
}

        private boolean isDateInRange(String date, String start, String end) {
            int dateNum = convertDateToNumber(date);
            int startNum = convertDateToNumber(start);
            int endNum = convertDateToNumber(end);

            return dateNum >= startNum && dateNum <= endNum;
        }

        // Convert dd/MM/yyyy to yyyyMMdd for comparison
        private int convertDateToNumber(String date) {
            String[] parts = date.split("/");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);

            return year * 10000 + month * 100 + day;
        }
    //==================================================================    
    public boolean Checkorderid(int oid)
    {
        if (!orders.empty())
        {
            orders.findFirst();
            while (!orders.last())
            {
                if (orders.retrieve().getoId()== oid)
                    return true;
                orders.findNext();
            }
            if (orders.retrieve().getoId()== oid)
                return true;
        }
        return false;
    }
}