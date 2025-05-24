package br.com.klok.pedidos.dto.response;

public class ItemPedidoResponseDTO {
    private ItemResponseDTO item;
    private int quantidade;

    public ItemResponseDTO getItem() {
        return item;
    }

    public void setItem(ItemResponseDTO item) {
        this.item = item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
