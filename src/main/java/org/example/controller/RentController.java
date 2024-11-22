package org.example.controller;

import org.example.DAO.MachineDAO;
import org.example.DAO.RentDAO;
import org.example.entities.Rent;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class RentController extends BaseController{


    private RentDAO rentDAO;
    private MachineDAO machineDAO;

    public RentController(){
        this.rentDAO = new RentDAO();
        this.machineDAO = new MachineDAO();
    }

    public void addRent(int clientId, int machineId, Date startDate, Date endDate){

        boolean success = false;
        Scanner scanner = new Scanner(System.in);

        while (!success) {
            // Prompt for inputs
            System.out.print("Enter client ID: ");
            clientId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter machine ID: ");
            machineId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter start date (yyyy-MM-dd): ");
            String startDateInput = scanner.next();
            startDate = parseDate(startDateInput);

            System.out.print("Enter end date (yyyy-MM-dd): ");
            String endDateInput = scanner.next();
            endDate = parseDate(endDateInput);

            // Validate dates
            if (startDate == null || endDate == null) {
                System.out.println("Invalid date entered. Please try again.");
                continue;
            }

            // Check machine status
            String machineStatus = machineDAO.getMachineStatus(machineId);
            if ("rented".equalsIgnoreCase(machineStatus)) {
                System.out.println("Machine with ID " + machineId + " is already reserved. Try again.");
                continue;
            } else if (machineStatus == null) {
                System.out.println("Machine with ID " + machineId + " does not exist. Try again.");
                continue;
            }


            if ("available".equalsIgnoreCase(machineStatus)) {
                // Create Rent object
                Rent newRent = new Rent();
                newRent.setClient_id(clientId);
                newRent.setMachine_id(machineId);
                newRent.setStartDate(startDate);
                newRent.setEndDate(endDate);

                // Add rent to the database
                this.rentDAO.addRent(newRent);

                System.out.println("Rent registered successfully!");
                success = true; // Exit the loop
            }
        }
    }

    public List<Rent> getAllRents(int page){
        int pageDefault = page;
        int pageSize = 5;

        return this.rentDAO.getAllRents(pageDefault, pageSize);
    }

    public int getTotalRents(){
        return this.rentDAO.getTotalRents();
    }
}
