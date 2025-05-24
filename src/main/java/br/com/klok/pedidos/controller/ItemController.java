package br.com.klok.pedidos.controller;

import br.com.klok.pedidos.model.Item;
import br.com.klok.pedidos.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itens")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public Item criarItem(@RequestBody Item item) {
        return itemService.salvar(item);
    }

    @GetMapping("/{id}")
    public Item buscarItem(@PathVariable Long id) {
        return itemService.buscarPorId(id);
    }
}
