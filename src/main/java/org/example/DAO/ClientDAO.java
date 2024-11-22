package org.example.DAO;

import org.example.entities.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO extends BaseDAO{

    public void addClient(Client client){
        String sql = "INSERT INTO Clients (name, email, phone, address) VALUES(?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setString(3, client.getPhone());
            statement.setString(4, client.getAddress());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Client> getAllClients(int pageDefault, int pageSizeDefault){
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM Clients LIMIT ? OFFSET ?";
        int offset = (pageDefault - 1) * pageSizeDefault; // Starting point for the current page

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, pageSizeDefault); // Set the limit (number of rows to fetch)
            statement.setInt(2, offset);   // Set the offset (starting point)

            try (ResultSet rs = statement.executeQuery()){
                while (rs.next()) {
                    clients.add(mapResultSetToClient(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public int getTotalClients(){
        String sql = "SELECT COUNT(*) AS total FROM Clients";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Return 0 if there was an issue
    }

    private Client mapResultSetToClient(ResultSet rs) throws SQLException {
        return new Client(
                rs.getInt("client_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("address")
        );
    }
}
