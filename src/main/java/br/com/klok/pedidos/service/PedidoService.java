package br.com.klok.pedidos.service;

import org.springframework.stereotype.Service;

import br.com.klok.pedidos.service.helper.EstoqueService;
import br.com.klok.pedidos.service.helper.NotificacaoService;
import br.com.klok.pedidos.service.helper.TotalService;
import br.com.klok.pedidos.model.Pedido;
import java.util.List;
import java.time.LocalDate;

@Service
public class PedidoService {
    private final TotalService totalService;
    private final EstoqueService estoqueService;
    private final NotificacaoService notificacaoService;

    public PedidoService(TotalService totalService, EstoqueService estoqueService, NotificacaoService notificacaoService) {
        this.totalService = totalService;
        this.estoqueService = estoqueService;
        this.notificacaoService = notificacaoService;
    }

    public void processarPedidos(List<Pedido> pedidos) {
        for (Pedido pedido : pedidos) {
            double total = totalService.calcularTotal(pedido);
            pedido.setTotal(total);

            if (pedido.getCliente().isVip()) {
                total = totalService.aplicarDescontoVip(total);
            }
            pedido.setTotalComDesconto(total);

            boolean emEstoque = estoqueService.verificarDisponibilidade(pedido);
            pedido.setEmEstoque(emEstoque);

            pedido.setDataEntrega(emEstoque ? LocalDate.now().plusDays(3) : null);

            String mensagem = emEstoque
                    ? "Seu pedido será entregue em breve."
                    : "Um ou mais itens do seu pedido estão fora de estoque.";

            notificacaoService.enviar(pedido.getCliente().getEmail(), mensagem);
        }
    }
}

