package com.orgme.service;

// Analyzes requirements and outputs an AI feasibility report

public class AIEstimatorEngine {


        public void runAiEstimation(String projectType, int expectedUsers, boolean needsPlayStoreTesting) {
            System.out.println("\n--------------------------------------------------------------------------------");
            System.out.println("                 AI PROJECT FEASIBILITY & ESTIMATION REPORT                     ");
            System.out.println("--------------------------------------------------------------------------------");
            if ("MOBILE".equalsIgnoreCase(projectType)) {
                System.out.println("  • Recommended Architecture: Flutter + Clean Architecture + Firebase");
                System.out.println("  • Estimated Core Timeline: 30 Working Days");
                System.out.println("  • Base Development Cost: LKR 120,000.00");
                if (needsPlayStoreTesting) {
                    System.out.println("  • Play Store Compliance: +14 Days Closed Testing with 20 Testers (+LKR 35,000.00)");
                    System.out.println("  • TOTAL RECOMMENDED BUDGET: LKR 155,000.00");
                } else {
                    System.out.println("  • TOTAL RECOMMENDED BUDGET: LKR 120,000.00");
                }
            } else if ("WEB".equalsIgnoreCase(projectType)) {
                System.out.println("  • Recommended Architecture: React + Spring Boot Microservices + PostgreSQL");
                System.out.println("  • Estimated Core Timeline: 45 Working Days");
                if (expectedUsers > 10000) {
                    System.out.println("  • High Concurrency Cloud Cluster Recommended (+LKR 45,000.00)");
                    System.out.println("  • TOTAL RECOMMENDED BUDGET: LKR 195,000.00");
                } else {
                    System.out.println("  • TOTAL RECOMMENDED BUDGET: LKR 150,000.00");
                }
            } else if ("AI".equalsIgnoreCase(projectType)) {
                System.out.println("  • Recommended Architecture: Python FastAPI + LangChain Agent + Vector Database");
                System.out.println("  • Estimated Core Timeline: 60 Working Days");
                System.out.println("  • Dedicated Cloud GPU Training Cluster: YES (+LKR 60,000.00)");
                System.out.println("  • TOTAL RECOMMENDED BUDGET: LKR 240,000.00");
            } else {
                System.out.println("  • Custom Hybrid Enterprise Architecture");
                System.out.println("  • Estimated Baseline: 40 Days | LKR 140,000.00");
            }
            System.out.println("--------------------------------------------------------------------------------\n");
        }
    }


