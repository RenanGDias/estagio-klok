package br.com.klok.pedidos.service.helper;

import br.com.klok.pedidos.model.Pedido;
import br.com.klok.pedidos.model.ItemPedido;
import org.springframework.stereotype.Service;

@Service
public class TotalService {
    public double calcularTotal(Pedido pedido) {
        return pedido.getItens().stream()
                .mapToDouble(itemPedido ->
                        itemPedido.getItem().getPreco() * itemPedido.getQuantidade())
                .sum();
    }

    public double aplicarDescontoVip(double total) {
        return total * 0.9;
    }
}
