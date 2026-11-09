package com.orgme;

import com.orgme.model.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     OrgMe - Software Consultation & Management   ");
        System.out.println("==================================================");
        System.out.println("System initialized successfully.");
        System.out.println();
        
        ServicePackage mobPkg = null;
        ServicePackage webPkg = null;
        ServicePackage aiPkg = null;
        
        try{
        mobPkg = new MobileAppPackage("PKG-MOB-01", "Android Pro", 100, 14, true, "Android Native");
        } catch (IllegalArgumentException e) {
            System.out.println("Mobile Package Error: " + e.getMessage());
            System.out.println();
        }
        
        try{
        webPkg = new WebAppPackage("PKG-WEB-01", "Enterprise Web", 150000, 30, true, 10);
        } catch (IllegalArgumentException e) {
            System.out.println("Web Package Error: " + e.getMessage());
            System.out.println();
        }
        
        try{
        aiPkg = new AISolutionPackage("PKG-AI-01", "Custom LLM", 2000, 45, "Fine-Tuned LLM", true);
        } catch (IllegalArgumentException e) {
            System.out.println("AI Package Error: " + e.getMessage());
            System.out.println();
        }

        if (mobPkg != null) mobPkg.printPackageDetails();
        if (webPkg != null) webPkg.printPackageDetails();
        if (aiPkg != null) aiPkg.printPackageDetails();
       
        }
        
}



