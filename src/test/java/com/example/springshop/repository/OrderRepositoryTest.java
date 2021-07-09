package com.example.springshop.repository;

import com.example.springshop.model.Order;
import com.example.springshop.model.Product;
import com.example.springshop.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class OrderRepositoryTest {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void orderRepositorySaveTest() {
        Order order = new Order();
        User user = new User();
        user.setId(1L);
        entityManager.merge(user);
        order.setUuid(UUID.randomUUID());
        order.setUser(user);
        order.setTotalPrice(0);
        order.setDate(new Date());
        order.setStatus("new");
        entityManager.persist(order);
        entityManager.flush();
        List<Order> orders = repository.findAll();
        Assertions.assertEquals(order.getUuid(), orders.get(0).getUuid());
    }
}