package com.orgme.model;

// OOP Principle: Encapsulation - Represents a software project order with Play Store testing lifecycle
public class ProjectOrder {
    private String projectId;
    private String clientUsername;
    private String packageType; // "MOBILE", "WEB", "AI"
    private String currentPhase; // "DEVELOPMENT", "CLOSED_TESTING", "READY_FOR_DEPLOYMENT", "COMPLETED"
    private int closedTestingDay; // 0 to 14 days
    private int activeTesterCount; // Minimum 20 required
    private boolean testingApproved;
    private String engineerRemarks;

    // Constructor for new projects
    public ProjectOrder(String projectId, String clientUsername, String packageType, boolean hasPlayStoreTesting) {
        this.projectId = projectId;
        this.clientUsername = clientUsername;
        this.packageType = packageType;
        this.currentPhase = hasPlayStoreTesting ? "CLOSED_TESTING" : "DEVELOPMENT";
        this.closedTestingDay = 0;
        this.activeTesterCount = hasPlayStoreTesting ? 20 : 0;
        this.testingApproved = !hasPlayStoreTesting; // Web and AI don't need play store testing
        this.engineerRemarks = "Initial order logged.";
    }

    // Overloaded constructor for restoring persisted projects from projects.txt
    public ProjectOrder(String projectId, String clientUsername, String packageType, String currentPhase,
                        int closedTestingDay, int activeTesterCount, boolean testingApproved, String remarks) {
        this.projectId = projectId;
        this.clientUsername = clientUsername;
        this.packageType = packageType;
        this.currentPhase = currentPhase;
        this.closedTestingDay = closedTestingDay;
        this.activeTesterCount = activeTesterCount;
        this.testingApproved = testingApproved;
        this.engineerRemarks = remarks;
    }

    // Getters and Setters
    public String getProjectId() { return projectId; }
    public String getClientUsername() { return clientUsername; }
    public String getPackageType() { return packageType; }
    public String getCurrentPhase() { return currentPhase; }
    public int getClosedTestingDay() { return closedTestingDay; }
    public int getActiveTesterCount() { return activeTesterCount; }
    public boolean isTestingApproved() { return testingApproved; }
    public String getEngineerRemarks() { return engineerRemarks; }

    public void setCurrentPhase(String currentPhase) { this.currentPhase = currentPhase; }
    public void setEngineerRemarks(String engineerRemarks) { this.engineerRemarks = engineerRemarks; }
    public void setClosedTestingDay(int day) { this.closedTestingDay = day; }
    public void setTestingApproved(boolean approved) { this.testingApproved = approved; }

    // Advances testing day and auto-evaluates readiness for deployment
    public void incrementTestingDay() {
        if (closedTestingDay < 14) {
            closedTestingDay++;
        }
        // State Invariant: 14 days + 20 testers required by Google Play policy
        if (closedTestingDay >= 14 && activeTesterCount >= 20) {
            this.testingApproved = true;
            this.currentPhase = "READY_FOR_DEPLOYMENT";
            this.engineerRemarks = "Play Store 14-day 20-tester testing completed successfully. Approved for production!";
        }
    }

    public void displayTestingStatus() {
        System.out.printf("[%s] Client: %s | Type: %s | Phase: %s%n", projectId, clientUsername, packageType, currentPhase);
        if ("MOBILE".equalsIgnoreCase(packageType) || "CLOSED_TESTING".equalsIgnoreCase(currentPhase)) {
            System.out.printf("   Google Play Testing: Day %d/14 | Active Testers: %d/20 | Approved: %s%n", 
                    closedTestingDay, activeTesterCount, (testingApproved ? "YES" : "PENDING"));
            // ASCII Progress Bar
            int progress = (int) ((closedTestingDay / 14.0) * 20);
            System.out.print("   Progress: [");
            for (int i = 0; i < 20; i++) {
                System.out.print(i < progress ? "=" : " ");
            }
            System.out.printf("] %d%%%n", (int) ((closedTestingDay / 14.0) * 100));
        }
        System.out.println("   Remarks: " + engineerRemarks);
    }
}