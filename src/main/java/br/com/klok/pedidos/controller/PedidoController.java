package br.com.klok.pedidos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PedidoController {

    @GetMapping("/")
    public String hello() {
        return "Hello from Spring Boot!";
    }
}
