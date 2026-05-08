package com.example.hocjavafx.model;

public abstract class Entity {
    protected int id; // ID chung cho cả User và Item

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) { this.id = id; }
}