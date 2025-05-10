package com.programming;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderPlacedEvent {
    private String orderNumber;
}
