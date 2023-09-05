package com.example.storebackend.Controllers;

import com.example.storebackend.Entities.Utente;
import com.example.storebackend.Security.Utils;
import com.example.storebackend.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginService loginService;


    @GetMapping("/logged")
    public ResponseEntity<Utente> checkLogged(@RequestParam(value = "email") String email,
                                              @RequestParam(value = "nome") String nome,
                                              @RequestParam(value = "cognome") String cognome) {
        Utente currUser=loginService.addOrgetUtente(email,nome,cognome);
        return new ResponseEntity<>(currUser, HttpStatus.OK);
    }
}
