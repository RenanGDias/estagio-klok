package br.com.klok.pedidos.service;

import br.com.klok.pedidos.model.Item;
import br.com.klok.pedidos.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item buscarPorId(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item com ID " + id + " não encontrado"));
    }

    public Item salvar(Item item) {
        return itemRepository.save(item);
    }
}
