package org.example.views;

import org.example.controller.ClientController;
import org.example.entities.Client;

import java.util.List;
import java.util.Scanner;

public class ClientView {

    private final ClientController clientController;
    private final Scanner scanner = new Scanner(System.in);

    public ClientView(ClientController clientController) {
        this.clientController = clientController;
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n=== Clients Menu ===");
            System.out.println("1. Register Client");
            System.out.println("2. Show All Clients");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> registerClient();
                case 2 -> showAllClients();
                case 3 -> { return; }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void registerClient() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        clientController.addClient(name, email, phone, address);
        System.out.println("Client registered successfully!");
    }

    private void showAllClients() {

        // New fetch the total number of clients
        int totalClients = clientController.getTotalClients();
        int pageSize = 5;
        int totalPages = (totalClients + pageSize - 1)/ pageSize;

        System.out.println("Total clients registered: " + totalClients +
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
        List<Client> clients = clientController.getAllClients(page);
        if (clients.isEmpty()){
            System.out.println("No clients found on this page...");
        } else {

            clients.forEach(client ->
                    System.out.println(client.toString())
            );
        }

    }
}
