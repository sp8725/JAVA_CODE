package com.inventory.inventory_service.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
@Data
public class InventoryResponse {

    private String skuCode;
    private boolean isInStock;
}
