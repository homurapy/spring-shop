package com.example.springshop.repository;

import com.example.springshop.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void productRepositorySaveTest() {
        var product = new Product();
        product.setPrice(200);
        product.setName("Кукурузные лепешки");
        product.setCount(1);

        var product2 = new Product();
        product2.setPrice(350);
        product2.setName("Салат");
        product2.setCount(1);
        entityManager.persist(product);
        entityManager.persist(product2);
        entityManager.flush();
        List<Product> productList = repository.findAll();
        Assertions.assertEquals(2, productList.size());
        Assertions.assertEquals("Салат", productList.get(productList.size() - 1).getName());
        Assertions.assertEquals("Кукурузные лепешки", productList.get(0).getName());
    }

}
