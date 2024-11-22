package org.example;

import org.example.controller.ClientController;
import org.example.controller.MachineController;
import org.example.controller.RentController;
import org.example.views.ClientView;
import org.example.views.MachineView;
import org.example.views.MainMenu;
import org.example.views.RentView;

public class Main {

    public static void main(String[] args) {
        ClientController clientController = new ClientController();
        MachineController machineController = new MachineController();
        RentController rentController = new RentController();

        ClientView clientView = new ClientView(clientController);
        MachineView machineView = new MachineView(machineController);
        RentView rentView = new RentView(rentController, machineController);

        MainMenu mainMenu = new MainMenu(clientView, machineView, rentView);
        mainMenu.display();
    }
}