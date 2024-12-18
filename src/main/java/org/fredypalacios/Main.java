package org.fredypalacios;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String xmlPath = "/Users/fredy/Desktop/contracts.xml";
        String xmlPathOutput = "/Users/fredy/Desktop/contracts_modified.xml";

        DatabaseManager databaseManager = new DatabaseManager();
        XMLCleaner xmlCleaner = new XMLCleaner();

        try {
            databaseManager.createTable(); // Create database
            databaseManager.insertData(xmlPath); // Insert data from xml to database
        } catch (Exception e) {
            System.err.println("Something went wrong when inserting data");
        }

        try {
            // Remove "tipo de contrato" column and its rows from the XML file
            xmlCleaner.removeContractTypeData(xmlPath, xmlPathOutput);
        }catch (Exception e) {
            System.err.println("Error while removing \"tipo de contrato\" column");
        }
    }
}