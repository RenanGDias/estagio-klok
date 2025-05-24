package br.com.klok.pedidos.mapper;

import br.com.klok.pedidos.dto.request.ClienteRequestDTO;
import br.com.klok.pedidos.dto.response.ClienteResponseDTO;
import br.com.klok.pedidos.model.Cliente;

public class ClienteMapper {

    public static Cliente toEntity(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setVip(dto.isVip());
        return cliente;
    }

    public static ClienteResponseDTO toDto(Cliente cliente) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        dto.setVip(cliente.isVip());
        return dto;
    }
}

