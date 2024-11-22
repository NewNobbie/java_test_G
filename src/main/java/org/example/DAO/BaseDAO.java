package org.example.DAO;

import org.example.persistence.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDAO {
    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }
}
