package org.example.controller;

import org.example.DAO.MachineDAO;
import org.example.entities.Machine;
import org.example.enums.Status;

import java.util.List;

public class MachineController {

    private MachineDAO machineDAO;

    public MachineController(){
        this.machineDAO = new MachineDAO();
    }

    public void addMachine(String model, String serialNumber, String status){
        Machine newMachine = new Machine();
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
}
