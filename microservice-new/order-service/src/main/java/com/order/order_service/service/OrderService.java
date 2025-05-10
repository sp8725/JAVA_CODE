package com.order.order_service.service;

import com.order.order_service.dto.InventoryResponse;
import com.order.order_service.dto.OrderRequest;
import com.order.order_service.event.OrderPlacedEvent;
import com.order.order_service.model.Order;
import com.order.order_service.model.OrderLineItems;
import com.order.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final WebClient.Builder webClientBuilder;
    @Autowired
    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;

    public CompletableFuture<String> placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsRequests().stream().map(OrderLineItemsDto -> {
            OrderLineItems orderLineItem = new OrderLineItems();
            orderLineItem.setPrice(OrderLineItemsDto.getPrice());
            orderLineItem.setQuantity(OrderLineItemsDto.getQuantity());
            orderLineItem.setSkuCode(OrderLineItemsDto.getSkuCode());
            return orderLineItem;
        }).collect(Collectors.toList());
        order.setOrderLineItems(orderLineItems);

        List<String> skuCodeList = order.getOrderLineItems().stream().map(OrderLineItems::getSkuCode).collect(Collectors.toList());
        //call inventory service and place order if product in stock
        InventoryResponse [] inventoryResponseArray = webClientBuilder.build().get().uri("http://inventory-service/api/inventory", uriBuilder ->
                        uriBuilder.queryParam("skuCode", skuCodeList).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean allProductInStock=Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);
        if (allProductInStock) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber()));
            return CompletableFuture.supplyAsync(()->"Order Has Been Placed Successfully!");
        } else {
            throw new IllegalArgumentException("Product Not In Stock");
        }
    }
}
