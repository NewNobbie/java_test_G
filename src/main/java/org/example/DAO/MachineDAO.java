package org.example.DAO;

import org.example.entities.Machine;
import org.example.enums.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachineDAO extends BaseDAO{

    public void addMachine(Machine machine){
        String sql = "INSERT INTO Machines (model, serial_number, status) VALUES(?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, machine.getModel());
            statement.setString(2, machine.getSerialNumber());
            statement.setString(3, machine.getStatus().name()); //Mini parse
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Machine> getAllMachines(int pageDefault, int pageSize){
        List<Machine> machines = new ArrayList<>();
        String sql = "SELECT * FROM Machines LIMIT ? OFFSET ?";
        int offset = (pageDefault - 1) * pageSize; // Starting point for the current page

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, pageSize); // Set the limit (number of rows to fetch)
            statement.setInt(2, offset);   // Set the offset (starting point)

            try (ResultSet rs = statement.executeQuery()){
                while (rs.next()) {
                    machines.add(mapResultSetToMachine(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return machines;
    }

    public int getTotalMachines(){
        String sql = "SELECT COUNT(*) AS total FROM Machines";
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

    public String getMachineStatus(int machineId){
        String sql = "SELECT status FROM Machines WHERE machine_id = ?";

        try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);)
        {
            statement.setInt(1, machineId);
            try (ResultSet rs = statement.executeQuery()){
                if (rs.next()){
                    return rs.getString("status");
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Machine> getAvailableMachines(){
        List<Machine> availableMachines = new ArrayList<>();
        String sql = "SELECT * FROM Machines WHERE status = 'available'";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                availableMachines.add(mapResultSetToMachine(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableMachines;
    }



    private Machine mapResultSetToMachine(ResultSet rs) throws SQLException {
        Status status = Status.valueOf(rs.getString("status").toUpperCase());
        return new Machine(
                rs.getInt("machine_id"),
                rs.getString("model"),
                rs.getString("serial_number"),
                status
        );
    }
}
