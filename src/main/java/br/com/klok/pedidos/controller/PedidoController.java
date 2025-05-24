package br.com.klok.pedidos.controller;

import br.com.klok.pedidos.dto.request.PedidoRequestDTO;
import br.com.klok.pedidos.dto.response.PedidoResponseDTO;
import br.com.klok.pedidos.model.Pedido;
import br.com.klok.pedidos.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/criar")
    public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestBody PedidoRequestDTO pedidoDto) {
        PedidoResponseDTO response = pedidoService.salvarPedido(pedidoDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) {
        PedidoResponseDTO pedido = pedidoService.buscarPorId(id);
        return pedido != null ? ResponseEntity.ok(pedido) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        pedidoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
