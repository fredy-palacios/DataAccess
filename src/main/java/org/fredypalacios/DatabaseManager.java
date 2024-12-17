package org.fredypalacios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DatabaseManager {
    String dbUrl = "jdbc:mysql://localhost/contractsDB";
    String dbUser = "root";
    String dbPassword = "";

    public DatabaseManager() {

    }

    public void createTable() throws Exception {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            // drop table if exists
            String dropTableSQL = "DROP TABLE IF EXISTS contracts";
            try (PreparedStatement pstmt = connection.prepareStatement(dropTableSQL)) {
                pstmt.execute();
            }
            // create the table
            String createTableSQL = "CREATE TABLE contracts (" +
                    "nif VARCHAR(255) NOT NULL, " +
                    "adjudicatario VARCHAR(255) NULL, " +
                    "objeto_generico VARCHAR(255) NULL, " +
                    "objeto VARCHAR(255) NULL, " +
                    "fecha_de_adjudicacion VARCHAR(255) NULL, " +
                    "importe DOUBLE PRECISION NULL, " +
                    "proveedores_consultados VARCHAR(255) NULL, " +
                    "tipo_de_contrato VARCHAR(255) NULL, " +
                    "PRIMARY KEY (nif))";
            try (PreparedStatement pstmt = connection.prepareStatement(createTableSQL)) {
                pstmt.execute();

            }
        }
    }

    public void insertData(String xmlPath) {

    }

}
