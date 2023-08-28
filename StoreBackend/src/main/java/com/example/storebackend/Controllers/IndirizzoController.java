package com.example.storebackend.Controllers;

import com.example.storebackend.Services.IndirizzoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/indirizzo")
@RestController
@CrossOrigin
public class IndirizzoController {

    @Autowired
    private IndirizzoService indirizzoService;
}
