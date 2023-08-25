package com.example.storebackend.Support.Exceptions;

public class NessunIndirizzoException extends Exception{

    public NessunIndirizzoException(){
        super();
        System.out.println("L'utente non ha registrato nessun indirizzo");
    }
}
