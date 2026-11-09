
package com.orgme.model;
// OOP Principle: Abstraction - Base abstract class for software solutions catalog
public abstract class ServicePackage {
    protected String packageId;
    protected String packageName;
    protected double basePrice;
    protected int estimatedDays;
    public ServicePackage(String packageId, String packageName, double basePrice, int estimatedDays) {
        this.packageId = packageId;
        this.packageName = packageName;
        setBasePrice(basePrice);
        this.estimatedDays = estimatedDays;
    }
    public String getPackageId() {
        return packageId;
    }
    public String getPackageName() {
        return packageName;
    }
    public double getBasePrice() {
        return basePrice;
    }
    public int getEstimatedDays() {
        return estimatedDays;
    }
    // OOP Principle: Encapsulation with validation
    public void setBasePrice(double basePrice) {
        if (basePrice < 10000.0) {
            throw new IllegalArgumentException("Base package price cannot be less than LKR 10,000");
        }
        this.basePrice = basePrice;
    }
    // OOP Principle: Abstraction - Contracts to be implemented polymorphically
    public abstract double calculateEstimatedCost();
    public abstract void printPackageDetails();
}