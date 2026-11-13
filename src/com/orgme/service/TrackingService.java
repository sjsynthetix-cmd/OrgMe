package com.orgme.service;

import com.orgme.model.ProjectOrder;
import com.orgme.exception.ProjectNotFoundException;
import com.orgme.util.FileDatabaseManager;

import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

// Service handling project lifecycle tracking and Google Play testing cycles
public class TrackingService {
    // OOP Principle: Collections & Generics - Key-Value Map for O(1) project retrieval
    private Map<String, ProjectOrder> projectMap;

    public TrackingService() {
        this.projectMap = new HashMap<>();
        loadProjectsFromDatabase();
    }

    // Loads persisted projects from projects.txt; seeds defaults if empty
    private void loadProjectsFromDatabase() {
        try {
            List<String[]> records = FileDatabaseManager.readAllRecords(FileDatabaseManager.PROJECTS_FILE);
            for (String[] data : records) {
                if (data.length >= 7) {
                    String pId = data[0].trim();
                    String cUser = data[1].trim();
                    String pType = data[2].trim();
                    String phase = data[3].trim();
                    int testDay = Integer.parseInt(data[4].trim());
                    int testers = Integer.parseInt(data[5].trim());
                    boolean approved = Boolean.parseBoolean(data[6].trim());
                    String remarks = data.length >= 8 ? data[7].trim() : "Monitored active project.";

                    ProjectOrder prj = new ProjectOrder(pId, cUser, pType, phase, testDay, testers, approved, remarks);
                    projectMap.put(pId, prj);
                }
            }
        } catch (Exception e) {
            System.out.println("Warning: Could not read projects from file. Initializing defaults.");
        }

        // Seed default projects if none exist
        if (projectMap.isEmpty()) {
            seedDefaultProjects();
            syncProjectsToFile();
        }
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

    // Synchronizes in-memory projects with data/projects.txt
    public void syncProjectsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileDatabaseManager.PROJECTS_FILE, false))) {
            for (ProjectOrder p : projectMap.values()) {
                String record = p.getProjectId() + "," + p.getClientUsername() + "," + p.getPackageType() + ","
                        + p.getCurrentPhase() + "," + p.getClosedTestingDay() + "," + p.getActiveTesterCount() + ","
                        + p.isTestingApproved() + "," + p.getEngineerRemarks();
                writer.write(record);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Storage sync error: " + e.getMessage());
        }
    }

    // Creates and assigns a new project to any client
    public ProjectOrder createProject(String clientUsername, String packageType, boolean hasPlayStoreTesting) {
        String newId = "PRJ-" + (100 + projectMap.size() + 1);
        ProjectOrder project = new ProjectOrder(newId, clientUsername, packageType, hasPlayStoreTesting);
        projectMap.put(newId, project);
        syncProjectsToFile();
        return project;
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
        syncProjectsToFile();
        System.out.printf("Project %s testing advanced to Day %d/14!%n", projectId, project.getClosedTestingDay());
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
        syncProjectsToFile();
        System.out.println("Project " + projectId + " phase successfully updated to: " + newPhase);
    }
}