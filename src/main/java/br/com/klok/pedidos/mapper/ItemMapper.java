package br.com.klok.pedidos.mapper;

import br.com.klok.pedidos.dto.request.ItemRequestDTO;
import br.com.klok.pedidos.dto.response.ItemResponseDTO;
import br.com.klok.pedidos.model.Item;

public class ItemMapper {

    public static Item toEntity(ItemRequestDTO dto) {
        Item item = new Item();
        item.setId(dto.getId()); // importante para buscar o item do banco
        item.setQuantidade(dto.getQuantidade());
        return item;
    }

    public static ItemResponseDTO toDTO(Item entity) {
        ItemResponseDTO dto = new ItemResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setQuantidade(entity.getQuantidade());
        dto.setPreco(entity.getPreco());
        dto.setEstoque(entity.getEstoque());
        return dto;
    }
}
