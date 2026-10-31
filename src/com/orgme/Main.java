package com.orgme;

import com.orgme.util.FileDatabaseManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     OrgMe - Software Consultation & Management   ");
        System.out.println("==================================================");
        System.out.println("System initialized successfully.");
        FileDatabaseManager.initializeDatabase();
    }
    
}
