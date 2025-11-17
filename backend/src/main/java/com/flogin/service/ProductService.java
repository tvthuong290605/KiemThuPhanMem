package com.flogin.service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

// Product model
class Product {
    private long id;
    private String name;
    private double price;
    private int quantity;

    public Product() {}

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // getters & setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "Product{id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity + "}";
    }
}

// ProductService
public class ProductService {

    private final List<Product> products = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Product createProduct(Product product) {
        product.setId(idCounter.getAndIncrement());
        products.add(product);
        return product;
    }

    public Product getProduct(long id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Product updateProduct(long id, Product updated) {
        Product existing = getProduct(id);
        if (existing == null) return null;
        existing.setName(updated.getName());
        existing.setPrice(updated.getPrice());
        existing.setQuantity(updated.getQuantity());
        return existing;
    }

    public boolean deleteProduct(long id) {
        return products.removeIf(p -> p.getId() == id);
    }

    public List<Product> getAll(int page, int limit) {
        int start = (page - 1) * limit;
        int end = Math.min(start + limit, products.size());
        if (start >= products.size()) return new ArrayList<>();
        return products.subList(start, end);
    }

    public void reset() {
        products.clear();
        idCounter.set(1);
    }
}

