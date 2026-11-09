package com.orgme.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Naweesha
 */
public abstract class User {
    protected String userId;
    protected String username;
    protected String password;
    protected String role;

    
    public User(String userId,String username,String password,String role){
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public String getUserId(){
        return userId;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getRole(){
        return role;
    }
    public boolean validatePassword(String inputPassword){
        return this.password.equals(inputPassword);
    }
    public abstract void displayDashboard();
    
}
