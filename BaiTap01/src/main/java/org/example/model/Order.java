package org.example.model;
public class Order {
    private String id;
    private String status;

    public Order(String id) {
        this.id = id;
        this.status = "NEW";
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}