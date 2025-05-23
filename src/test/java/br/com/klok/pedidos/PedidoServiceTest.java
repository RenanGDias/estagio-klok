package br.com.klok.pedidos;

import br.com.klok.pedidos.model.*;
import br.com.klok.pedidos.service.*;
import br.com.klok.pedidos.service.helper.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

public class PedidoServiceTest {

    @Mock private TotalService totalService;
    @Mock private EstoqueService estoqueService;
    @Mock private NotificacaoService notificacaoService;

    @InjectMocks private PedidoService pedidoService;

    public PedidoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveProcessarPedidoVipEmEstoque() {
        Cliente cliente = new Cliente();
        cliente.setVip(true);
        cliente.setEmail("vip@cliente.com");

        Item item = new Item();
        item.setPreco(100.0);
        item.setQuantidade(2);
        item.setEstoque(10);

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setItems(List.of(item));

        when(totalService.calcularTotal(pedido)).thenReturn(200.0);
        when(totalService.aplicarDescontoVip(200.0)).thenReturn(180.0);
        when(estoqueService.verificarDisponibilidade(pedido)).thenReturn(true);

        pedidoService.processarPedidos(List.of(pedido));

        verify(notificacaoService).enviar(eq("vip@cliente.com"), contains("entregue em breve"));
    }
}
