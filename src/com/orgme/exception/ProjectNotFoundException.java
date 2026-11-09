package com.orgme.exception;
// OOP Principle: Custom Checked Exception - Thrown when requested project ID does not exist
public class ProjectNotFoundException extends Exception {
    public ProjectNotFoundException(String message) {
        super(message);
    }
}
