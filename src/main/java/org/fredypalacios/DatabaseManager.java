package org.fredypalacios;

import java.sql.*;
import java.util.List;

public class DatabaseManager {

    public DatabaseManager() {}

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                AppConfig.DB_URL,
                AppConfig.DB_USER,
                AppConfig.DB_PASSWORD
        );
    }

    //Method to create the table in the database.
    protected void createTable(){
        try (Connection connection = getConnection()) {
            String dropTableSQL = "DROP TABLE IF EXISTS contracts";

            try (PreparedStatement preparedStatement = connection.prepareStatement(dropTableSQL)) {
                preparedStatement.execute();
            }
            System.out.println("Creating table Contracts...");
            String createTableSQL = "CREATE TABLE contracts (" +
                    "nif VARCHAR(255) NULL, " +
                    "adjudicatario VARCHAR(255) NULL, " +
                    "objeto_generico VARCHAR(255) NULL, " +
                    "objeto VARCHAR(255) NULL, " +
                    "fecha_de_adjudicacion VARCHAR(255) NULL, " +
                    "importe VARCHAR (255), " +
                    "proveedores_consultados VARCHAR(255) NULL, " +
                    "tipo_de_contrato VARCHAR(255) NULL" + ")";
            try (PreparedStatement pstmt = connection.prepareStatement(createTableSQL)) {
                pstmt.execute();

            }
        } catch (SQLException e) {
            System.err.println("The connection could not be established while creating the table.");
        }
        System.out.println("Successfully created table Contracts");
    }

    protected void insertData(String xmlPath) {
        try (Connection connection = getConnection()) {
            String insertSQL = "INSERT INTO contracts " +
                    "(nif, adjudicatario, objeto_generico, objeto, fecha_de_adjudicacion, importe, proveedores_consultados, tipo_de_contrato) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            // Parse the XML file to retrieve data
            List<String[]> rowsData = XMLParserToDB.parseXML(xmlPath);

            // Iterate through each row of data
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                System.out.println("Inserting rows into database...");
                int i = 0;
                for (String[] row : rowsData) {
                    preparedStatement.setString(1, row[0]); // nif
                    preparedStatement.setString(2, row[1]); // adjudicatario
                    preparedStatement.setString(3, row[2]); // objeto_generico
                    preparedStatement.setString(4, row[3]); // objeto
                    preparedStatement.setString(5, row[4]); // fecha_de_adjudicacion
                    preparedStatement.setString(6, row[5]); // importe
                    preparedStatement.setString(7, row[6]); // proveedores_consultados
                    preparedStatement.setString(8, row[7]); // tipo_de_contrato

                    preparedStatement.addBatch();
                    i++;
                }
                preparedStatement.executeBatch();
                System.out.printf("Successfully %d inserted rows into database%n", i);

            } catch (SQLException e) {
                System.err.println("Error inserting rows into database");
            }
        } catch (SQLException e) {
            System.err.println("The connection dropped while the data was being inserted.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}