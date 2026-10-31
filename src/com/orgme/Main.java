package com.orgme;

import com.orgme.model.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     OrgMe - Software Consultation & Management   ");
        System.out.println("==================================================");
        System.out.println("System initialized successfully.");
        
        ServicePackage mobPkg = new MobileAppPackage("PKG-MOB-01", "Android Pro", 100000, 14, true, "Android Native");
        ServicePackage webPkg = new WebAppPackage("PKG-WEB-01", "Enterprise Web", 150000, 30, true, 10);
        ServicePackage aiPkg = new AISolutionPackage("PKG-AI-01", "Custom LLM", 200000, 45, "Fine-Tuned LLM", true);
        
        ServicePackage[] catalog = { mobPkg, webPkg, aiPkg };
        for (ServicePackage p : catalog) {
            p.printPackageDetails(); 
        }
    }
}
