/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.planmanagmentsystem;

import java.sql.* ;

/**
 *
 * @author WINDOWS 10
 */


public class database {

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/plan", "root", "");
            System.out.println("Connected to database");
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
