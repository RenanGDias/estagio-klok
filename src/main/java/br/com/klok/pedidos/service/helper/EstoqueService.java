package br.com.klok.pedidos.service.helper;

import br.com.klok.pedidos.model.Pedido;
import br.com.klok.pedidos.model.Item;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {
    public boolean verificarDisponibilidade(Pedido pedido) {
        return pedido.getItems().stream()
                .allMatch(item -> item.getQuantidade() <= item.getEstoque());
    }
}

