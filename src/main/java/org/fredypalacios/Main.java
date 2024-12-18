package org.fredypalacios;

public class Main {
    public static void main(String[] args) {
        String xmlPath = "/Users/fredy/Desktop/contracts.xml";
        String xmlPathOutput = "/Users/fredy/Desktop/contracts_modified.xml";

        DatabaseManager databaseManager = new DatabaseManager();
        XMLCleaner xmlCleaner = new XMLCleaner();

        try {
            // Create database
            databaseManager.createTable();

            // Insert data from xml to database
            databaseManager.insertData(xmlPath);

            // Remove "tipo de contrato" column and its rows from the XML file
            xmlCleaner.removeContractTypeData(xmlPath, xmlPathOutput);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}