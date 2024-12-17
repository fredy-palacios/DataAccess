package org.fredypalacios;

public class Main {
    public static void main(String[] args) {
        String xmlPath = "/Users/fredy/Desktop/contracts.xml";
        DatabaseManager databaseManager = new DatabaseManager();

        try {
            // Create database
            databaseManager.createTable();

            // Insert data from xml to database
            databaseManager.insertData(xmlPath);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}