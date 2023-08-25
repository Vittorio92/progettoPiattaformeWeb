package com.example.storebackend.Support.Exceptions;



public class NessunProdottoException extends Exception{
    public NessunProdottoException() {
        super();
        System.out.println("Nessun prodotto trovato.");
    }
}
