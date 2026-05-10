package co.edu.unbosque.pokemon.repository;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.pokemon.entity.Item;

public interface ItemRepository extends CrudRepository<Item,String> {

}
