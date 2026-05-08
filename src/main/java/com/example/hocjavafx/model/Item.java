package com.example.hocjavafx.model;

public abstract class Item extends Entity {
    protected String itemName;
    protected String description;
    protected double startingPrice;

    public Item(int id, String itemName, String description, double startingPrice) {
        super(id);
        this.itemName = itemName;
        this.description = description;
        this.startingPrice = startingPrice;
    }
}