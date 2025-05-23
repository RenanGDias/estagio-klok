package br.com.klok.pedidos.controller;

import br.com.klok.pedidos.model.Pedido;
import br.com.klok.pedidos.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<String> processarPedidos(@RequestBody List<Pedido> pedidos) {
        pedidoService.processarPedidos(pedidos);
        return ResponseEntity.ok("Pedidos processados com sucesso.");
    }
}
