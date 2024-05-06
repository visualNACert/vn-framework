package com.visualnacert.reto.reto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reto")
public class Controller {

    @GetMapping
    public void reto() {
        System.out.println("Reto");
    }
}
