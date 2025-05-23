package br.com.klok.pedidos.service.helper;

import br.com.klok.pedidos.model.Pedido;
import br.com.klok.pedidos.model.Item;
import org.springframework.stereotype.Service;

@Service
public class TotalService {
    public double calcularTotal(Pedido pedido) {
        return pedido.getItems().stream()
                .mapToDouble(item -> item.getPreco() * item.getQuantidade())
                .sum();
    }

    public double aplicarDescontoVip(double total) {
        return total * 0.9;
    }
}
