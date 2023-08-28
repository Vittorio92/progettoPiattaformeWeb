package com.example.storebackend.Support.Exceptions;

public class ProdottoEsauritoException extends Exception{
    public ProdottoEsauritoException() {
        super();
        System.out.println("Prodotto esaurito!");
    }
}
