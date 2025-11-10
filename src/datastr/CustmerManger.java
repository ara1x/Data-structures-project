/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastr;

import java.io.File;
import java.util.Scanner;
public class CustmerManger {
    
    /**
     *
     */
    public static Scanner input=new Scanner(System.in);
    public static LinkedList<Customers> customer = new LinkedList <Customers> ();
    
    public CustmerManger (String readFile){
        try{
           File custmerFile = new File( readFile) ;
           Scanner read = new Scanner(custmerFile);
           String line = read.nextLine();
            
           while(read.hasNext()){
               line = read.nextLine();
              String [] cust= line.split(",");
              Customers customers = new Customers(Integer.parseInt(cust[0]),cust[1],cust[2]);
            customer.insert(customers);
           }
           read.close();
        }
        catch(Exception ex){
          System.out.print(ex.getMessage());
        }
    }
    
    
    
    
    
    
    public void RegisterNewCustomer(){
        Customers customers =new Customers();
 
    
     System.out.println("enter customer ID");
     int customerID = input.nextInt();
     
    if(check(customerID))
     customer.retrieve().setCustomerID(customerID);
      while(!check(customerID)) {   
         System.out.println("Invalid ID. Please enter another one");  
           customerID = input.nextInt();
      }  

 System.out.println("enter customer name");
  String name=input.next();
      customer.retrieve().setName(name);
              
   System.out.println("enter customer name");
   String email=input.next();
   customer.retrieve().setEmail(email);
   
    customer.insert(customers);
   
}
  
      public void Ohistory () {
           LinkedList<Integer> order= new LinkedList<Integer>();
         if(customer.empty()){
           System.out.println("no customer yet");  
         return;}
         
        System.out.println("enter customer ID" );
       int customerID = input.nextInt();
       
        customer.findfirst();
       while(!customer.last()){
        if(customer.retrieve().getCustomerID()== customerID){
          order=customer.retrieve().getOrder();   
        if(order.empty()){
             System.out.println("No orders yet");
        }else{
            order.findfirst();
            while(!order.last()){
              System.out.println(order.retrieve());
            order.findnext();}
            System.out.println(order.retrieve());}
        return;
        }
       customer.findnext();
       }
        if(customer.retrieve().getCustomerID()== customerID){
          order=customer.retrieve().getOrder();   
        if(order.empty())
             System.out.println("No orders yet");
        else{
            order.findfirst();
            while(!order.last()){
              System.out.println(order.retrieve());
            order.findnext();}
            System.out.println(order.retrieve());}
        return;
        }
       
         System.out.println("Customer not found");
       
    }
    
    
      public  boolean check(int customerID ){
          if(customer.empty())
         return true;
      else{
      while(!customer.last()){
        if(customer.retrieve().getCustomerID()!=customerID)
            customer.findnext();
        else
        return false; 
      }
          }
   if(customer.retrieve().getCustomerID()!=customerID)
    return true;
   else 
       return false; 
       
}
    public Customers getCustomersId(){
    
    if(customer.empty()){
        System.out.println("");
    }
    else
        System.out.println("enter customer ID");
     int customerID = input.nextInt();
    customer.findfirst();
       while(!customer.last()){
        if(customer.retrieve().getCustomerID()== customerID)
    return customer.retrieve();
        customer.findnext();
            }
     if(customer.retrieve().getCustomerID()== customerID)
    return customer.retrieve();
     
      System.out.println("Customer not found");
      return null;
}  
      
    
}