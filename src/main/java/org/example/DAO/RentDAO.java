package org.example.DAO;

import org.example.entities.Client;
import org.example.entities.Rent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentDAO extends BaseDAO{

    public void addRent(Rent rent){
        String sql = "INSERT INTO Rents (client_id, machine_id, start_date, end_date) VALUES(?, ?, ?, ?)";
        String updateMachineStatus = "UPDATE Machines SET status = 'rented' WHERE machine_id = ?";

        try(Connection connection = getConnection()){
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, rent.getClient_id());
                statement.setInt(2, rent.getMachine_id());
                statement.setDate(3, new java.sql.Date(rent.getStartDate().getTime()));
                statement.setDate(4, new java.sql.Date(rent.getEndDate().getTime()));
                statement.executeUpdate();
            }

            try(PreparedStatement updateStatement = connection.prepareStatement(updateMachineStatus)){
                updateStatement.setInt(1, rent.getMachine_id());
                updateStatement.executeUpdate();
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Rent> getAllRents(int pageDefault, int pageSize){
        List<Rent> rents = new ArrayList<>();
        String sql = "SELECT * FROM Rents LIMIT ? OFFSET ?";
        int offset = (pageDefault - 1) * pageSize; // Starting point for the current page

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, pageSize); // Set the limit (number of rows to fetch)
            statement.setInt(2, offset);   // Set the offset (starting point)

            try (ResultSet rs = statement.executeQuery()){
                while (rs.next()) {
                    rents.add(mapResultSetToRent(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rents;
    }

    public int getTotalRents(){
        String sql = "SELECT COUNT(*) AS total FROM Rents";
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

    private Rent mapResultSetToRent(ResultSet rs) throws SQLException {
        return new Rent(
                rs.getInt("rent_id"),
                rs.getInt("client_id"),
                rs.getInt("machine_id"),
                rs.getDate("start_date"),
                rs.getDate("end_date"),
                rs.getBoolean("is_active")
        );
    }

    public void softDeleteRent(int rentId){
        String updateRentSql = "UPDATE Rents SET is_active = FALSE WHERE rent_id = ?";
        String updateMachineSql = "UPDATE Machines SET status = 'available' WHERE machine_id = " +
                "(SELECT machine_id FROM Rents WHERE rent_id = ?)";

        try (Connection connection = getConnection();
             PreparedStatement updateRentStatement = connection.prepareStatement(updateRentSql);
             PreparedStatement updateMachineStatement = connection.prepareStatement(updateMachineSql)) {

            // Mark the rent as inactive
            updateRentStatement.setInt(1, rentId);
            updateRentStatement.executeUpdate();

            // Make the machine available again
            updateMachineStatement.setInt(1, rentId);
            updateMachineStatement.executeUpdate();

            System.out.println("Rent with ID " + rentId + " has been disable, and the machine is now available.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
