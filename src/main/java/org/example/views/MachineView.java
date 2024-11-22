package org.example.views;

import org.example.controller.MachineController;
import org.example.entities.Client;
import org.example.entities.Machine;

import java.util.List;
import java.util.Scanner;

public class MachineView {

    private final MachineController machineController;
    private final Scanner scanner = new Scanner(System.in);

    public MachineView(MachineController machineController) {
        this.machineController = machineController;
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n=== Machines Menu ===");
            System.out.println("1. Register Machine");
            System.out.println("2. Show All Machines");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> registerMachine();
                case 2 -> showAllMachines();
                case 3 -> { return; }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void registerMachine() {
        System.out.print("Enter model: ");
        String model = scanner.nextLine();

        System.out.print("Enter serial number: ");
        String serialNumber = scanner.nextLine();

        System.out.print("Enter status (available/rented): ");
        String status = scanner.nextLine();

        machineController.addMachine(model, serialNumber, status);
        System.out.println("Machine registered successfully!");
    }

    private void showAllMachines() {
        // New fetch the total number of clients
        int totalMachines = machineController.getTotalMachines();
        int pageSize = 5;
        int totalPages = (totalMachines + pageSize - 1)/ pageSize;

        System.out.println("Total machines registered: " + totalMachines +
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
        List<Machine> machines = machineController.getAllMachine(page);
        if (machines.isEmpty()){
            System.out.println("No machines found on this page...");
        } else {

            machines.forEach(machine ->
                    System.out.println(machine.toString())
            );
        }
    }
}
