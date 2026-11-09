package com.orgme;

import com.orgme.model.Client;
import com.orgme.model.Staff;
import com.orgme.model.User;
import com.orgme.service.AuthService;
import java.util.Scanner;

import com.orgme.util.FileDatabaseManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     OrgMe - Software Consultation & Management   ");
        System.out.println("==================================================");
        System.out.println("System initialized successfully.");
        FileDatabaseManager.initializeDatabase();
        
        AuthService authService = new AuthService();
        
        authService.registerUser(new Staff("STF01", "sandaru", "admin123", "Lead Architect"));
        authService.registerUser(new Client("CL01", "alpha_client", "pass123", "Alpha Corp", 150000.0));

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- LOGIN SYSTEM ---");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User currentUser = authService.login(username, password);

        if (currentUser != null) {
            System.out.println("\nLogin Successful! Welcome, " + currentUser.getUsername() + " (" + currentUser.getRole() + ")");
            currentUser.displayDashboard();
        } else {
            System.out.println("\nInvalid Username or Password! Login Failed.");
        }
        
        scanner.close();
    }
}
        


