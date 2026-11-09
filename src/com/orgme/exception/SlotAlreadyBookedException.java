package com.orgme.exception;
// OOP Principle: Custom Checked Exception - Prevents double booking of consultation slots
public class SlotAlreadyBookedException extends Exception {
    public SlotAlreadyBookedException(String message) {
        super(message);
    }
}