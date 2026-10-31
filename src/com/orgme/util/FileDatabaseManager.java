package com.orgme.util;

import java.io.*;
import java.util.*;

public class FileDatabaseManager {
    public static final String DATA_DIR = "data/";
    public static final String USERS_FILE = "data/users.txt";
    public static final String BOOKINGS_FILE = "data/bookings.txt";
    public static final String PROJECTS_FILE = "data/projects.txt";
    public static final String INVOICES_FILE = "data/invoices.txt";

    // user
    public static void initializeDatabase() {
        try {
            // create folder
            File folder = new File(DATA_DIR);
            if (!folder.exists()) {
                folder.mkdir();
            }
            
            // create files
            File userFile = new File(USERS_FILE);
            if (!userFile.exists()) {
                userFile.createNewFile();
            }   
            
            File bookingsFile = new File(BOOKINGS_FILE);
            if (!bookingsFile.exists()) {
                bookingsFile.createNewFile(); 
            }   
            
            File projectsFile = new File(PROJECTS_FILE);
            if (!projectsFile.exists()) {
                projectsFile.createNewFile();    
            }
            
            File invoicesFile = new File(INVOICES_FILE);
            if (!invoicesFile.exists()) {
                invoicesFile.createNewFile();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    } 

    public static void appendRecord (String path, String details)throws IOException {
        try( BufferedWriter writeRecord = new BufferedWriter(new FileWriter(path, true))) {
           
            writeRecord.append(details);
            writeRecord.newLine();
            
        }   
    }
    
    public static List<String[]> readAllRecords(String path)throws IOException{
        List<String[]> records = new ArrayList<>();//collection
        File file = new File(path);
        
        if(!file.exists()){
                return records;
            }
        
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            
            while((line=reader.readLine())!= null){
                if(!line.trim().isEmpty()){
                    String[] fields = line.split(",");
                    records.add(fields);
                    
                }
                
            }
            
            
        }
        
        return records;
    }
}