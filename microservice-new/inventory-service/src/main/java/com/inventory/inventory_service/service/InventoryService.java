package com.inventory.inventory_service.service;

import com.inventory.inventory_service.dto.InventoryResponse;
import com.inventory.inventory_service.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InventoryService {

    @Autowired
    private final InventoryRepository inventoryRepository;
    @SneakyThrows
    @Transactional
    public List<InventoryResponse> isInStock(List<String> skuCode) throws NullPointerException{
        log.info("Wait Started");
        Thread.sleep(10000);
        log.info("Wait Ended");

        return inventoryRepository.findBySkuCodeIn(skuCode).stream().map(inventory -> {
           return InventoryResponse.builder()
                    .skuCode(inventory.getSkuCode())
                    .isInStock(inventory.getQuantity() > 0).build();
        }).collect(Collectors.toList());
    }
}
