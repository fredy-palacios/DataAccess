package org.fredypalacios;

public class Main {
    public static void main(String[] args) {
        String xmlPath = "/Users/fredy/Desktop/contracts.xml";

        DatabaseManager databaseManager = new DatabaseManager();

        try {
            System.out.println("Initializing database setup");
            databaseManager.createTable();
            System.out.println("Successfully initialized database");
        } catch (Exception e) {
            System.out.println("Error initializing database");
        }
    }
}