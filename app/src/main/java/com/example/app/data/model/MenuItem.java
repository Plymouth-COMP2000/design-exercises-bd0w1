package com.example.app.data.model;

public class MenuItem {
    private long id;
    private String name;
    private String description;
    private double price;
    private boolean available;

    public MenuItem(long id, String name, String description, double price, boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
    }

    public MenuItem(String name, String description, double price, boolean available) {
        this(-1, name, description, price, available);
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return available; }
    public void setId(long id) { this.id = id; }
}
