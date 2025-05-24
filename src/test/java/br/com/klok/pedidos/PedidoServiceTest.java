package br.com.klok.pedidos;

import br.com.klok.pedidos.model.Cliente;
import br.com.klok.pedidos.model.Item;
import br.com.klok.pedidos.model.Pedido;
import br.com.klok.pedidos.service.PedidoService;
import br.com.klok.pedidos.service.helper.EstoqueService;
import br.com.klok.pedidos.service.helper.NotificacaoService;
import br.com.klok.pedidos.service.helper.TotalService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private TotalService totalService;

    @Mock
    private EstoqueService estoqueService;

    @Mock
    private NotificacaoService notificacaoService;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente();
        cliente.setEmail("cliente@email.com");
        cliente.setVip(true);

        Item item = new Item("Produto", 10.0, 2, 5);

        pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setItens(List.of(item));
    }

    @Test
    void deveProcessarPedidoComItensEmEstoqueParaClienteVip() {
        when(totalService.calcularTotal(pedido)).thenReturn(20.0);
        when(estoqueService.verificarDisponibilidade(pedido)).thenReturn(true);

        pedidoService.processarPedidos(List.of(pedido));

        assertEquals(20.0, pedido.getTotal());
        assertEquals(18.0, pedido.getTotalComDesconto()); // 10% desconto
        assertTrue(pedido.isEmEstoque());
        assertEquals(LocalDate.now().plusDays(3), pedido.getDataEntrega());

        verify(notificacaoService).enviar(eq("cliente@email.com"), contains("entregue"));
    }

    @Test
    void deveProcessarPedidoComItensForaDeEstoque() {
        when(totalService.calcularTotal(pedido)).thenReturn(20.0);
        when(estoqueService.verificarDisponibilidade(pedido)).thenReturn(false);

        pedidoService.processarPedidos(List.of(pedido));

        assertEquals(20.0, pedido.getTotal());
        assertEquals(18.0, pedido.getTotalComDesconto());
        assertFalse(pedido.isEmEstoque());
        assertNull(pedido.getDataEntrega());

        verify(notificacaoService).enviar(eq("cliente@email.com"), contains("fora de estoque"));
    }

	@Test
	void deveProcessarPedidoComItensEmEstoqueParaClienteNaoVip() {
		Cliente clienteNaoVip = new Cliente();
		clienteNaoVip.setEmail("naovip@email.com");
		clienteNaoVip.setVip(false);

		Item item = new Item("Produto", 10.0, 2, 5);
		Pedido pedidoNaoVip = new Pedido();
		pedidoNaoVip.setCliente(clienteNaoVip);
		pedidoNaoVip.setItens(List.of(item));

		when(totalService.calcularTotal(pedidoNaoVip)).thenReturn(20.0);
		when(estoqueService.verificarDisponibilidade(pedidoNaoVip)).thenReturn(true);

		pedidoService.processarPedidos(List.of(pedidoNaoVip));

		assertEquals(20.0, pedidoNaoVip.getTotal());
		assertEquals(20.0, pedidoNaoVip.getTotalComDesconto()); // Sem desconto
		assertTrue(pedidoNaoVip.isEmEstoque());
		assertEquals(LocalDate.now().plusDays(3), pedidoNaoVip.getDataEntrega());

		verify(notificacaoService).enviar(eq("naovip@email.com"), contains("entregue"));
	}

	@Test
	void deveProcessarMultiplosPedidos() {
		Cliente clienteVip = new Cliente();
		clienteVip.setEmail("vip@email.com");
		clienteVip.setVip(true);

		Cliente clienteNaoVip = new Cliente();
		clienteNaoVip.setEmail("naovip@email.com");
		clienteNaoVip.setVip(false);

		Item item1 = new Item("Produto A", 5.0, 2, 10);
		Item item2 = new Item("Produto B", 10.0, 1, 10);

		Pedido pedidoVip = new Pedido();
		pedidoVip.setCliente(clienteVip);
		pedidoVip.setItens(List.of(item1));

		Pedido pedidoNaoVip = new Pedido();
		pedidoNaoVip.setCliente(clienteNaoVip);
		pedidoNaoVip.setItens(List.of(item2));

		when(totalService.calcularTotal(pedidoVip)).thenReturn(10.0);
		when(totalService.calcularTotal(pedidoNaoVip)).thenReturn(10.0);
		when(estoqueService.verificarDisponibilidade(any(Pedido.class))).thenReturn(true);

		pedidoService.processarPedidos(List.of(pedidoVip, pedidoNaoVip));

		// Pedido VIP
		assertEquals(10.0, pedidoVip.getTotal());
		assertEquals(9.0, pedidoVip.getTotalComDesconto());
		assertTrue(pedidoVip.isEmEstoque());

		// Pedido não VIP
		assertEquals(10.0, pedidoNaoVip.getTotal());
		assertEquals(10.0, pedidoNaoVip.getTotalComDesconto());
		assertTrue(pedidoNaoVip.isEmEstoque());

		verify(notificacaoService).enviar(eq("vip@email.com"), contains("entregue"));
		verify(notificacaoService).enviar(eq("naovip@email.com"), contains("entregue"));
	}


}
