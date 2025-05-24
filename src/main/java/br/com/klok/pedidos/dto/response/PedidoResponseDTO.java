package br.com.klok.pedidos.dto.response;

import java.time.LocalDate;
import java.util.List;

public class PedidoResponseDTO {
    private Long id;
    private ClienteResponseDTO cliente;
    private List<ItemResponseDTO> itens;
    private double total;
    private double totalComDesconto;
    private boolean emEstoque;
    private LocalDate dataEntrega;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public ClienteResponseDTO getCliente() {
        return cliente;
    }
    public void setCliente(ClienteResponseDTO cliente) {
        this.cliente = cliente;
    }
    public List<ItemResponseDTO> getItens() {
        return itens;
    }
    public void setItens(List<ItemResponseDTO> itens) {
        this.itens = itens;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public double getTotalComDesconto() {
        return totalComDesconto;
    }
    public void setTotalComDesconto(double totalComDesconto) {
        this.totalComDesconto = totalComDesconto;
    }
    public boolean isEmEstoque() {
        return emEstoque;
    }
    public void setEmEstoque(boolean emEstoque) {
        this.emEstoque = emEstoque;
    }
    public LocalDate getDataEntrega() {
        return dataEntrega;
    }
    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }
    
}

