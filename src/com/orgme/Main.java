package com.orgme;

import com.orgme.util.FileDatabaseManager;
import com.orgme.model.*;
import com.orgme.service.*;
import com.orgme.exception.*;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;

// System Integrator: Central Console Execution Hub
public class Main {
    public static void main(String[] args) {
        // Initialize file storage directory and txt tables
        FileDatabaseManager.initializeDatabase();

        // Instantiate core business service engines
        AuthService authService = new AuthService();
        AIEstimatorEngine aiEngine = new AIEstimatorEngine();
        BookingService bookingService = new BookingService();
        TrackingService trackingService = new TrackingService();
        BillingService billingService = new BillingService();

        // Catalog of Software Service Packages
        List<ServicePackage> packageCatalog = new ArrayList<>();
        packageCatalog.add(new MobileAppPackage("PKG-MOB-01", "Android Enterprise & Play Store Closed Testing", 120000.0, 30, true, "Android Native"));
        packageCatalog.add(new MobileAppPackage("PKG-MOB-02", "Cross-Platform Flutter Mobile Suite", 110000.0, 25, false, "Flutter iOS/Android"));
        packageCatalog.add(new WebAppPackage("PKG-WEB-01", "Enterprise Cloud Portal + React Dashboard", 150000.0, 45, true, 8));
        packageCatalog.add(new AISolutionPackage("PKG-AI-01", "LLM Customer Assistant + GPU Training Cluster", 180000.0, 60, "Fine-Tuned LLM Agent", true));

        Scanner scanner = new Scanner(System.in);
        boolean systemRunning = true;

        // Main Application Event Loop
        while (systemRunning) {
            System.out.println("================================================================================");
            System.out.println("           NEXIS SOFTWARE SOLUTIONS LTD - CLIENT & DEV HUB");
            System.out.println("================================================================================");
            System.out.println("  [1] Login (Existing Client or Staff Member)");
            System.out.println("  [2] Register as a New Client");
            System.out.println("  [3] Exit System");
            System.out.println("--------------------------------------------------------------------------------");
            System.out.print("Enter your choice (1-3): ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer

                switch (choice) {
                    case 1:
                        System.out.print("\nEnter Username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter Password: ");
                        String password = scanner.nextLine();

                        // Polymorphic User Authentication
                        User loggedInUser = authService.login(username, password);

                        if (loggedInUser != null) {
                            System.out.println("\nLogin Successful! Welcome, " + loggedInUser.getUsername() + " [" + loggedInUser.getRole() + "]");
                            if (loggedInUser instanceof Client) {
                                handleClientSession(scanner, (Client) loggedInUser, packageCatalog, aiEngine, bookingService, trackingService, billingService);
                            } else if (loggedInUser instanceof Staff) {
                                handleStaffSession(scanner, (Staff) loggedInUser, trackingService, billingService);
                            }
                        } else {
                            System.out.println("\nAuthentication Failed: Invalid username or password.\n");
                        }
                        break;

                    case 2:
                        System.out.println("\n--- Register New Corporate Client ---");
                        System.out.print("Desired Username: ");
                        String newUname = scanner.nextLine();
                        System.out.print("Password: ");
                        String newPwd = scanner.nextLine();
                        System.out.print("Company / Business Name: ");
                        String company = scanner.nextLine();

                        boolean registered = authService.registerClient(newUname, newPwd, company);
                        if (registered) {
                            System.out.println("Registration successful! You may now login.\n");
                        } else {
                            System.out.println("Registration failed: Username already exists.\n");
                        }
                        break;

                    case 3:
                        System.out.println("\nShutting down Nexis Software Solutions Hub. Goodbye!");
                        systemRunning = false;
                        break;

                    default:
                        System.out.println("\nInvalid choice. Please select 1, 2, or 3.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nError: Numeric input required!");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    // Handles interactive Client session
    private static void handleClientSession(Scanner scanner, Client client, List<ServicePackage> packageCatalog,
                                            AIEstimatorEngine aiEngine, BookingService bookingService,
                                            TrackingService trackingService, BillingService billingService) {
        boolean clientSession = true;
        while (clientSession) {
            // Polymorphism: Invokes Client's overridden dashboard
            client.displayDashboard();
            System.out.print("Select an option (0-5): ");

            try {
                int opt = scanner.nextInt();
                scanner.nextLine();

                switch (opt) {
                    case 1:
                        System.out.println("\n--- Available Software Engineering Packages ---");
                        for (ServicePackage pkg : packageCatalog) {
                            pkg.printPackageDetails(); // Polymorphic method call
                        }
                        System.out.println();
                        break;

                    case 2:
                        System.out.println("\n--- AI Project Scope & Cost Estimator ---");
                        System.out.print("Project Type (MOBILE / WEB / AI): ");
                        String pType = scanner.nextLine();
                        System.out.print("Expected Concurrent Users: ");
                        int users = scanner.nextInt();
                        System.out.print("Requires Google Play Store 14-Day Testing? (true/false): ");
                        boolean playTest = scanner.nextBoolean();
                        scanner.nextLine();

                        aiEngine.runAiEstimation(pType, users, playTest);
                        break;

                    case 3:
                        bookingService.displayAvailableSlots();
                        System.out.print("Enter Slot ID to Book (e.g., SLOT-101): ");
                        String sId = scanner.nextLine();
                        System.out.print("Consultation Topic / Requirements Note: ");
                        String note = scanner.nextLine();

                        try {
                            boolean booked = bookingService.bookSlot(sId, client.getUsername(), note);
                            if (booked) {
                                System.out.println("Slot successfully booked! Confirmation synced.");
                            }
                        } catch (SlotAlreadyBookedException e) {
                            System.out.println("Booking Refused: " + e.getMessage());
                        }
                        break;

                    case 4:
                        trackingService.displayClientProjects(client.getUsername());
                        break;

                    case 5:
                        billingService.displayClientInvoices(client.getUsername());
                        System.out.print("Enter Invoice ID to pay (or press Enter to cancel): ");
                        String invId = scanner.nextLine();
                        if (!invId.trim().isEmpty()) {
                            System.out.print("Enter Payment Amount (LKR): ");
                            double payAmt = scanner.nextDouble();
                            scanner.nextLine();

                            try {
                                billingService.settlePayment(invId, payAmt);
                            } catch (InvalidPaymentException e) {
                                System.out.println("Transaction Rejected: " + e.getMessage());
                            }
                        }
                        break;

                    case 0:
                        System.out.println("Logging out from Client Workspace...\n");
                        clientSession = false;
                        break;

                    default:
                        System.out.println("Invalid option! Please choose 0-5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Numeric input required!");
                scanner.nextLine();
            }
        }
    }

    // Handles interactive Staff session
    private static void handleStaffSession(Scanner scanner, Staff staff, TrackingService trackingService, BillingService billingService) {
        boolean staffSession = true;
        while (staffSession) {
            // Polymorphism: Invokes Staff's overridden dashboard
            staff.displayDashboard();
            System.out.print("Select an option (0-4): ");

            try {
                int opt = scanner.nextInt();
                scanner.nextLine();

                switch (opt) {
                    case 1:
                        trackingService.displayAllProjects();
                        break;

                    case 2:
                        System.out.println("\n--- Advance 14-Day Play Store Testing Cycle ---");
                        System.out.print("Enter Project ID (e.g., PRJ-101): ");
                        String prjId = scanner.nextLine();

                        try {
                            trackingService.advanceTestingDay(prjId);
                        } catch (ProjectNotFoundException e) {
                            System.out.println("Action Failed: " + e.getMessage());
                        }
                        break;

                    case 3:
                        System.out.println("\n--- Approve Project Deployment Release ---");
                        System.out.print("Enter Project ID to Deploy: ");
                        String deployPrj = scanner.nextLine();

                        try {
                            ProjectOrder prj = trackingService.findProjectById(deployPrj);
                            if (prj.isTestingApproved()) {
                                trackingService.updateProjectPhase(deployPrj, "COMPLETED", "Approved for live production release.");
                            } else {
                                System.out.println("Deployment Denied: Project has not satisfied Play Store 14-day 20-tester testing!");
                            }
                        } catch (ProjectNotFoundException e) {
                            System.out.println("Action Failed: " + e.getMessage());
                        }
                        break;

                    case 4:
                        billingService.displayAllInvoices();
                        System.out.print("Issue new invoice? (y/n): ");
                        String issueAns = scanner.nextLine();
                        if ("y".equalsIgnoreCase(issueAns)) {
                            System.out.print("Project ID: ");
                            String pId = scanner.nextLine();
                            System.out.print("Client Username: ");
                            String cUser = scanner.nextLine();
                            System.out.print("Gross Package Amount (LKR): ");
                            double gAmt = scanner.nextDouble();
                            System.out.print("Discount Amount (LKR): ");
                            double dAmt = scanner.nextDouble();
                            scanner.nextLine();

                            Invoice issued = billingService.issueInvoice(pId, cUser, gAmt, dAmt);
                            System.out.println("Invoice successfully issued: " + issued.getInvoiceId());
                        }
                        break;

                    case 0:
                        System.out.println("Logging out from Staff Control...\n");
                        staffSession = false;
                        break;

                    default:
                        System.out.println("Invalid option! Please choose 0-4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Numeric input required!");
                scanner.nextLine();
            }
        }
    }
}