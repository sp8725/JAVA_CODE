package com.order.order_service.event;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderPlacedEvent {
    private String orderNumber;
}
