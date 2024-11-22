package org.example.views;

import java.util.Scanner;

public class MainMenu {

    private final ClientView clientView;
    private final MachineView machineView;
    private final RentView rentView;
    private final Scanner scanner = new Scanner(System.in);

    public MainMenu(ClientView clientView, MachineView machineView, RentView rentView) {
        this.clientView = clientView;
        this.machineView = machineView;
        this.rentView = rentView;
    }

    public void display() {
        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Manage Clients");
            System.out.println("2. Manage Machines");
            System.out.println("3. Manage Rents");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> clientView.displayMenu();
                case 2 -> machineView.displayMenu();
                case 3 -> rentView.displayMenu();
                case 4 -> exit();
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void exit() {
        System.out.println("Exiting the system. Goodbye!");
        System.exit(0);
    }

}
