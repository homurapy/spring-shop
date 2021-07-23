package com.example.springshop.service;

import com.example.springshop.config.security.CustomUserDetailsService;
import com.example.springshop.dto.ProductDto;
import com.example.springshop.model.Item;
import com.example.springshop.model.Order;
import com.example.springshop.repository.ItemRepository;
import com.example.springshop.repository.OrderRepository;
import com.example.springshop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;
    private final CustomUserDetailsService service;

    public Order createOrder(List<ProductDto> dtos) {
        Order order = new Order();
        order.setUuid(UUID.randomUUID());
        order.setUser(service.getUser());
        order.setTotalPrice(totalPrice(dtos));
        order.setDate(new Date());
        order.setStatus("new");
        orderRepository.save(order);
            for (int i = 0; i < dtos.size(); i++) {
            Item item = new Item();
            ProductDto dto = dtos.get(i);
            item.setProduct(productRepository.getById(dto.getId()));
            item.setQuantity(dto.getCount());
            item.setOrder(order);
            itemRepository.save(item);
        }
        return order;
    }

    private Integer totalPrice(List<ProductDto> productList) {
        Integer total = 0;
        for (int i = 0; i < productList.size(); i++) {
            total = total + productList.get(i).getPrice() * productList.get(i).getCount();
        }
        return total;
    }
    public void clearOrder(Order ord){
        orderRepository.delete(ord);
    }

}
