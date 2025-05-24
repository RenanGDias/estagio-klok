package br.com.klok.pedidos.dto.request;

import java.time.LocalDate;
import java.util.List;

public class PedidoRequestDTO {
    private Long clienteId;
    private List<ItemPedidoRequestDTO> itens;
    private LocalDate data;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemPedidoRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoRequestDTO> itens) {
        this.itens = itens;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
