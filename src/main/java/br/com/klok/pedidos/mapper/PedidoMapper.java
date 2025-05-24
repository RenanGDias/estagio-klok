package br.com.klok.pedidos.mapper;

import br.com.klok.pedidos.dto.request.PedidoRequestDTO;
import br.com.klok.pedidos.dto.request.ItemPedidoRequestDTO;
import br.com.klok.pedidos.dto.response.PedidoResponseDTO;
import br.com.klok.pedidos.dto.response.ItemPedidoResponseDTO;
import br.com.klok.pedidos.model.Cliente;
import br.com.klok.pedidos.model.Item;
import br.com.klok.pedidos.model.ItemPedido;
import br.com.klok.pedidos.model.Pedido;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoMapper {

    public static Pedido toEntity(PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setDataEntrega(dto.getData());

        // Cria uma instância do cliente apenas com ID (associação mínima)
        Cliente cliente = new Cliente();
        cliente.setId(dto.getClienteId());
        pedido.setCliente(cliente);

        // Mapeia os itens do pedido
        List<ItemPedido> itens = dto.getItens().stream().map(itemDTO -> {
            Item item = new Item();
            item.setId(itemDTO.getItemId());

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setItem(item);
            itemPedido.setQuantidade(itemDTO.getQuantidade());
            itemPedido.setPedido(pedido);

            return itemPedido;
        }).collect(Collectors.toList());

        pedido.setItens(itens);
        return pedido;
    }

    public static PedidoResponseDTO toDTO(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setDataEntrega(pedido.getDataEntrega());
        dto.setTotal(pedido.getTotal());
        dto.setTotalComDesconto(pedido.getTotalComDesconto());
        dto.setEmEstoque(pedido.isEmEstoque());

        if (pedido.getCliente() != null) {
            dto.setCliente(ClienteMapper.toDto(pedido.getCliente()));
        }

        List<ItemPedidoResponseDTO> itensDTO = pedido.getItens().stream()
                .map(itemPedido -> {
                    ItemPedidoResponseDTO itemDto = new ItemPedidoResponseDTO();
                    itemDto.setItem(ItemMapper.toDTO(itemPedido.getItem()));
                    itemDto.setQuantidade(itemPedido.getQuantidade());
                    return itemDto;
                })
                .collect(Collectors.toList());

        dto.setItens(itensDTO);
        return dto;
    }
}
