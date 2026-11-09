
package com.orgme.model;

public class WebAppPackage extends ServicePackage {
    private boolean includeCloudHosting;
    private int numberOfDatabaseEntities; 
    
    public WebAppPackage(String packageId, String packageName, double basePrice,int estimatedDays, boolean includeCloudHosting,int numberOfDatabaseEntities){
        super(packageId, packageName, basePrice, estimatedDays);
        this.includeCloudHosting = includeCloudHosting;
        this.numberOfDatabaseEntities = numberOfDatabaseEntities;
    }

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
        System.out.println("--- Web App Package Details ---");
        System.out.println("ID: " + packageId );
        System.out.println("Name: " + packageName);
        System.out.println("Cloud Hosting: " + includeCloudHosting);
        System.out.println("DB Entities: " + numberOfDatabaseEntities);
        System.out.println("Total Estimated Cost: LKR " + calculateEstimatedCost());
        System.out.println();
    }
}
