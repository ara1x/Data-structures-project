
package com.mycompany.datastructures;

public class Review {
    int reviewId;
    int productID;
    int customerID;
    int rating;
    String comment;

    public Review() {
        this.reviewId = 0;
        this.productID = 0;
        this.customerID = 0;
        this.rating = 0;
        this.comment = "";
    }

    public Review(int reviewId, int productID, int customerID, int rating, String comment) {
        this.reviewId = reviewId;
        this.productID = productID;
        this.customerID = customerID;
        setRating(rating);  
        this.comment = comment;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getProduct() {
        return productID;
    }

    public void setProduct(int productID) {
        this.productID = productID;
    }

    public int getCustomer() {
        return customerID;
    }

    public void setCustomer(int customerID) {
        this.customerID = customerID;
    }

    public int getRating() {
        return rating;
    }

    
    public void setRating(int rating) {
        if (rating < 1)
            this.rating = 1;
        else if (rating > 5)
            this.rating = 5;
        else
            this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Review{" + "reviewId=" + reviewId + ", productID=" + productID + ", customerID=" + customerID + ", rating=" + rating + ", comment=" + comment + '}';
    }

  
}
