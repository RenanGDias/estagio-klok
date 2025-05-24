package br.com.klok.pedidos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.klok.pedidos.service.helper.EstoqueService;
import br.com.klok.pedidos.service.helper.NotificacaoService;
import br.com.klok.pedidos.service.helper.TotalService;
import br.com.klok.pedidos.model.Cliente;
import br.com.klok.pedidos.model.Item;
import br.com.klok.pedidos.model.Pedido;
import br.com.klok.pedidos.repository.ClienteRepository;
import br.com.klok.pedidos.repository.ItemRepository;
import br.com.klok.pedidos.repository.PedidoRepository;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

@Service
public class PedidoService {
    private final TotalService totalService;
    private final EstoqueService estoqueService;
    private final NotificacaoService notificacaoService;
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ItemRepository itemRepository;

    public PedidoService(
        TotalService totalService,
        EstoqueService estoqueService,
        NotificacaoService notificacaoService,
        PedidoRepository pedidoRepository,
        ClienteRepository clienteRepository,
        ItemRepository itemRepository
    ) {
        this.totalService = totalService;
        this.estoqueService = estoqueService;
        this.notificacaoService = notificacaoService;
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.itemRepository = itemRepository;
    }

    public void processarPedidos(List<Pedido> pedidos) {
        for (Pedido pedido : pedidos) {
            processarPedido(pedido);
        }
    }

    public void processarPedido(Pedido pedido) {
        double total = calcularTotal(pedido);
        pedido.setTotal(total);

        if (pedido.getCliente().isVip()) {
            total = aplicarDescontoVip(total);
        }
        pedido.setTotalComDesconto(total);

        boolean emEstoque = verificarDisponibilidadeEmEstoque(pedido);
        pedido.setEmEstoque(emEstoque);

        definirDataEntrega(pedido, emEstoque);
        notificarCliente(pedido.getCliente().getEmail(), emEstoque);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido salvarPedido(Pedido pedido) {
        // 1. Buscar cliente
        Cliente cliente = clienteRepository.findById(pedido.getCliente().getId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        pedido.setCliente(cliente);

        // 2. Recarregar os itens com dados corretos
        List<Item> itensAtualizados = new ArrayList<>();
        for (Item item : pedido.getItens()) {
            Item itemBanco = itemRepository.findById(item.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Item com id " + item.getId() + " não encontrado"));
            itemBanco.setQuantidade(item.getQuantidade()); // mantém a quantidade enviada
            itensAtualizados.add(itemBanco);
        }

        pedido.setItens(itensAtualizados);

        // 3. Calcular total e total com desconto
        double total = totalService.calcularTotal(pedido);
        pedido.setTotal(total);

        if (cliente.isVip()) {
            pedido.setTotalComDesconto(totalService.aplicarDescontoVip(total));
        } else {
            pedido.setTotalComDesconto(total);
        }

        // 4. Definir data de entrega (ex: 7 dias após hoje)
        pedido.setDataEntrega(LocalDate.now().plusDays(7));

        // 5. Verificar estoque
        boolean emEstoque = pedido.getItens().stream()
                .allMatch(i -> i.getQuantidade() <= i.getEstoque());
        pedido.setEmEstoque(emEstoque);

        // 6. Salvar pedido
        return pedidoRepository.save(pedido);
    }

    public Pedido buscarPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public void deletarPorId(@PathVariable Long id) {
        pedidoRepository.deleteById(id);
    }

    private double calcularTotal(Pedido pedido) {
        return totalService.calcularTotal(pedido);
    }

    private double aplicarDescontoVip(double total) {
        return total * 0.9;
    }

    private boolean verificarDisponibilidadeEmEstoque(Pedido pedido) {
        return estoqueService.verificarDisponibilidade(pedido);
    }

    private void definirDataEntrega(Pedido pedido, boolean emEstoque) {
        if (emEstoque) {
            pedido.setDataEntrega(LocalDate.now().plusDays(3));
        } else {
            pedido.setDataEntrega(null);
        }
    }

    private void notificarCliente(String email, boolean emEstoque) {
        String mensagem = emEstoque
            ? "Seu pedido será entregue em breve."
            : "Um ou mais itens do seu pedido estão fora de estoque.";
        enviarNotificacao(email, mensagem);
    }

    private void enviarNotificacao(String email, String mensagem) {
        notificacaoService.enviar(email, mensagem);
    }
}

