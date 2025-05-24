package br.com.klok.pedidos.service.helper;

import br.com.klok.pedidos.model.Pedido;
import br.com.klok.pedidos.model.ItemPedido;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {
    public boolean verificarDisponibilidade(Pedido pedido) {
        return pedido.getItens().stream()
                .allMatch(itemPedido ->
                        itemPedido.getQuantidade() <= itemPedido.getItem().getEstoque());
    }
}
