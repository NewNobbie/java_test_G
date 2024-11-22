package org.example.controller;

import org.example.DAO.MachineDAO;
import org.example.DAO.RentDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseController {


    Date parseDate(String dateInput){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); // For strict parsing
        try {
            return dateFormat.parse(dateInput);
        } catch (ParseException e) {
            System.err.println("Invalid date format. Please use yyyy-MM-dd.");
            return null; // Return null if parsing fails
        }

    }
}
