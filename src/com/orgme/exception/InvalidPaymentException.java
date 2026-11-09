
package com.orgme.exception;

// OOP Principle: Custom Checked Exception - Rejects negative, zero, or overpayments



public class InvalidPaymentException extends Exception {


    public InvalidPaymentException(String message) {
        super(message);


    }
}







