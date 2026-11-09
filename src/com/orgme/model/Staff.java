package com.orgme.model;

// OOP Principle: Inheritance - Staff is-a User
public class Staff extends User {
    private String specialization;

    // Constructor chaining calling parent constructor
    public Staff(String userId, String username, String password, String specialization) {
        super(userId, username, password, "STAFF");
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    // OOP Principle: Polymorphism (Method Overriding) - Custom Staff dashboard
    @Override
    public void displayDashboard() {
        System.out.println("================================================================================");
        System.out.println("             STAFF OPERATIONS CONTROL - " + specialization.toUpperCase());
        System.out.println("================================================================================");
        System.out.println("  [1] View All Active Client Projects & Delivery Status");
        System.out.println("  [2] Advance 14-Day Play Store Testing Day / Opt-in Testers");
        System.out.println("  [3] Review & Approve Deployment Releases");
        System.out.println("  [4] Issue Commercial Invoices & Manage Balances");
        System.out.println("  [0] Logout to Main Menu");
        System.out.println("--------------------------------------------------------------------------------");
    }
}