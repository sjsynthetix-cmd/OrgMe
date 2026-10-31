
package com.orgme.model;

public abstract class ServicePackage {
    String packageId;
    String packageName;
    double basePrice;
    int estimatedDays;
    
    public ServicePackage(String packageId,String packageName,double baseprice,int estimatedDays){
        this.packageId = packageId;
        this.packageName = packageName;
        this.basePrice = basePrice;
        this.estimatedDays = estimatedDays;
    }
    
    public void setbaseprice(double baseprice){
        if(baseprice<10000){
            throw new IllegalArgumentException("Base price cannot be less than LKR 10,000");
        }else{
            this.basePrice = baseprice;
        }
    }
    
    public String getpackageId(){
        return packageId;
    }
    public String getpackageNmae(){
        return packageName;
    }
    public double getbasePrice(){
        return basePrice;
    }
    public int getestimatedDays(){
        return estimatedDays;
    }
    
    public abstract double calculateEstimatedCost();
    public abstract void printPackageDetails();
}
