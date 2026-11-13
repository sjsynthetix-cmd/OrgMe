package com.orgme.service;

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
                        double budgetLimit = 200000.0; // Default starter tier allowance
                        if (data.length >= 6) {
                            try {
                                budgetLimit = Double.parseDouble(data[5].trim());
                            } catch (NumberFormatException ignored) {}
                        }
                        userRegistry.put(uName, new Client(id, uName, pwd, extra, budgetLimit));
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
            Client defaultClient = new Client("CL01", "alpha_client", "pass123", "Alpha Corp", 200000.0);
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
            String record;
            if (user instanceof Client) {
                Client c = (Client) user;
                record = c.getUserId() + "," + c.getUsername() + "," + c.getPassword() + "," + c.getRole() + "," + c.getCompanyName() + "," + c.getBudgetLimit();
            } else {
                Staff s = (Staff) user;
                record = s.getUserId() + "," + s.getUsername() + "," + s.getPassword() + "," + s.getRole() + "," + s.getSpecialization();
            }
            FileDatabaseManager.appendRecord(FileDatabaseManager.USERS_FILE, record);
        } catch (IOException e) {
            System.out.println("Database sync error: " + e.getMessage());
        }
        return true;
    }

    // OOP Principle: Method Overloading 1 - Client registration with Custom Project Budget
    public boolean registerClient(String username, String password, String companyName, double budgetLimit) {
        if (userRegistry.containsKey(username)) {
            return false;
        }
        String generatedId = "CL" + (userRegistry.size() + 1);
        Client client = new Client(generatedId, username, password, companyName, budgetLimit);
        return registerUser(client);
    }

    // OOP Principle: Method Overloading 2 - Client registration with Default Starter Budget (LKR 200,000.00)
    public boolean registerClient(String username, String password, String companyName) {
        return registerClient(username, password, companyName, 200000.0);
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