package com.example.sellmybooks;

public class BooksDetails {

    public String userName;
    public String userEmail;
    public String bookName;
    public String description;
    public String author;
    public String publisher;
    public String price;

    public BooksDetails() {
    }

    public BooksDetails(String userName, String userEmail, String bookName, String description, String author, String publisher, String price) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.bookName = bookName;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
