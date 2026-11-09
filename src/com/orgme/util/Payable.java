package com.orgme.util;


import com.orgme.exception.InvalidPaymentException;
// OOP Principle: Interface - Standard contract for any chargeable financial unit
public interface Payable {
    double calculateNetPayable();
    void processPayment(double paymentAmount) throws InvalidPaymentException;
    boolean isFullyPaid();
}