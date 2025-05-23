package br.com.klok.pedidos.model;

import java.util.List;
import java.time.LocalDate;

public class Pedido {
    private Cliente cliente;
    private List<Item> items;
    private double total;
    private double totalComDesconto;
    private boolean emEstoque;
    private LocalDate dataEntrega;
    
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
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
