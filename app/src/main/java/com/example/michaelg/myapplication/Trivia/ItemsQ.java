package com.example.michaelg.myapplication.Trivia;

/**
 * Created by Pcp on 02/10/2016.
 */

public class ItemsQ {
    private String author;
    private String itemName;

    public ItemsQ(String author, String itemName) {
        this.author = author;
        this.itemName = itemName;
    }
    public ItemsQ() {
        // TODO Auto-generated constructor stub
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
