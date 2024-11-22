package org.example.controller;

import org.example.DAO.MachineDAO;
import org.example.entities.Machine;
import org.example.enums.Status;
import org.example.helpers.exceptions.Imports.ExcelImporter;

import java.io.IOException;
import java.util.List;

public class MachineController {

    private MachineDAO machineDAO;

    public MachineController(){
        this.machineDAO = new MachineDAO();
    }

    public void addMachine(String model, String serialNumber, String status){
        Machine newMachine = new Machine(model, serialNumber, status);
        newMachine.setModel(model);
        newMachine.setSerialNumber(serialNumber);

        try {
            // Convert String to enum
            newMachine.setStatus(Status.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            // Handle invalid status input
            System.err.println("Invalid status: " + status);
            return;
        }

        this.machineDAO.addMachine(newMachine);
    }

    public List<Machine> getAllMachine(int page){
        int pageDefault = page;
        int pageSize = 5;

        return this.machineDAO.getAllMachines(pageDefault, pageSize);
    }

    public int getTotalMachines(){
        return this.machineDAO.getTotalMachines();
    }

    public List<Machine> showAvailableMachines(){
        List<Machine> availableMachines = machineDAO.getAvailableMachines();

        if (availableMachines.isEmpty()) {
            System.out.println("No available machines at the moment...");
        }

        return availableMachines; // Always return the list, even if empty
    }

    public void importMachinesFromExcel(String filePath) {
        ExcelImporter importer = new ExcelImporter();
        MachineDAO machineDAO = new MachineDAO();

        try {
            List<Machine> machines = importer.importMachinesFromExcel(filePath);
            int totalMachines = machines.size();
            machineDAO.saveMachines(machines);
            System.out.println("Imported " + totalMachines + " machines from " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading the Excel file: " + e.getMessage());
        }
    }
}
