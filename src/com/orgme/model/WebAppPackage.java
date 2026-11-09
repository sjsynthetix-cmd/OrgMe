
package com.orgme.model;

// OOP Principle: Inheritance - Extends ServicePackage
public class WebAppPackage extends ServicePackage {
    private boolean includeCloudHosting;
    private int numberOfDatabaseEntities;

    public WebAppPackage(String packageId, String packageName, double basePrice, int estimatedDays,
                         boolean includeCloudHosting, int numberOfDatabaseEntities) {
        super(packageId, packageName, basePrice, estimatedDays);
        this.includeCloudHosting = includeCloudHosting;
        this.numberOfDatabaseEntities = numberOfDatabaseEntities;
    }

    // OOP Principle: Polymorphism (Method Overriding) - Cloud Hosting (+LKR 25,000) and Database entities (+LKR 2,000 each)
    @Override
    public double calculateEstimatedCost() {
        double total = super.basePrice;
        if (includeCloudHosting) {
            total += 25000.0;
        }
        total += (numberOfDatabaseEntities * 2000.0);
        return total;
    }

    @Override
    public void printPackageDetails() {
        System.out.printf("[%s] %s | Cloud Hosting: %s | DB Entities: %d | Base: LKR %,.2f | Total: LKR %,.2f%n",
                packageId, packageName, (includeCloudHosting ? "YES (+LKR 25,000)" : "NO"), 
                numberOfDatabaseEntities, basePrice, calculateEstimatedCost());
    }
}