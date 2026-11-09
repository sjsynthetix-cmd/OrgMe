
package com.orgme.model;

// OOP Principle: Inheritance - Extends ServicePackage
public class AISolutionPackage extends ServicePackage {
    private String aiModelType;
    private boolean requiresDedicatedGpuTraining;

    public AISolutionPackage(String packageId, String packageName, double basePrice, int estimatedDays,
                             String aiModelType, boolean requiresDedicatedGpuTraining) {
        super(packageId, packageName, basePrice, estimatedDays);
        this.aiModelType = aiModelType;
        this.requiresDedicatedGpuTraining = requiresDedicatedGpuTraining;
    }

    // OOP Principle: Polymorphism (Method Overriding) - Dedicated GPU Compute (+LKR 60,000)
    @Override
    public double calculateEstimatedCost() {
        double total = super.basePrice;
        if (requiresDedicatedGpuTraining) {
            total += 60000.0;
        }
        return total;
    }

    @Override
    public void printPackageDetails() {
        System.out.printf("[%s] %s | AI Type: %s | Dedicated GPU Cluster: %s | Base: LKR %,.2f | Total: LKR %,.2f%n",
                packageId, packageName, aiModelType, 
                (requiresDedicatedGpuTraining ? "YES (+LKR 60,000)" : "NO"), basePrice, calculateEstimatedCost());
    }
}

