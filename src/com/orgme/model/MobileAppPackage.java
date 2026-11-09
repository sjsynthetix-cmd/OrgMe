
package com.orgme.model;

// OOP Principle: Inheritance - Extends ServicePackage
public class MobileAppPackage extends ServicePackage {
    private boolean includesPlayStoreClosedTesting;
    private String targetPlatform; // "Android Native", "Cross-Platform Flutter"

    public MobileAppPackage(String packageId, String packageName, double basePrice, int estimatedDays, 
                            boolean includesPlayStoreClosedTesting, String targetPlatform) {
        super(packageId, packageName, basePrice, estimatedDays);
        this.includesPlayStoreClosedTesting = includesPlayStoreClosedTesting;
        this.targetPlatform = targetPlatform;
    }

    public boolean isIncludesPlayStoreClosedTesting() {
        return includesPlayStoreClosedTesting;
    }

    public String getTargetPlatform() {
        return targetPlatform;
    }

    // OOP Principle: Polymorphism (Method Overriding) - Includes Play Store 14-day compliance fee (+LKR 35,000)
    @Override
    public double calculateEstimatedCost() {
        double total = super.basePrice;
        if (includesPlayStoreClosedTesting) {
            total += 35000.0; // Google Play 14-day 20-tester closed testing compliance fee
        }
        return total;
    }

    @Override
    public void printPackageDetails() {
        System.out.printf("[%s] %s | Platform: %s | Base: LKR %,.2f | Play Store Testing: %s | Total: LKR %,.2f%n",
                packageId, packageName, targetPlatform, basePrice, 
                (includesPlayStoreClosedTesting ? "YES (+LKR 35,000)" : "NO"), calculateEstimatedCost());
    }
}