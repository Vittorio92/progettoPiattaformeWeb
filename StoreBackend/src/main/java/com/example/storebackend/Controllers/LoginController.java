package com.example.storebackend.Controllers;

import com.example.storebackend.Entities.Utente;
import com.example.storebackend.Security.Utils;
import com.example.storebackend.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginService loginService;


    @GetMapping("/logged")
    public ResponseEntity<Utente> checkLogged() {
        String email= Utils.getEmail();
        String[] nominativo=Utils.getName();
        String nome=nominativo[0];
        String cognome=nominativo[1];
        Utente currUser=loginService.addOrgetUtente(email,nome,cognome);
        return new ResponseEntity<>(currUser, HttpStatus.OK);
    }
}
