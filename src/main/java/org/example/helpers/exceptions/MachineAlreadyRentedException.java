package org.example.helpers.exceptions;

public class MachineAlreadyRentedException extends Exception{
    public MachineAlreadyRentedException(String message){
        super(message);
    }
}
