package org.example.views;

import org.example.controller.MachineController;
import org.example.controller.RentController;
import org.example.entities.Client;
import org.example.entities.Machine;
import org.example.entities.Rent;
import org.example.helpers.exceptions.MachineAlreadyRentedException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class RentView {

    private final RentController rentController;
    private MachineController machineController;
    private final Scanner scanner = new Scanner(System.in);

    public RentView(RentController rentController, MachineController machineController) {
        this.rentController = rentController;
        this.machineController = machineController;
    }

    private Date parseDate(String dateInput){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); // For strict parsing
        try {
            return dateFormat.parse(dateInput);
        } catch (ParseException e) {
            System.err.println("Invalid date format. Please use yyyy-MM-dd.");
            return null; // Return null if parsing fails
        }

    }


    public void displayMenu() {
        while (true) {
            System.out.println("\n=== Rents Menu ===");
            System.out.println("1. Register Rent");
            System.out.println("2. Show All Rents");
            System.out.println("3. Show available Machines");
            System.out.println("4. Disable rent");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                        registerRent();
                        /*try {
                            registerRent();
                        } catch (MachineAlreadyRentedException e) {
                            throw new RuntimeException(e);
                        }*/
                }
                case 2 -> showAllRents();
                case 3 -> showAvailableMachines();
                case 4 -> disableRent();
                case 5 -> { return; }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void registerRent(){
        System.out.print("Enter client ID: ");
        int clientId = scanner.nextInt();

        System.out.print("Enter machine ID: ");
        int machineId = scanner.nextInt();

        System.out.print("Enter start date (yyyy-MM-dd): ");
        String startDateInput = scanner.next();
        Date startDate = parseDate(startDateInput);

        System.out.print("Enter end date (yyyy-MM-dd): ");
        String endDateInput = scanner.next();
        Date endDate = parseDate(endDateInput);

        if (startDate == null || endDate == null){
            System.out.println("Failed to register rent due invalid dates...");
        }

        rentController.addRent(clientId, machineId, startDate, endDate);
        System.out.println("Rent registered successfully!");
    }

    private void showAllRents() {
        // New fetch the total number of rents
        int totalRents = rentController.getTotalRents();
        int pageSize = 5;
        int totalPages = (totalRents + pageSize - 1)/ pageSize;

        System.out.println("Total rents registered: " + totalRents +
                "\nTotal pages available: " + totalPages +
                "\nEnter the page number: ");

        int page = scanner.nextInt();
        scanner.nextLine();

        if (page < 1 || page > totalPages){
            System.out.println("Invalid page number!!" +
                    "\nEnter a number between 1 and " + totalPages);
            return;
        }

        // Search and display clients for the selected page
        List<Rent> rents = rentController.getAllRents(page);
        if (rents.isEmpty()){
            System.out.println("No clients found on this page...");
        } else {

            rents.forEach(rent ->
                    System.out.println(rent.toString())
            );
        }
    }

    private void showAvailableMachines(){

        List<Machine> availableMachines = machineController.showAvailableMachines(); // Fetch available machines

        if (availableMachines.isEmpty()) {
            System.out.println("No available machines at the moment...");
        } else {
            System.out.println("Available Machines:");
            availableMachines.forEach(machine -> System.out.println(machine.toString())); // Display each machine
        }
    }

    private void disableRent(){
        System.out.print("Enter the ID of the rent to deactivate: ");
        int rentId = scanner.nextInt();

        rentController.disableRent(rentId);
        System.out.println("The rent has been deactivated, and the machine is now available.");
    }
}
