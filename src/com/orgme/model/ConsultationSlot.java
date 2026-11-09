package com.orgme.model;
// OOP Principle: Encapsulation - Data model for consultation booking slots
public class ConsultationSlot {
    private String slotId;
    private String date;
    private String time;
    private boolean isBooked;
    private String bookedByClientUsername;
    private String consultationTopic;
    // Constructor to initialize an available slot
    public ConsultationSlot(String slotId, String date, String time) {
        this.slotId = slotId;
        this.date = date;
        this.time = time;
        this.isBooked = false;
        this.bookedByClientUsername = null;
        this.consultationTopic = null;
    }
    // Getters and Setters
    public String getSlotId() { return slotId; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public boolean isBooked() { return isBooked; }
    public String getBookedByClientUsername() { return bookedByClientUsername; }
    public String getConsultationTopic() { return consultationTopic; }
    public void setBooked(boolean booked) { this.isBooked = booked; }
    public void setBookedByClientUsername(String username) { this.bookedByClientUsername = username; }
    public void setConsultationTopic(String topic) { this.consultationTopic = topic; }
    // Displays formatted slot details
    public void displaySlotInfo() {
        String status = isBooked ? ("BOOKED by " + bookedByClientUsername + (consultationTopic != null ? " (" + consultationTopic + ")" : "")) : "AVAILABLE";
        System.out.printf("[%s] Date: %s | Time: %s | Status: %s%n", slotId, date, time, status);
    }
}