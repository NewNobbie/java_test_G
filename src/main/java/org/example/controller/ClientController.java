package org.example.controller;

import org.example.DAO.ClientDAO;
import org.example.entities.Client;

import java.util.List;

public class ClientController {

    private ClientDAO clientDAO;

    public ClientController(){
        this.clientDAO = new ClientDAO();
    }

    public void addClient(String name, String email, String phone, String address){
        Client newClient = new Client();
        newClient.setName(name);
        newClient.setEmail(email);
        newClient.setPhone(phone);
        newClient.setAddress(address);


        this.clientDAO.addClient(newClient);
    }

    public List<Client> getAllClients(int page){
        int pageDefault = page;
        int pageSizeDefault = 5;

        return this.clientDAO.getAllClients(pageDefault, pageSizeDefault);
    }

    public int getTotalClients(){
        return this.clientDAO.getTotalClients();
    }

}
