package com.orgme.service;

import com.orgme.model.Invoice;
import com.orgme.exception.InvalidPaymentException;
import com.orgme.util.FileDatabaseManager;

import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

// Service engine managing commercial invoices and payment settlement
public class BillingService {
    // OOP Principle: Collections & Generics - In-memory Map for invoices
    private Map<String, Invoice> invoiceRegistry;

    public BillingService() {
        this.invoiceRegistry = new HashMap<>();
        loadInvoicesFromDatabase();
    }

    // Loads persisted invoices from invoices.txt; seeds defaults if empty
    private void loadInvoicesFromDatabase() {
        try {
            List<String[]> records = FileDatabaseManager.readAllRecords(FileDatabaseManager.INVOICES_FILE);
            for (String[] data : records) {
                if (data.length >= 7) {
                    String invId = data[0].trim();
                    String pId = data[1].trim();
                    String cUser = data[2].trim();
                    double gross = Double.parseDouble(data[3].trim());
                    double discount = Double.parseDouble(data[4].trim());
                    double paid = Double.parseDouble(data[6].trim());
                    boolean settled = Boolean.parseBoolean(data[7].trim());

                    Invoice inv = new Invoice(invId, pId, cUser, gross, discount, paid, settled);
                    invoiceRegistry.put(invId, inv);
                }
            }
        } catch (Exception e) {
            System.out.println("Warning: Could not read invoices from file. Initializing defaults.");
        }

        // Seed default invoices if none exist
        if (invoiceRegistry.isEmpty()) {
            seedDefaultInvoices();
            syncInvoicesToFile();
        }
    }

    private void seedDefaultInvoices() {
        Invoice inv1 = new Invoice("INV-501", "PRJ-101", "alpha_client", 155000.0, 5000.0);
        invoiceRegistry.put(inv1.getInvoiceId(), inv1);

        Invoice inv2 = new Invoice("INV-502", "PRJ-102", "alpha_client", 150000.0, 0.0);
        invoiceRegistry.put(inv2.getInvoiceId(), inv2);
    }

    // Synchronizes in-memory invoices with data/invoices.txt
    public void syncInvoicesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileDatabaseManager.INVOICES_FILE, false))) {
            for (Invoice inv : invoiceRegistry.values()) {
                String record = inv.getInvoiceId() + "," + inv.getProjectId() + "," + inv.getClientUsername() + ","
                        + inv.getGrossAmount() + "," + inv.getDiscountAmount() + "," + "0.025," + inv.getPaidAmount() + "," + inv.isFullyPaid();
                writer.write(record);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Invoice storage sync error: " + e.getMessage());
        }
    }

    // OOP Principle: Compile-Time Polymorphism (Method Overloading 1: Standard invoice)
    public Invoice issueInvoice(String projectId, String clientUsername, double grossCost) {
        return issueInvoice(projectId, clientUsername, grossCost, 0.0);
    }

    // OOP Principle: Compile-Time Polymorphism (Method Overloading 2: Invoice with promotional discount)
    public Invoice issueInvoice(String projectId, String clientUsername, double grossCost, double discount) {
        String newId = "INV-" + (500 + invoiceRegistry.size() + 1);
        Invoice inv = new Invoice(newId, projectId, clientUsername, grossCost, discount);
        invoiceRegistry.put(newId, inv);
        syncInvoicesToFile();
        return inv;
    }

    public void displayAllInvoices() {
        System.out.println("\n--- All Corporate Billing Invoices ---");
        for (Invoice inv : invoiceRegistry.values()) {
            inv.printInvoiceSummary();
        }
        System.out.println();
    }

    public void displayClientInvoices(String clientUsername) {
        System.out.println("\n--- Invoices for Client: " + clientUsername + " ---");
        boolean found = false;
        for (Invoice inv : invoiceRegistry.values()) {
            if (inv.getClientUsername().equalsIgnoreCase(clientUsername)) {
                inv.printInvoiceSummary();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No invoices registered for this account.");
        }
        System.out.println();
    }

    public boolean settlePayment(String invoiceId, double paymentAmount) throws InvalidPaymentException {
        Invoice inv = invoiceRegistry.get(invoiceId);
        if (inv == null) {
            System.out.println("Error: Invoice ID [" + invoiceId + "] not found!");
            return false;
        }
        inv.processPayment(paymentAmount);
        syncInvoicesToFile();
        System.out.printf("Payment of LKR %,.2f accepted for Invoice %s. Remaining: LKR %,.2f%n",
                paymentAmount, invoiceId, inv.getRemainingBalance());
        return true;
    }
}