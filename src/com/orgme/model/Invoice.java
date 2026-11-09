package com.orgme.model;
import com.orgme.exception.InvalidPaymentException;
import com.orgme.util.Payable;
// OOP Principle: Interface Implementation - Invoice implements Payable contract

public class Invoice implements Payable {

    private String invoiceId;
    private String projectId;
    private String clientUsername;
    private double grossAmount;
    private double discountAmount;
    private double taxRate; // 0.025 (2.5% Tech VAT)
    private double paidAmount;
    private boolean isSettled;

    public Invoice(String invoiceId, String projectId, String clientUsername, double grossAmount, double discountAmount) {
        this.invoiceId = invoiceId;
        this.projectId = projectId;
        this.clientUsername = clientUsername;
        this.grossAmount = grossAmount;
        this.discountAmount = discountAmount;
        this.taxRate = 0.025; // 2.5% standard tax
        this.paidAmount = 0.0;
        this.isSettled = false;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public double getGrossAmount() {
        return grossAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    // Calculates taxable total after discount plus tax
    @Override
    public double calculateNetPayable() {
        double taxable = grossAmount - discountAmount;
        return taxable + (taxable * taxRate);
    }

    public double getRemainingBalance() {
        return calculateNetPayable() - paidAmount;
    }

    // OOP Principle: Encapsulation & Exception Handling
    @Override
    public void processPayment(double paymentAmount) throws InvalidPaymentException {
        if (paymentAmount <= 0) {
            throw new InvalidPaymentException("Payment must be greater than zero!");
        }
        if (paymentAmount > getRemainingBalance()) {
            throw new InvalidPaymentException("Payment of LKR " + paymentAmount + " exceeds remaining balance of LKR " + getRemainingBalance());
        }
        this.paidAmount += paymentAmount;
        if (getRemainingBalance() <= 0.01) {
            this.isSettled = true;
        }
    }

    @Override
    public boolean isFullyPaid() {
        return isSettled;
    }

    public void printInvoiceSummary() {
        System.out.printf("[%s] Project: %s | Client: %s | Gross: LKR %,.2f | Disc: LKR %,.2f | Net: LKR %,.2f | Paid: LKR %,.2f | Status: %s%n",
                invoiceId, projectId, clientUsername, grossAmount, discountAmount, calculateNetPayable(), paidAmount,
                (isSettled ? "SETTLED" : "UNPAID (Bal: LKR " + String.format("%,.2f", getRemainingBalance()) + ")"));
    }

}

