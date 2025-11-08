
package com.mycompany.datastructures;


import java.util.Date;



public class Order {
    int oId;
    int customerRef;
    LinkedList <Integer> products = new LinkedList <Integer> ();  
    double total_price;
    String date;
    String status; 

    public Order() {
        this.oId = 0;
        this.customerRef = 0;
        this.total_price = 0;
        this.status = "";
        this.date = "";

    }

    public Order(int oId, int customerRef, Integer [] pids, double total_price, String date, String status) {
        this.oId = oId;
        this.customerRef = customerRef;
        this.total_price = total_price;
        this.date = date ;
        this.status = status;
        
        
        int i = 0;
        while (i < pids.length) {
        this.products.insert(pids[i]);
        i++;
}
    }

    public int getoId() {
        return oId;
    }

    public int getCustomerRef() {
        return customerRef;
    }

    public LinkedList<Integer> getProducts() {
        return products;
    }

    public double getTotal_price() {
        return total_price;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setOrderId(int oId) {
        this.oId = oId;
    }

    public void setcustomerRef(int customerRef) {
        this.customerRef = customerRef;
    }

    public void setProducts(int pid) {
        this.products.insert(pid);
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addProduct (Integer product )
    {
        products.insert(product);
    }

    public boolean deleteProduct( Integer P)
    {
        if ( ! products.empty())
        {
            products.findFirst();
            while(! products.last())
            {
                if (products.retrieve() == P)
                {
                    products.remove();
                    return true;
                }
                else
                    products.findNext();
            }
            if (products.retrieve() == P)
            {
                products.remove();
                return true;
            }
        }
        return false;
    }
    
   
    @Override
    public String toString() {
        String str =  "\nOrder{" + "orderId=" + oId
                + ", customer Refrence=" + customerRef 
                + ",total price=" + total_price 
                + " , status =" + status
                + ", date =" + date;
        if ( ! products.empty())
        {
            str += "(products List" ;
            products.findFirst();
            while(! products.last())
            {
                str += products.retrieve() + " ";
                products.findNext();
            }
            str += products.retrieve() + " )";
        }
        str +=  " }";
        return str;        
    }
    

}