package com.RodCarvalhoas.BackeryStock;

import com.RodCarvalhoas.BackeryStock.domain.Categoria;
import com.RodCarvalhoas.BackeryStock.domain.Item;
import com.RodCarvalhoas.BackeryStock.repositories.CategoriaRepository;
import com.RodCarvalhoas.BackeryStock.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.RodCarvalhoas.BackeryStock.Enums.UnMedida.KG;

@SpringBootApplication
public class BackeryStockApplication implements CommandLineRunner {

	@Autowired
	CategoriaRepository categoriaRepository;
	@Autowired
	ItemRepository itemRepository;

	public static void main(String[] args) {
		SpringApplication.run(BackeryStockApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Farinhas & Grãos", "Farinhas e Grãos");
		Item it1 = new Item(null, "Farinha de Trigo", 5, 2.0, KG, cat1);

		cat1.getItems().add(it1);

		categoriaRepository.save(cat1);
		itemRepository.save(it1);
	}
}

