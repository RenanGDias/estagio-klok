package br.com.klok.pedidos.repository;

import br.com.klok.pedidos.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>{
    
}
