 package com.orgme.model;

// OOP Principle: Inheritance - Client is-a User
public class Client extends User {
    // Encapsulated private attributes specific to Client
    private String companyName;
    private double budgetLimit;

    // Constructor chaining via super()
    public Client(String userId, String username, String password, String companyName, double budgetLimit) {
        super(userId, username, password, "CLIENT");
        this.companyName = companyName;
        setBudgetLimit(budgetLimit); // Defensive validation
    }

    // Getters and Setters
    public String getCompanyName() {
        return companyName;
    }

    public double getBudgetLimit() {
        return budgetLimit;
    }

