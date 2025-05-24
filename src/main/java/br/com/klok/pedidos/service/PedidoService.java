package br.com.klok.pedidos.service;

import org.springframework.stereotype.Service;

import br.com.klok.pedidos.service.helper.EstoqueService;
import br.com.klok.pedidos.service.helper.NotificacaoService;
import br.com.klok.pedidos.service.helper.TotalService;
import br.com.klok.pedidos.dto.request.PedidoRequestDTO;
import br.com.klok.pedidos.dto.response.PedidoResponseDTO;
import br.com.klok.pedidos.mapper.PedidoMapper;
import br.com.klok.pedidos.dto.request.ItemPedidoRequestDTO;
import br.com.klok.pedidos.model.Cliente;
import br.com.klok.pedidos.model.Item;
import br.com.klok.pedidos.model.ItemPedido;
import br.com.klok.pedidos.model.Pedido;
import br.com.klok.pedidos.repository.ClienteRepository;
import br.com.klok.pedidos.repository.ItemRepository;
import br.com.klok.pedidos.repository.PedidoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    public PedidoResponseDTO salvarPedido(PedidoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataEntrega(dto.getData());
        List<ItemPedido> itensPedido = new ArrayList<>();
        for (ItemPedidoRequestDTO itemDTO : dto.getItens()) {
            Item item = itemRepository.findById(itemDTO.getItemId())
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setItem(item);
            itemPedido.setQuantidade(itemDTO.getQuantidade());
            itemPedido.setPedido(pedido); 

            itensPedido.add(itemPedido);
        }

        pedido.setItens(itensPedido); 

        processarPedido(pedido); 
        Pedido salvo = pedidoRepository.save(pedido);
        return PedidoMapper.toDTO(salvo);
    }

    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(PedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .map(PedidoMapper::toDTO)
                .orElse(null);
    }

    public void deletarPorId(Long id) {
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
