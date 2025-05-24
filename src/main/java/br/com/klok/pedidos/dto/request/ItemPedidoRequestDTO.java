package br.com.klok.pedidos.dto.request;

public class ItemPedidoRequestDTO {

    private Long itemId;
    private int quantidade;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
