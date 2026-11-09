package com.orgme.service;
import com.orgme.model.ConsultationSlot;
import com.orgme.exception.SlotAlreadyBookedException;
import com.orgme.util.FileDatabaseManager;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
// Service handling consultation booking and slot persistence
public class BookingService {
    // OOP Principle: Collections & Generics - Dynamic list holding consultation slots
    private List<ConsultationSlot> slotList;
    public BookingService() {
        this.slotList = new ArrayList<>();
        seedDefaultSlots();
    }
    private void seedDefaultSlots() {
        slotList.add(new ConsultationSlot("SLOT-101", "2026-11-10", "10:00 AM"));
        slotList.add(new ConsultationSlot("SLOT-102", "2026-11-10", "02:00 PM"));
        slotList.add(new ConsultationSlot("SLOT-103", "2026-11-12", "11:00 AM"));
        slotList.add(new ConsultationSlot("SLOT-104", "2026-11-15", "03:30 PM"));
    }
    public void displayAvailableSlots() {
        System.out.println("\n--- Available Architecture Consultation Slots ---");
        for (ConsultationSlot slot : slotList) {
            slot.displaySlotInfo();
        }
        System.out.println();
    }
    // OOP Principle: Compile-Time Polymorphism (Method Overloading 1: Basic booking)
    public boolean bookSlot(String slotId, String clientUsername) throws SlotAlreadyBookedException {
        return bookSlot(slotId, clientUsername, "General Project Discussion");
    }
    // OOP Principle: Compile-Time Polymorphism (Method Overloading 2: Detailed booking with topic)
    public boolean bookSlot(String slotId, String clientUsername, String topicNote) throws SlotAlreadyBookedException {
        ConsultationSlot foundSlot = null;
        for (ConsultationSlot s : slotList) {
            if (s.getSlotId().equalsIgnoreCase(slotId)) {
                foundSlot = s;
                break;
            }
        }
        if (foundSlot == null) {
            System.out.println("Error: Slot ID not found!");
            return false;
        }
        // Exception Handling: Checked business rule violation
        if (foundSlot.isBooked()) {
            throw new SlotAlreadyBookedException("Slot [" + slotId + "] is already booked by " + foundSlot.getBookedByClientUsername());
        }
        foundSlot.setBooked(true);
        foundSlot.setBookedByClientUsername(clientUsername);
        foundSlot.setConsultationTopic(topicNote);
        // Persist booking to bookings.txt
        try {
            String record = foundSlot.getSlotId() + "," + foundSlot.getDate() + "," + foundSlot.getTime() + "," + clientUsername + "," + topicNote;
            FileDatabaseManager.appendRecord(FileDatabaseManager.BOOKINGS_FILE, record);
        } catch (IOException e) {
            System.out.println("Warning: Could not sync booking to storage.");
        }
        return true;
    }
}