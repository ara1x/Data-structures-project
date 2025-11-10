/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastr;

import java.util.ArrayList;
import java.util.List;


public class Customers{
    int customerID;
    String name;
    String Email; 
  LinkedList<Integer> order= new LinkedList<Integer>();
  
  public Customers() {
        this.customerID = 0;
        this.name = "";
        this.Email = "";
    
    }

    public Customers(int customerID, String name, String Email) {
        this.customerID = customerID;
        this.name = name;
        this.Email = Email;
      

    }
    
  
    public void PlaceNew ( Integer o ) {
      order.insert(o);
    }
       
public boolean removeOrder (Integer o){    
    if(!order.empty()){
        order.findfirst();
        while(!order.last()){
            if(order.retrieve()==o){
               order.remove();
            return true;}
            else
            order.findnext();   
        }
             if(order.retrieve()==o){ 
            order.remove();
            return true;}
            
             return false;
             
            
    }
     
    

}   
    

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    } 

    public LinkedList<Integer> getOrder() {
        return order;
    }
    
    
}

