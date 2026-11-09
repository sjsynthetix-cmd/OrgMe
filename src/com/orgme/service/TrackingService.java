package com.orgme.service;
import com.orgme.model.ProjectOrder;
import com.orgme.exception.ProjectNotFoundException;
import com.orgme.util.FileDatabaseManager;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
// Service handling project lifecycle tracking and Google Play testing cycles
public class TrackingService {
    // OOP Principle: Collections & Generics - Key-Value Map for O(1) project retrieval
    private Map<String, ProjectOrder> projectMap;
    public TrackingService() {
        this.projectMap = new HashMap<>();
        seedDefaultProjects();
    }
    private void seedDefaultProjects() {
        ProjectOrder p1 = new ProjectOrder("PRJ-101", "alpha_client", "MOBILE", true);
        p1.incrementTestingDay();
        p1.incrementTestingDay();
        projectMap.put(p1.getProjectId(), p1);
        ProjectOrder p2 = new ProjectOrder("PRJ-102", "alpha_client", "WEB", false);
        p2.setCurrentPhase("READY_FOR_DEPLOYMENT");
        projectMap.put(p2.getProjectId(), p2);
    }
    public ProjectOrder findProjectById(String projectId) throws ProjectNotFoundException {
        ProjectOrder project = projectMap.get(projectId);
        if (project == null) {
            throw new ProjectNotFoundException("Project with ID [" + projectId + "] was not found in system repository!");
        }
        return project;
    }
    public void displayAllProjects() {
        System.out.println("\n--- All Monitored Company Projects ---");
        for (ProjectOrder order : projectMap.values()) {
            order.displayTestingStatus();
            System.out.println("--------------------------------------------------------------------------------");
        }
    }
    public void displayClientProjects(String clientUsername) {
        System.out.println("\n--- Your Active Project Orders (" + clientUsername + ") ---");
        boolean found = false;
        for (ProjectOrder order : projectMap.values()) {
            if (order.getClientUsername().equalsIgnoreCase(clientUsername)) {
                order.displayTestingStatus();
                System.out.println("--------------------------------------------------------------------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No active projects found for this account.");
        }
    }
    // Advances the closed testing day for Play Store compliance
    public void advanceTestingDay(String projectId) throws ProjectNotFoundException {
        ProjectOrder project = findProjectById(projectId);
        project.incrementTestingDay();
        System.out.printf("Project %s testing advanced to Day %d/14!%n", projectId, project.getClosedTestingDay());
        // Sync with projects.txt
        try {
            String record = project.getProjectId() + "," + project.getClientUsername() + "," + project.getPackageType() + "," 
                          + project.getCurrentPhase() + "," + project.getClosedTestingDay() + "," + project.getActiveTesterCount() + "," + project.isTestingApproved();
            FileDatabaseManager.appendRecord(FileDatabaseManager.PROJECTS_FILE, record);
        } catch (IOException e) {
            System.out.println("Storage sync error: " + e.getMessage());
        }
    }
    // OOP Principle: Compile-Time Polymorphism (Method Overloading 1)
    public void updateProjectPhase(String projectId, String newPhase) throws ProjectNotFoundException {
        updateProjectPhase(projectId, newPhase, "Phase transitioned to " + newPhase);
    }
    // OOP Principle: Compile-Time Polymorphism (Method Overloading 2)
    public void updateProjectPhase(String projectId, String newPhase, String remarks) throws ProjectNotFoundException {
        ProjectOrder project = findProjectById(projectId);
        project.setCurrentPhase(newPhase);
        project.setEngineerRemarks(remarks);
        System.out.println("Project " + projectId + " phase successfully updated to: " + newPhase);
    }
}
