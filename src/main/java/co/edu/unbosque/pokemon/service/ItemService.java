package co.edu.unbosque.pokemon.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.pokemon.dto.ItemDTO;
import co.edu.unbosque.pokemon.repository.ItemRepository;

@Service
public class ItemService {
	@Autowired
	private ItemRepository itemRep;

	@Autowired
	private ModelMapper mapper;

	public List<ItemDTO> listarTodos() {
		List<ItemDTO> dtos = new ArrayList<>();
		itemRep.findAll().forEach(e -> dtos.add(mapper.map(e, ItemDTO.class)));
		return dtos;
	}
}
