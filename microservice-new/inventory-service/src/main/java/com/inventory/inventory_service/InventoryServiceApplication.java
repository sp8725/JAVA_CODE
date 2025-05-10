package com.inventory.inventory_service;

import com.inventory.inventory_service.model.Inventory;
import com.inventory.inventory_service.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args->{
			Inventory inventory= new Inventory();
			inventory.setSkuCode("OnePlus11R");
			inventory.setQuantity(120L);

			Inventory inventory1= new Inventory();
			inventory1.setSkuCode("OnePlus12R");
			inventory1.setQuantity(0L);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};
	}
}
