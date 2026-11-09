package com.orgme.service;
import com.orgme.model.Invoice;
import com.orgme.exception.InvalidPaymentException;
import com.orgme.util.FileDatabaseManager;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

// Service engine managing commercial invoices and payment settlement
public class BillingService {
    // OOP Principle: Collections & Generics - In-memory Map for invoices
    private Map<String, Invoice> invoiceRegistry;
    public BillingService() {
        this.invoiceRegistry = new HashMap<>();
        seedDefaultInvoices();
    }
    private void seedDefaultInvoices() {
        Invoice inv1 = new Invoice("INV-501", "PRJ-101", "alpha_client", 155000.0, 5000.0);
        invoiceRegistry.put(inv1.getInvoiceId(), inv1);
        Invoice inv2 = new Invoice("INV-502", "PRJ-102", "alpha_client", 150000.0, 0.0);
        invoiceRegistry.put(inv2.getInvoiceId(), inv2);
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
        // Sync with invoices.txt
        try {
            String record = inv.getInvoiceId() + "," + inv.getProjectId() + "," + inv.getClientUsername() + ","
                    + inv.getGrossAmount() + "," + inv.getDiscountAmount() + "," + "0.025," + inv.getPaidAmount() + "," + inv.isFullyPaid();
            FileDatabaseManager.appendRecord(FileDatabaseManager.INVOICES_FILE, record);
        } catch (IOException e) {
            System.out.println("Invoice storage sync error: " + e.getMessage());
        }
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
        System.out.printf("Payment of LKR %,.2f accepted for Invoice %s. Remaining: LKR %,.2f%n",
                paymentAmount, invoiceId, inv.getRemainingBalance());
        return true;
    }
}