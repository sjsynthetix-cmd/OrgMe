
package com.orgme.model;

public class MobileAppPackage extends ServicePackage{
    
    private boolean includesPlayStoreClosedTesting;
    private String targetPlatform;
    
    @Override
    public double calculateEstimatedCost(){
        double total = super.basePrice;
        if (this.includesPlayStoreClosedTesting) {
            total += 35000.0; 
        }
        return total;
    }
    
    @Override
    public void printPackageDetails() {
        System.out.println("--- Mobile App Package Details ---");
        System.out.println("ID: " + packageId + " | Name: " + packageName);
        System.out.println("Platform: " + targetPlatform);
        System.out.println("Play Store Track: " + includesPlayStoreClosedTesting);
        System.out.println("Total Cost: LKR " + calculateEstimatedCost());
    }
    
    public MobileAppPackage(String packageId,String packageName,double basePrice,int estimatedDays,boolean includesPlayStoreClosedTesting,String targetPlatform){
        super(packageId,packageName,basePrice, estimatedDays);  
        
        this.includesPlayStoreClosedTesting = includesPlayStoreClosedTesting;
        this.targetPlatform = targetPlatform;
    }
    
}
