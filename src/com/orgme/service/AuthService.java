package com.orgme.service;
import java.io.*;

import com.orgme.model.User;
import com.orgme.model.Client;
import com.orgme.model.Staff;
import com.orgme.util.FileDatabaseManager;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

// Service layer handling authentication & user registration
public class AuthService {
    // OOP Principle: Collections & Generics - In-memory Map registry for O(1) lookups
    private Map<String, User> userRegistry;

    public AuthService() {
        this.userRegistry = new HashMap<>();
        loadUsersFromDatabase();
    }

    // Reads user records from FileDatabaseManager and populates registry
    private void loadUsersFromDatabase() {
        try {
            List<String[]> records = FileDatabaseManager.readAllRecords(FileDatabaseManager.USERS_FILE);
            for (String[] data : records) {
                if (data.length >= 5) {
                    String id = data[0].trim();
                    String uName = data[1].trim();
                    String pwd = data[2].trim();
                    String role = data[3].trim();
                    String extra = data[4].trim();

                    if ("CLIENT".equalsIgnoreCase(role)) {
                        userRegistry.put(uName, new Client(id, uName, pwd, extra, 50000.0));
                    } else if ("STAFF".equalsIgnoreCase(role)) {
                        userRegistry.put(uName, new Staff(id, uName, pwd, extra));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: Could not read users from storage. Using default seeding.");
        }

        // Seed default demo accounts if registry is empty
        if (!userRegistry.containsKey("sandaru")) {
            Staff defaultStaff = new Staff("STF01", "sandaru", "admin123", "Lead Architect");
            registerUser(defaultStaff);
        }
        if (!userRegistry.containsKey("alpha_client")) {
            Client defaultClient = new Client("CL01", "alpha_client", "pass123", "Alpha Corp", 150000.0);
            registerUser(defaultClient);
        }
    }

    // Registers a User object into memory and persists to users.txt
    public boolean registerUser(User user) {
        if (userRegistry.containsKey(user.getUsername())) {
            return false; // Username already taken
        }
        userRegistry.put(user.getUsername(), user);

        // Persist to users.txt file
        try {
            String extraField = (user instanceof Client) ? ((Client) user).getCompanyName() : ((Staff) user).getSpecialization();
            String record = user.getUserId() + "," + user.getUsername() + "," + "protected" + "," + user.getRole() + "," + extraField;
            FileDatabaseManager.appendRecord(FileDatabaseManager.USERS_FILE, record);
        } catch (IOException e) {
            System.out.println("Database sync error: " + e.getMessage());
        }
        return true;
    }

    // Convenience method for Client registration
    public boolean registerClient(String username, String password, String companyName) {
        if (userRegistry.containsKey(username)) {
            return false;
        }
        String generatedId = "CL" + (userRegistry.size() + 1);
        Client client = new Client(generatedId, username, password, companyName, 25000.0);
        return registerUser(client);
    }

    // Authenticates user by username and password
    public User login(String username, String password) {
        User user = userRegistry.get(username);
        if (user != null && user.validatePassword(password)) {
            return user;
        }
        return null;
    }
}