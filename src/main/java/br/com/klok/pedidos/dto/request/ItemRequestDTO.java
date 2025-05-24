package br.com.klok.pedidos.dto.request;

public class ItemRequestDTO {
    private Long id; 
    private int quantidade;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}

