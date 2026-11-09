
package com.orgme.model;

public class AISolutionPackage extends ServicePackage {
    private String aiModelType;
    private boolean requiresDedicatedGpuTraining;
    public AISolutionPackage(String packageId, String packageName, double basePrice, 
        int estimatedDays, String aiModelType, 
        boolean requiresDedicatedGpuTraining) {
        super(packageId, packageName, basePrice, estimatedDays);
        this.aiModelType = aiModelType;
        this.requiresDedicatedGpuTraining = requiresDedicatedGpuTraining;
    }

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
        System.out.println("--- AI Solution Package Details ---");
        System.out.println("ID: " + packageId);
        System.out.println("Name: " + packageName);
        System.out.println("Model Type: " + aiModelType);
        System.out.println("GPU Cluster: " + requiresDedicatedGpuTraining);
        System.out.println("Total Estimated Cost: LKR " + calculateEstimatedCost());
        System.out.println();
    }
 }

