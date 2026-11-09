package com.orgme.model;

/**
 * OOP Principle: Inheritance - Client is-a User
 */
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

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getBudgetLimit() {
        return budgetLimit;
    }

    public void setBudgetLimit(double budgetLimit) {
        if (budgetLimit < 0.0) {
            this.budgetLimit = 0.0;
        } else {
            this.budgetLimit = budgetLimit;
        }
    }

    // Polymorphism: Overriding the abstract method from User
    @Override
    public void displayDashboard() {
        System.out.println("\n========================================");
        System.out.println("            CLIENT DASHBOARD            ");
        System.out.println("========================================");
        System.out.println("Client ID    : " + getUserId());
        System.out.println("Username     : " + getUsername());
        System.out.println("Company      : " + companyName);
        System.out.println("Budget Limit : LKR " + String.format("%.2f", budgetLimit));
        System.out.println("Role         : " + getRole());
        System.out.println("========================================");
    }
}