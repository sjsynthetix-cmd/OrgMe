package com.orgme;

import com.orgme.util.FileDatabaseManager;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        FileDatabaseManager.initializeDatabase();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while(running){
            
            //1st interface
            System.out.println("================================================================================");
            System.out.println("           NEXIS SOFTWARE SOLUTIONS LTD - CLIENT & DEV HUB");
            System.out.println("================================================================================");
            System.out.println("  [1] Login (Existing Client or Staff Member)");
            System.out.println("  [2] Register as a New Client");
            System.out.println("  [3] Exit System");
            System.out.println("--------------------------------------------------------------------------------");
            System.out.print("Enter your choice (1-3): ");
            
            int input = scanner.nextInt();
            scanner.nextLine();
            switch(input){
                case 1://login
                    System.out.print("Enter Username: ");
                    String user = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String pass = scanner.nextLine();
                    System.out.println("[DEBUG] Logged in as: " + user);//for test
                    break;
                
                case 2://register
                    System.out.println("\n--- Register as a New Client ---");
                    System.out.print("Enter Desired Username: ");
                    String newUsername = scanner.nextLine();

                    System.out.print("Enter Password: ");
                    String newPassword = scanner.nextLine();

                    System.out.print("Enter Company/Business Name: ");
                    String companyName = scanner.nextLine();

                    System.out.println("[DEBUG] Registered Client: " + newUsername + " from " + companyName);//for test
                    System.out.println("Registration successful! You can now login.\n");
                    break;
                    
                case 3://exits
                    
                    System.out.println("\nExiting System... Thank you for choosing Nexis Software Solutions!");
                    running = false; // stop loop
                    break;

                default:
                    System.out.println("\nInvalid choice! Please select 1, 2, or 3.\n");
                    break;    
                    
            }
            
        }
        
         
        
    }
}
