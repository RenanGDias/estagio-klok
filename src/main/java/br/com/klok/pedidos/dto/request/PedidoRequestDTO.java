package br.com.klok.pedidos.dto.request;

import java.time.LocalDate;
import java.util.List;

public class PedidoRequestDTO {
    private Long clienteId;
    private LocalDate data;
    private List<ItemRequestDTO> itens;
    
    public Long getClienteId() {
        return clienteId;
    }
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public List<ItemRequestDTO> getItens() {
        return itens;
    }
    public void setItens(List<ItemRequestDTO> itens) {
        this.itens = itens;
    }
    
}

