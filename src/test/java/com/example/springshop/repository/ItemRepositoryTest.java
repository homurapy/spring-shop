package com.example.springshop.repository;

import com.example.springshop.model.Item;
import com.example.springshop.model.Order;
import com.example.springshop.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void itemRepositorySaveTest() {
        var product = new Product();
        product.setPrice(200);
        product.setName("Кукурузные лепешки");
        product.setCount(5);

        var product2 = new Product();
        product2.setPrice(350);
        product2.setName("Салат");
        product2.setCount(7);

        entityManager.persist(product);
        entityManager.persist(product2);

        Order order = new Order();
order.setUuid(UUID.randomUUID());
        entityManager.persist(order);

        var item = new Item();
        item.setProduct(product);
        item.setQuantity(product.getCount());
        item.setOrder(order);

        var item1 = new Item();
        item1.setProduct(product2);
        item1.setQuantity(product2.getCount());
        item1.setOrder(order);


        entityManager.persist(item);
        entityManager.persist(item1);
        entityManager.flush();
        List<Item> items = repository.findAll();
        Assertions.assertEquals(2, items.size());
        Assertions.assertEquals(7, items.get(items.size() - 1).getQuantity());

        Assertions.assertEquals("Кукурузные лепешки", items.get(0).getProduct().getName());
    }
}